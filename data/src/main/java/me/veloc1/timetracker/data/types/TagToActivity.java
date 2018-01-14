package me.veloc1.timetracker.data.types;

public class TagToActivity {
  private final int id;
  private final int tagId;
  private final int activityId;

  public TagToActivity(int id, int tagId, int activityId) {
    this.id = id;
    this.tagId = tagId;
    this.activityId = activityId;
  }

  public int getId() {
    return id;
  }

  public int getTagId() {
    return tagId;
  }

  public int getActivityId() {
    return activityId;
  }
}
