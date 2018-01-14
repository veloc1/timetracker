package me.veloc1.timetracker.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.types.TagToActivity;

import java.util.ArrayList;
import java.util.List;

public class AndroidTagToActivityRepository
    extends SqliteRepository<TagToActivity>
    implements TagToActivityRepository {

  private static final String TABLE_NAME      = "tag_activity";
  private static final String COLUMN_TAG      = "tag_id";
  private static final String COLUMN_ACTIVITY = "activity_id";

  private static final String[] COLUMNS
      = new String[]{
      COLUMN_ID,
      COLUMN_TAG,
      COLUMN_ACTIVITY};

  @Override
  public List<TagToActivity> findByActivityId(int activityId) {
    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        TABLE_NAME,
        COLUMNS,
        String.format("%1$s = ?", COLUMN_ACTIVITY),
        new String[]{String.valueOf(activityId)},
        null,
        null,
        null);

    List<TagToActivity> result = new ArrayList<>();

    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      do {
        result.add(createObjectFromCursor(cursor));
      } while (cursor.moveToNext());
    }

    cursor.close();
    return result;
  }

  @Override
  public void removeTagsFromActivity(int id) {
    SQLiteDatabase database = getWritableDatabase();

    database.delete(
        TABLE_NAME,
        String.format("%1$s = ?", COLUMN_ACTIVITY),
        new String[]{String.valueOf(id)});

    database.close();
  }

  @Override
  public void createTable(SQLiteDatabase database) {
    database.execSQL(
        String.format(
            "CREATE TABLE %1$s (" +
            "%2$s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%3$s INTEGER," +
            "%4$s INTEGER)",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_TAG,
            COLUMN_ACTIVITY));
  }

  @Override
  protected String getId(TagToActivity object) {
    return String.valueOf(object.getId());
  }

  @Override
  protected ContentValues objectToContentValues(TagToActivity object) {
    ContentValues result = new ContentValues();
    result.put(COLUMN_ID, object.getId());
    result.put(COLUMN_TAG, object.getTagId());
    result.put(COLUMN_ACTIVITY, object.getActivityId());
    return result;
  }

  @Override
  protected TagToActivity createObjectFromCursor(Cursor cursor) {
    return new TagToActivity(
        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
        cursor.getInt(cursor.getColumnIndex(COLUMN_TAG)),
        cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY))
    );
  }

  @Override
  protected String getTableName() {
    return TABLE_NAME;
  }

  @Override
  protected String[] getAllColumns() {
    return COLUMNS;
  }
}
