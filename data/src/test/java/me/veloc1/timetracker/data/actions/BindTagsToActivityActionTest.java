package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.data.types.Tag;
import me.veloc1.timetracker.data.types.TagToActivity;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

public class BindTagsToActivityActionTest extends BaseActionTest {
  private int createdTagCounter = 0; // this will increment
  private int foundTagCounter   = 0; // this will decrement

  @Test
  public void bindTags() throws Throwable {
    int      activityId = 1;
    String[] tags       = {"created", "notcreated"};

    BindTagsToActivityAction action = new BindTagsToActivityAction(activityId, tags);

    TagsRepository tagsRepository = mockTagsRepository();
    action.setTagsRepository(tagsRepository);
    TagToActivityRepository tagToActivityRepository = mockTagToActivityRepository();
    action.setTagToActivityRepository(tagToActivityRepository);

    execute(action);

    verifyTagsRepositoryInvocation(tagsRepository, tags);
    verifyTagToActivityRepositoryInvocation(tagToActivityRepository, activityId, tags);

    Mockito.verifyNoMoreInteractions(tagsRepository, tagToActivityRepository);
  }

  private TagsRepository mockTagsRepository() {
    TagsRepository repository = Mockito.mock(TagsRepository.class);
    BDDMockito
        .given(repository.find(Mockito.anyString()))
        .will(new Answer<Tag>() {

          @Override
          public Tag answer(InvocationOnMock invocation) throws Throwable {
            String tagTitle = (String) invocation.getArguments()[0];
            if (tagTitle.startsWith("created")) {
              foundTagCounter--;
              return new Tag(foundTagCounter, tagTitle);
            }
            return null;
          }
        });

    BDDMockito
        .given(repository.add(Mockito.any(Tag.class)))
        .will(new Answer<Integer>() {

          @Override
          public Integer answer(InvocationOnMock invocation) throws Throwable {
            createdTagCounter++;
            return createdTagCounter;
          }
        });
    return repository;
  }

  private TagToActivityRepository mockTagToActivityRepository() {
    TagToActivityRepository repository = Mockito.mock(TagToActivityRepository.class);
    return repository;
  }

  private void verifyTagsRepositoryInvocation(TagsRepository tagsRepository, String[] tags) {
    for (final String tag : tags) {
      Mockito.verify(tagsRepository).find(tag);
      if (!tag.startsWith("created")) {
        ArgumentCaptor<Tag> argumentCaptor = ArgumentCaptor.forClass(Tag.class);
        Mockito.verify(tagsRepository).add(argumentCaptor.capture());
        Assert.assertEquals(tag, argumentCaptor.getValue().getTitle());
      }
    }
  }

  private void verifyTagToActivityRepositoryInvocation(
      TagToActivityRepository repository,
      int activityId,
      String[] tags) {

    Mockito.verify(repository).removeTagsFromActivity(activityId);

    ArgumentCaptor<TagToActivity> captor = ArgumentCaptor.forClass(TagToActivity.class);
    Mockito.verify(repository, Mockito.times(tags.length)).add(captor.capture());

    List<TagToActivity> captured = captor.getAllValues();
    // check count of bindngs, this should be equals count of created tags + count of founded
    // tags, and
    Assert.assertEquals(createdTagCounter + (-foundTagCounter), captured.size());
    Assert.assertEquals(tags.length, captured.size());

    for (final TagToActivity tagToActivity : captured) {
      boolean found = false;
      if (tagToActivity.getTagId() < createdTagCounter) {
        found = true;
      }
      if (tagToActivity.getTagId() > foundTagCounter) {
        found = true;
      }
      Assert.assertTrue("Can't find tag binding", found);
    }
  }
}
