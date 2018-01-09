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
import java.util.Date;

public class CreateActivityActionTest extends BaseActionTest {

  @Test
  public void createActivityWithoutTags() throws Throwable {
    String title       = "title";
    String description = "test description";

    CreateActivityAction action = new CreateActivityAction(title, description, null);

    ActivitiesRepository repository = initActivitiesRepository(action);

    action.setTimeProvider(new TimeProvider());

    Date     timeBeforeExecute = Calendar.getInstance().getTime();
    Activity result            = execute(action);
    Date     timeAfterExecute  = Calendar.getInstance().getTime();

    verifyReturnedActivity(title, description, result, timeBeforeExecute, timeAfterExecute);
    verifyAddInvocation(repository, result);

    Mockito.verifyNoMoreInteractions(repository);
  }

  @Test(expected = NullPointerException.class)
  public void createActivityWithError() throws Throwable {
    CreateActivityAction action = new CreateActivityAction(null, null, null);

    ActivitiesRepository repository = initActivitiesRepository(action);

    action.setTimeProvider(new TimeProvider());

    execute(action); //this line should throw

    Mockito.verifyNoMoreInteractions(repository);
  }

  @Test
  public void createActivityWithTags() throws Throwable {
    String title       = "title";
    String description = "test description";

    String   tag1 = "tag, already created";
    String   tag2 = "not created";
    String[] tags = new String[]{tag1, tag2};

    CreateActivityAction action = new CreateActivityAction(title, description, tags);

    ActivitiesRepository    activitiesRepository    = initActivitiesRepository(action);
    TagsRepository          tagsRepository          = initTagsRepository(action);
    TagToActivityRepository tagToActivityRepository = initTagToActivityRepository(action);

    action.setTimeProvider(new TimeProvider());

    Date     timeBeforeExecute = Calendar.getInstance().getTime();
    Activity result            = execute(action);
    Date     timeAfterExecute  = Calendar.getInstance().getTime();

    verifyReturnedActivity(title, description, result, timeBeforeExecute, timeAfterExecute);

    verifyAddInvocation(activitiesRepository, result);

    verifyTags(tag1, tag2, tags, tagsRepository, tagToActivityRepository);

    Mockito.verifyNoMoreInteractions(activitiesRepository, tagsRepository, tagToActivityRepository);
  }

  private ActivitiesRepository initActivitiesRepository(CreateActivityAction action) {
    ActivitiesRepository repository = mockActivitiesRepository();
    action.setActivitiesRepository(repository);
    return repository;
  }

  private TagsRepository initTagsRepository(CreateActivityAction action) {
    TagsRepository tagsRepository = mockTagsRepository();
    action.setTagsRepository(tagsRepository);
    return tagsRepository;
  }

  private TagToActivityRepository initTagToActivityRepository(CreateActivityAction action) {
    TagToActivityRepository tagToActivityRepository = mockTagToActivityRepository();
    action.setTagToActivityRepository(tagToActivityRepository);
    return tagToActivityRepository;
  }

  private void verifyReturnedActivity(
      String title,
      String description,
      Activity result,
      Date timeBeforeExecute,
      Date timeAfterExecute) {

    Assert.assertEquals(1, result.getId());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertEquals(description, result.getDescription());

    Assert.assertTrue(
        result.getCreatedAt() >= timeBeforeExecute.getTime()
        && result.getCreatedAt() <= timeAfterExecute.getTime());
    Assert.assertTrue(
        result.getUpdatedAt() >= timeBeforeExecute.getTime()
        && result.getUpdatedAt() <= timeAfterExecute.getTime());
  }

  private void verifyAddInvocation(ActivitiesRepository repository, Activity result) {
    ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);
    Mockito.verify(repository).add(captor.capture());

    Assert.assertEquals(-1, captor.getValue().getId());
    Assert.assertEquals(result.getTitle(), captor.getValue().getTitle());
    Assert.assertEquals(result.getDescription(), captor.getValue().getDescription());
    Assert.assertEquals(result.getCreatedAt(), captor.getValue().getCreatedAt());
    Assert.assertEquals(result.getUpdatedAt(), captor.getValue().getUpdatedAt());
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
        .given(repository.add(Mockito.any(Activity.class)))
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
