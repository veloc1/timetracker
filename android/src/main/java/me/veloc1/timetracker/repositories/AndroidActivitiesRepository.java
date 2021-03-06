package me.veloc1.timetracker.repositories;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

public class AndroidActivitiesRepository
    extends SqliteRepository<Activity>
    implements ActivitiesRepository {

  private static final String TABLE_NAME         = "activities";
  private static final String COLUMN_TITLE       = "title";
  private static final String COLUMN_DESCRIPTION = "description";
  private static final String COLUMN_COLOR       = "color";
  private static final String COLUMN_CREATED_AT  = "created_at";
  private static final String COLUMN_UPDATED_AT  = "updated_at";

  private static final String[] COLUMNS =
      new String[]{
          COLUMN_ID,
          COLUMN_TITLE,
          COLUMN_DESCRIPTION,
          COLUMN_COLOR,
          COLUMN_CREATED_AT,
          COLUMN_UPDATED_AT};

  @Override
  public void createTable(SQLiteDatabase database) {
    database.execSQL(
        String.format(
            "CREATE TABLE %1$s (" +
            "%2$s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%3$s TEXT," +
            "%4$s TEXT," +
            "%5$s INTEGER," +
            "%6$s REAL," +
            "%7$s REAL)",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_DESCRIPTION,
            COLUMN_COLOR,
            COLUMN_CREATED_AT,
            COLUMN_UPDATED_AT));
  }

  @Override
  public void updateTable(SQLiteDatabase db, int oldVersion, int newVersion) {
    super.updateTable(db, oldVersion, newVersion);
    if (newVersion == 2) {
      db.execSQL(
          String.format("ALTER TABLE %1$s ADD COLUMN %2$s INTEGER DEFAULT 0", getTableName(),
                        COLUMN_COLOR));
    }
  }

  @Override
  protected String getId(Activity object) {
    return String.valueOf(object.getId());
  }

  @Override
  protected ContentValues objectToContentValues(Activity object) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, object.getId());
    values.put(COLUMN_TITLE, object.getTitle());
    values.put(COLUMN_DESCRIPTION, object.getDescription());
    values.put(COLUMN_COLOR, object.getColor());
    values.put(COLUMN_CREATED_AT, object.getCreatedAt());
    values.put(COLUMN_UPDATED_AT, object.getUpdatedAt());

    return values;
  }

  @Override
  protected Activity createObjectFromCursor(Cursor cursor) {
    return
        new Activity(
            cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
            cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
            cursor.getInt(cursor.getColumnIndex(COLUMN_COLOR)),
            cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_AT)),
            cursor.getLong(cursor.getColumnIndex(COLUMN_UPDATED_AT)));
  }

  @Override
  protected String getTableName() {
    return TABLE_NAME;
  }

  @Override
  protected String[] getAllColumns() {
    return COLUMNS;
  }

  @Override
  public List<Activity> getActivitiesSortedByUpdateDate() {
    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        TABLE_NAME,
        COLUMNS,
        null,
        null,
        null,
        null,
        String.format("%1$s DESC", COLUMN_UPDATED_AT));

    List<Activity> result = new ArrayList<>();

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
  public List<Activity> getActivitiesUpdatedSinceDate(long sinceDate) {
    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        TABLE_NAME,
        COLUMNS,
        String.format("%1$s > ?", COLUMN_UPDATED_AT),
        new String[]{String.valueOf(sinceDate)},
        null,
        null,
        String.format("%1$s DESC", COLUMN_UPDATED_AT));

    List<Activity> result = new ArrayList<>();

    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      do {
        result.add(createObjectFromCursor(cursor));
      } while (cursor.moveToNext());
    }

    cursor.close();
    return result;
  }
}
