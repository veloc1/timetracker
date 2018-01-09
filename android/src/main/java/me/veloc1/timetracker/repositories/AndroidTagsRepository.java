package me.veloc1.timetracker.repositories;

import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.data.types.Tag;

import java.util.List;

public class AndroidTagsRepository extends SqliteRepository implements TagsRepository {
  @Override
  public Tag find(String tag) {
    return null;
  }

  @Override
  public List<Tag> findByActivity(int activityId) {
    return null;
  }

  @Override
  public Tag add(Tag objectToCreate) {
    return null;
  }

  @Override
  public Tag update(Tag objectToUpdate) {
    return null;
  }

  @Override
  public Tag getById(int id) {
    return null;
  }

  @Override
  public void createTable(SQLiteDatabase database) {

  }
}
