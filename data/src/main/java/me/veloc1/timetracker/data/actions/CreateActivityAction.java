package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.annotations.Nullable;
import me.veloc1.timetracker.data.repository.ActivitiesRepository;
import me.veloc1.timetracker.data.repository.TagToActivityRepository;
import me.veloc1.timetracker.data.repository.TagsRepository;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Tag;
import me.veloc1.timetracker.data.types.TagToActivity;

import javax.inject.Inject;

/**
 * Creates an activity with given fields and bind newly created activity to given tags.
 */
public class CreateActivityAction implements Action<Activity> {

  private final String   title;
  private final String   description;
  @Nullable
  private final String[] tags;

  private Activity result;

  private ActivitiesRepository    activitiesRepository;
  private TagsRepository          tagsRepository;
  private TagToActivityRepository tagToActivityRepository;
  private TimeProvider            timeProvider;

  public CreateActivityAction(String title, String description, @Nullable String[] tags) {
    this.title = title;
    this.description = description;
    this.tags = tags;
  }

  @Override
  public void execute() {
    Activity toCreate =
        new Activity(
            -1,
            title,
            description,
            timeProvider.getCurrentTimeInMillis(),
            timeProvider.getCurrentTimeInMillis());
    result = activitiesRepository.add(toCreate);

    Tag[] tagsObjects = collectTagsFromStringArray(tags);
    bindTagsToActivity(tagsObjects, result);
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

  private void bindTagsToActivity(Tag[] tags, Activity activity) {
    for (final Tag tag : tags) {
      tagToActivityRepository.add(new TagToActivity(tag.getId(), activity.getId()));
    }
  }

  @Override
  public Activity getResult() {
    return result;
  }

  @Inject
  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }

  @Inject
  public void setTagsRepository(TagsRepository tagsRepository) {
    this.tagsRepository = tagsRepository;
  }

  @Inject
  public void setTagToActivityRepository(TagToActivityRepository tagToActivityRepository) {
    this.tagToActivityRepository = tagToActivityRepository;
  }

  @Inject
  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }
}
