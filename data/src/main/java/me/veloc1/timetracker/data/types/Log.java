package me.veloc1.timetracker.data.types;

import me.veloc1.timetracker.data.annotations.Nullable;

public class Log {
  public static final long NO_DATE = -1;

  private final int       id;
  private final int       activityId;
  @Nullable
  private final String    description;
  private final LogStatus status;
  @Nullable
  private final long      startDate;
  @Nullable
  private final long      endDate;

  public Log(
      int id,
      String description,
      LogStatus status,
      int activityId,
      long startDate,
      long endDate) {

    this.id = id;
    this.description = description;
    this.status = status;
    this.activityId = activityId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public int getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public LogStatus getStatus() {
    return status;
  }

  public int getActivityId() {
    return activityId;
  }

  public long getStartDate() {
    return startDate;
  }

  public long getEndDate() {
    return endDate;
  }
}
