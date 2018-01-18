package me.veloc1.timetracker.data.types;

import java.util.List;

public class ActivityStatistic {

  private final int       activityId;
  private final long      totalDuration;
  private final List<Log> logs;

  public ActivityStatistic(int activityId, long duration, List<Log> logs) {
    this.activityId = activityId;
    totalDuration = duration;
    this.logs = logs;
  }

  public int getActivityId() {
    return activityId;
  }

  public long getTotalDuration() {
    return totalDuration;
  }

  public List<Log> getLogs() {
    return logs;
  }
}
