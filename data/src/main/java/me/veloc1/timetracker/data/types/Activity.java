package me.veloc1.timetracker.data.types;

import java.util.Date;

public class Activity {
  private final int    id;
  private final String title;
  private final String description;
  private final Tag[]  tags;
  private final Date   createdAt;
  private final Date   updatedAt;

  private Activity(
      int id,
      String title,
      String description,
      Tag[] tags,
      Date createdAt,
      Date updatedAt) {

    this.id = id;
    this.title = title;
    this.description = description;
    this.tags = tags;
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

  public Tag[] getTags() {
    return tags;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }
}
