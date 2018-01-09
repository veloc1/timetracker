package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Tag;
import me.veloc1.timetracker.data.types.TagToActivity;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EditActivityActionTest extends BaseActionTest {

  @Test
  public void editActivityWithTags() throws Throwable {
    String title       = "title";
    String description = "test description";

    String   tag1 = "tag, already created";
    String   tag2 = "not created";
    String[] tags = new String[]{tag1, tag2};

    EditActivityAction action = new EditActivityAction(1, title, description, tags);

    ActivitiesRepository    activitiesRepository    = initActivitiesRepository(action);
    TagsRepository          tagsRepository          = initTagsRepository(action);
    TagToActivityRepository tagToActivityRepository = initTagToActivityRepository(action);

    action.setTimeProvider(new TimeProvider());

    Date timeBeforeExecute = Calendar.getInstance().getTime();
    execute(action);
    Date timeAfterExecute = Calendar.getInstance().getTime();

    Mockito.verify(activitiesRepository).getById(1);

    verifyUpdateActivityInvocation(
        title,
        description,
        activitiesRepository,
        timeBeforeExecute,
        timeAfterExecute);

    Mockito.verify(tagsRepository).findByActivity(1);

    verifyTags(tag1, tag2, tags, tagsRepository, tagToActivityRepository);

    Mockito.verifyNoMoreInteractions(activitiesRepository, tagsRepository, tagToActivityRepository);
  }

  private ActivitiesRepository initActivitiesRepository(EditActivityAction action) {
    ActivitiesRepository activitiesRepository = mockActivitiesRepository();
    action.setActivitiesRepository(activitiesRepository);
    return activitiesRepository;
  }

  private TagsRepository initTagsRepository(EditActivityAction action) {
    TagsRepository tagsRepository = mockTagsRepository();
    action.setTagsRepository(tagsRepository);
    return tagsRepository;
  }

  private TagToActivityRepository initTagToActivityRepository(EditActivityAction action) {
    TagToActivityRepository tagToActivityRepository = mockTagToActivityRepository();
    action.setTagToActivityRepository(tagToActivityRepository);
    return tagToActivityRepository;
  }

  private void verifyUpdateActivityInvocation(
      String title,
      String description,
      ActivitiesRepository activitiesRepository,
      Date timeBeforeExecute,
      Date timeAfterExecute) {

    ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);
    Mockito.verify(activitiesRepository).update(captor.capture());

    Assert.assertEquals(1, captor.getValue().getId());
    Assert.assertEquals(title, captor.getValue().getTitle());
    Assert.assertEquals(description, captor.getValue().getDescription());
    Assert.assertEquals(100L, captor.getValue().getCreatedAt());
    Assert.assertTrue(
        captor.getValue().getUpdatedAt() >= timeBeforeExecute.getTime()
        && captor.getValue().getUpdatedAt() <= timeAfterExecute.getTime());
  }

  private void verifyTags(
      String tag1,
      String tag2,
      String[] tags,
      TagsRepository tagsRepository,
      TagToActivityRepository tagToActivityRepository) {

    Mockito.verify(tagsRepository).find(tag1);
    Mockito.verify(tagsRepository).find(tag2);

    ArgumentCaptor<Tag> tagCaptor = ArgumentCaptor.forClass(Tag.class);
    Mockito.verify(tagsRepository).add(tagCaptor.capture());
    Assert.assertEquals(tag2, tagCaptor.getValue().getTitle());

    Mockito.verify(tagToActivityRepository).removeTagFromActivity(126, 1);

    ArgumentCaptor<TagToActivity> tagToActivityCaptor
        = ArgumentCaptor.forClass(TagToActivity.class);

    Mockito
        .verify(tagToActivityRepository, Mockito.times(tags.length))
        .add(tagToActivityCaptor.capture());

    int countOfAddedTags = 0;
    for (final TagToActivity tagToActivity : tagToActivityCaptor.getAllValues()) {
      if (tagToActivity.getActivityId() == 1) {
        if (tagToActivity.getTagId() == 125 || tagToActivity.getTagId() == 1) {
          countOfAddedTags++;
        }
      }
    }
    Assert.assertEquals(tags.length, countOfAddedTags);
  }

  private ActivitiesRepository mockActivitiesRepository() {
    ActivitiesRepository repository = Mockito.mock(ActivitiesRepository.class);

    BDDMockito
        .given(repository.getById(1))
        .will(new Answer<Activity>() {

          @Override
          public Activity answer(InvocationOnMock invocation) {
            Activity result =
                new Activity(
                    (Integer) invocation.getArguments()[0],
                    "",
                    "",
                    100L,
                    200L);
            return result;
          }
        });

    BDDMockito
        .given(repository.update(Mockito.any(Activity.class)))
        .will(new Answer<Activity>() {

          @Override
          public Activity answer(InvocationOnMock invocation) {
            Activity activityToCreate = invocation.getArgument(0);

            if (activityToCreate.getTitle() == null) {
              throw new NullPointerException();
            }

            Activity result =
                new Activity(
                    1, // id is permanent because of test
                    activityToCreate.getTitle(),
                    activityToCreate.getDescription(),
                    activityToCreate.getCreatedAt(),
                    activityToCreate.getUpdatedAt());
            return result;
          }
        });
    return repository;
  }

  private TagsRepository mockTagsRepository() {
    TagsRepository repository = Mockito.mock(TagsRepository.class);

    BDDMockito
        .given(repository.find(Mockito.anyString()))
        .will(new Answer<Tag>() {

          @Override
          public Tag answer(InvocationOnMock invocation) {
            if (invocation.getArguments()[0].equals("tag, already created")) {
              return new Tag(125, (String) invocation.getArguments()[0]);
            } else {
              return null;
            }
          }
        });

    BDDMockito
        .given(repository.findByActivity(1))
        .will(new Answer<List<Tag>>() {

          @Override
          public List<Tag> answer(InvocationOnMock invocation) {
            return Collections.singletonList(new Tag(126, ""));
          }
        });

    BDDMockito
        .given(repository.add(Mockito.any(Tag.class)))
        .will(new Answer<Tag>() {

          @Override
          public Tag answer(InvocationOnMock invocation) {
            return new Tag(1, ((Tag) invocation.getArguments()[0]).getTitle());
          }
        });

    return repository;
  }

  private TagToActivityRepository mockTagToActivityRepository() {
    return Mockito.mock(TagToActivityRepository.class);
  }
}
