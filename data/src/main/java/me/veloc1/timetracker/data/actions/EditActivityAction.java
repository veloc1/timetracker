package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.annotations.Nullable;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Tag;
import me.veloc1.timetracker.data.types.TagToActivity;

import javax.inject.Inject;
import java.util.List;

public class EditActivityAction implements Action<Void> {

  private final int      activityId;
  private final String   newTitle;
  private final String   newDescription;
  @Nullable
  private final String[] newTags;

  @Inject
  private ActivitiesRepository    activitiesRepository;
  @Inject
  private TagsRepository          tagsRepository;
  @Inject
  private TagToActivityRepository tagToActivityRepository;
  @Inject
  private TimeProvider            timeProvider;

  public EditActivityAction(int activityId, String newTitle, String newDescription, String[] tags) {
    this.activityId = activityId;
    this.newTitle = newTitle;
    this.newDescription = newDescription;
    newTags = tags;
  }

  @Override
  public void execute() {
    Activity activity = activitiesRepository.getById(activityId);

    activitiesRepository.update(
        new Activity(
            activityId,
            newTitle,
            newDescription,
            activity.getCreatedAt(),
            timeProvider.getCurrentTimeInMillis()));

    Tag[]     tagsObjects = collectTagsFromStringArray(newTags);
    List<Tag> oldTags     = tagsRepository.findByActivity(activityId);
    bindTagsToActivity(oldTags.toArray(new Tag[oldTags.size()]), tagsObjects, activityId);
  }

  private Tag[] collectTagsFromStringArray(String[] tags) {
    Tag[] tagsObjects;
    if (tags != null) {
      tagsObjects = new Tag[tags.length];
      for (int i = 0; i < tags.length; i++) {
        String tagTitle = tags[i];
        tagsObjects[i] = tagsRepository.find(tagTitle);
        if (tagsObjects[i] == null) {
          tagsObjects[i] = tagsRepository.add(new Tag(-1, tagTitle));
        }
      }
    } else {
      tagsObjects = new Tag[0];
    }
    return tagsObjects;
  }

  private void bindTagsToActivity(Tag[] oldTags, Tag[] tags, int activityId) {
    for (final Tag tag : oldTags) {
      tagToActivityRepository.removeTagFromActivity(tag.getId(), activityId);
    }

    for (final Tag tag : tags) {
      tagToActivityRepository.add(new TagToActivity(tag.getId(), activityId));
    }
  }

  @Override
  public Void getResult() {
    return null;
  }

  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }

  public void setTagsRepository(TagsRepository tagsRepository) {
    this.tagsRepository = tagsRepository;
  }

  public void setTagToActivityRepository(TagToActivityRepository tagToActivityRepository) {
    this.tagToActivityRepository = tagToActivityRepository;
  }

  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }
}
