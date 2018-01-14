package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.annotations.Nullable;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.data.types.Tag;
import me.veloc1.timetracker.data.types.TagToActivity;

import javax.inject.Inject;

public class BindTagsToActivityAction implements Action<Void> {
  private final int      activityId;
  private final String[] tags;

  @Inject
  private TagsRepository          tagsRepository;
  @Inject
  private TagToActivityRepository tagToActivityRepository;

  public BindTagsToActivityAction(int activityId, @Nullable String[] tags) {
    super();
    this.activityId = activityId;
    this.tags = tags;
  }

  @Override
  public void execute() {
    tagToActivityRepository.removeTagsFromActivity(activityId);
    Tag[] tagsObjects = collectTagsFromStringArray(tags);
    bindTagsToActivity(tagsObjects, activityId);
  }

  @Override
  public Void getResult() {
    return null;
  }

  private Tag[] collectTagsFromStringArray(String[] tags) {
    Tag[] tagsObjects;
    if (tags != null) {
      tagsObjects = new Tag[tags.length];
      for (int i = 0; i < tags.length; i++) {
        String tagTitle = tags[i];
        tagsObjects[i] = tagsRepository.find(tagTitle);
        if (tagsObjects[i] == null) {
          int id = tagsRepository.add(new Tag(-1, tagTitle));
          tagsObjects[i] = new Tag(id, tagTitle);
        }
      }
    } else {
      tagsObjects = new Tag[0];
    }
    return tagsObjects;
  }

  private void bindTagsToActivity(Tag[] tags, int activityId) {
    for (final Tag tag : tags) {
      tagToActivityRepository.add(new TagToActivity(-1, tag.getId(), activityId));
    }
  }

  public void setTagsRepository(TagsRepository tagsRepository) {
    this.tagsRepository = tagsRepository;
  }

  public void setTagToActivityRepository(TagToActivityRepository tagToActivityRepository) {
    this.tagToActivityRepository = tagToActivityRepository;
  }
}
