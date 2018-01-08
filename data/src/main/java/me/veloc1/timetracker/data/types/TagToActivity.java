package me.veloc1.timetracker.data.types;

public class TagToActivity {
  private final int tagId;
  private final int activityId;

  public TagToActivity(int tagId, int activityId) {
    this.tagId = tagId;
    this.activityId = activityId;
  }

  public int getTagId() {
    return tagId;
  }

  public int getActivityId() {
    return activityId;
  }
}
