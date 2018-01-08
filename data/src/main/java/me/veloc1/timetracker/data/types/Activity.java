package me.veloc1.timetracker.data.types;

public class Activity {
  private final int    id;
  private final String title;
  private final String description;
  private final long   createdAt;
  private final long   updatedAt;

  public Activity(int id, String title, String description, long createdAt, long updatedAt) {
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

  public long getCreatedAt() {
    return createdAt;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }
}
