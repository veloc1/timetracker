package me.veloc1.timetracker.data.types;

import me.veloc1.timetracker.data.annotations.Nullable;

import java.util.concurrent.TimeUnit;

public class Log {
  public static final long NO_DATE = -1;

  private final int       id;
  @Nullable
  private final String    description;
  private final LogStatus status;
  @Nullable
  private final long      startDate;
  @Nullable
  private final long      endDate;

  public Log(int id, String description, LogStatus status, long startDate, long endDate) {
    this.id = id;
    this.description = description;
    this.status = status;
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

  public long getStartDate() {
    return startDate;
  }

  public long getEndDate() {
    return endDate;
  }
}
