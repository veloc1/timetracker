package me.veloc1.timetracker.repositories;

import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.types.TagToActivity;

public class AndroidTagToActivityRepository
    extends SqliteRepository
    implements TagToActivityRepository {

  @Override
  public void removeTagFromActivity(int tagId, int activityId) {

  }

  @Override
  public TagToActivity add(TagToActivity objectToCreate) {
    return null;
  }

  @Override
  public TagToActivity update(TagToActivity objectToUpdate) {
    return null;
  }

  @Override
  public TagToActivity getById(int id) {
    return null;
  }

  @Override
  public void createTable(SQLiteDatabase database) {

  }
}
