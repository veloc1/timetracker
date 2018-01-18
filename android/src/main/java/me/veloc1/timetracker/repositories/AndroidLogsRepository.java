package me.veloc1.timetracker.repositories;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

public class AndroidLogsRepository
    extends SqliteRepository<Log>
    implements LogsRepository {

  private static final String TABLE_NAME         = "logs";
  private static final String COLUMN_DESCRIPTION = "description";
  private static final String COLUMN_STATUS      = "status";
  private static final String COLUMN_ACTIVITY_ID = "activity_id";
  private static final String COLUMN_START_DATE  = "start_date";
  private static final String COLUMN_END_DATE    = "end_date";

  private static final String[] COLUMNS =
      new String[]{
          COLUMN_ID,
          COLUMN_DESCRIPTION,
          COLUMN_STATUS,
          COLUMN_ACTIVITY_ID,
          COLUMN_START_DATE,
          COLUMN_END_DATE};

  @Override
  public void createTable(SQLiteDatabase database) {
    database.execSQL(
        String.format(
            "CREATE TABLE %1$s (" +
            "%2$s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%3$s TEXT," +
            "%4$s INTEGER," +
            "%5$s INTEGER," +
            "%6$s REAL," +
            "%7$s REAL)",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_DESCRIPTION,
            COLUMN_STATUS,
            COLUMN_ACTIVITY_ID,
            COLUMN_START_DATE,
            COLUMN_END_DATE));
  }

  @Override
  protected String getId(Log object) {
    return String.valueOf(object.getId());
  }

  @Override
  protected ContentValues objectToContentValues(Log object) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, object.getId());
    values.put(COLUMN_DESCRIPTION, object.getDescription());
    values.put(COLUMN_STATUS, object.getStatus().ordinal());
    values.put(COLUMN_ACTIVITY_ID, object.getActivityId());
    values.put(COLUMN_START_DATE, object.getStartDate());
    values.put(COLUMN_END_DATE, object.getEndDate());

    return values;
  }

  @Override
  protected Log createObjectFromCursor(Cursor cursor) {
    return
        new Log(
            cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
            LogStatus.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS))],
            cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITY_ID)),
            cursor.getLong(cursor.getColumnIndex(COLUMN_START_DATE)),
            cursor.getLong(cursor.getColumnIndex(COLUMN_END_DATE)));
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
  public List<Log> getLogsWithStatus(LogStatus status) {
    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        TABLE_NAME,
        COLUMNS,
        String.format("%1$s = ?", COLUMN_STATUS),
        new String[]{String.valueOf(status.ordinal())},
        null,
        null,
        null);

    List<Log> result = new ArrayList<>();

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
  public List<Log> getLogsByActivityId(int activityId) {
    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        TABLE_NAME,
        COLUMNS,
        String.format("%1$s = ?", COLUMN_ACTIVITY_ID),
        new String[]{String.valueOf(activityId)},
        null,
        null,
        null);

    List<Log> result = new ArrayList<>();

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
