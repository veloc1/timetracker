package me.veloc1.timetracker.data.types;

public class Activity {
  private final int    id;
  private final String title;
  private final String description;
  private final int    color;
  private final long   createdAt;
  private final long   updatedAt;

  public Activity(
      int id,
      String title,
      String description,
      int color,
      long createdAt,
      long updatedAt) {

    this.id = id;
    this.title = title;
    this.description = description;
    this.color = color;
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

  public int getColor() {
    return color;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public long getUpdatedAt() {
    return updatedAt;
  }
}
