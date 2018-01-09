package me.veloc1.timetracker.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

import java.util.ArrayList;
import java.util.List;

public class AndroidActivitiesRepository extends SqliteRepository implements ActivitiesRepository {

  private static final String TABLE_NAME         = "activities";
  private static final String COLUMN_ID          = "id";
  private static final String COLUMN_TITLE       = "title";
  private static final String COLUMN_DESCRIPTION = "description";
  private static final String COLUMN_CREATED_AT  = "created_at";
  private static final String COLUMN_UPDATED_AT  = "updated_at";

  private static final String[] COLUMNS =
      new String[]{
          COLUMN_ID,
          COLUMN_TITLE,
          COLUMN_DESCRIPTION,
          COLUMN_CREATED_AT,
          COLUMN_UPDATED_AT};

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
        COLUMN_UPDATED_AT);

    List<Activity> result = new ArrayList<>();

    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      do {
        result.add(
            new Activity(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3),
                cursor.getLong(4)));
      } while (cursor.moveToNext());
    }

    return result;
  }

  @Override
  public Activity add(Activity objectToCreate) {
    SQLiteDatabase database = getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_TITLE, objectToCreate.getTitle());
    values.put(COLUMN_DESCRIPTION, objectToCreate.getDescription());
    values.put(COLUMN_CREATED_AT, objectToCreate.getCreatedAt());
    values.put(COLUMN_UPDATED_AT, objectToCreate.getUpdatedAt());

    int id = (int) database.insert(TABLE_NAME, null, values);
    database.close();

    return new Activity(
        id,
        objectToCreate.getTitle(),
        objectToCreate.getDescription(),
        objectToCreate.getCreatedAt(),
        objectToCreate.getUpdatedAt()
    );
  }

  @Override
  public Activity update(Activity objectToUpdate) {
    SQLiteDatabase database = getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(COLUMN_ID, objectToUpdate.getId());
    values.put(COLUMN_TITLE, objectToUpdate.getTitle());
    values.put(COLUMN_DESCRIPTION, objectToUpdate.getDescription());
    values.put(COLUMN_CREATED_AT, objectToUpdate.getCreatedAt());
    values.put(COLUMN_UPDATED_AT, objectToUpdate.getUpdatedAt());

    int id = (int) database.insert(TABLE_NAME, null, values);
    database.close();

    return new Activity(
        id,
        objectToUpdate.getTitle(),
        objectToUpdate.getDescription(),
        objectToUpdate.getCreatedAt(),
        objectToUpdate.getUpdatedAt()
    );
  }

  @Override
  public Activity getById(int id) {
    return null;
  }

  @Override
  public void createTable(SQLiteDatabase database) {
    database.execSQL(
        String.format(
            "CREATE TABLE %1$s (" +
            "%2$s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%3$s TEXT," +
            "%4$s TEXT," +
            "%5$s REAL," +
            "%6$s REAL)",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_DESCRIPTION,
            COLUMN_CREATED_AT,
            COLUMN_UPDATED_AT));
  }
}
