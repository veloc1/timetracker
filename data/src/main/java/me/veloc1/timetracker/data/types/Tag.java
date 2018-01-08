package me.veloc1.timetracker.data.types;

public class Tag {
  private final int id;
  private final String title;

  public Tag(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }
}
