package me.veloc1.timetracker.data.types;

import java.util.Calendar;
import java.util.Date;

public class Activity {
  private final int    id;
  private final String title;
  private final String description;
  private final Date   createdAt;
  private final Date   updatedAt;

  public Activity(int id, String title, String description) {
    this(id,
         title,
         description,
         Calendar.getInstance().getTime(),
         Calendar.getInstance().getTime());
  }

  public Activity(int id, String title, String description, Date createdAt, Date updatedAt) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }
}
