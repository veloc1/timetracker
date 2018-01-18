package me.veloc1.timetracker.screens.main;

public class ActivityStatisticDisplayItem {
  private final int    activityId;
  private final String title;
  private final int    color;
  private final float  value;

  public ActivityStatisticDisplayItem(int activityId, String title, int color, float value) {
    this.activityId = activityId;
    this.title = title;
    this.color = color;
    this.value = value;
  }

  public int getActivityId() {
    return activityId;
  }

  public String getTitle() {
    return title;
  }

  public int getColor() {
    return color;
  }

  public float getValue() {
    return value;
  }
}