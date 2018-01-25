package me.veloc1.timetracker.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.Repository;

public abstract class SqliteRepository<T> implements Repository<T> {

  protected static final String COLUMN_ID = "id";

  private TimeTrackerSqliteOpenHelper sqliteOpenHelper;

  public SqliteRepository() {
    super();
  }

  public abstract void createTable(SQLiteDatabase database);

  public void updateTable(SQLiteDatabase db, int oldVersion, int newVersion) {
  }

  // I've created this method, because I can't use encapsulation. We can store any object, not
  // only objects inherited from BaseModel
  protected abstract String getId(T object);

  protected abstract ContentValues objectToContentValues(T object);

  protected abstract T createObjectFromCursor(Cursor cursor);

  protected abstract String getTableName();
  // This can be replaced with reflection and annotation parsing

  protected abstract String[] getAllColumns();

  @Override
  public int add(T objectToCreate) {
    SQLiteDatabase database = getWritableDatabase();

    ContentValues values = objectToContentValues(objectToCreate);
    values.remove(COLUMN_ID);

    int id = (int) database.insert(getTableName(), null, values);
    database.close();

    return id;
  }

  @Override
  public void update(T objectToUpdate) {
    SQLiteDatabase database = getWritableDatabase();

    ContentValues values = objectToContentValues(objectToUpdate);
    database
        .update(
            getTableName(),
            values,
            String.format("%1$s = ?", COLUMN_ID),
            new String[]{getId(objectToUpdate)});

    database.close();
  }

  @Override
  public T getById(int id) {
    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        getTableName(),
        getAllColumns(),
        String.format("%1$s = ?", COLUMN_ID),
        new String[]{String.valueOf(id)},
        null,
        null,
        null);

    T result = null;
    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      result = createObjectFromCursor(cursor);
    }
    cursor.close();
    return result;
  }

  @Override
  public void remove(int id) {
    SQLiteDatabase database = getWritableDatabase();

    database
        .delete(
            getTableName(),
            String.format("%1$s = ?", COLUMN_ID),
            new String[]{String.valueOf(id)});
  }

  protected SQLiteDatabase getReadableDatabase() {
    return sqliteOpenHelper.getReadableDatabase();
  }

  protected SQLiteDatabase getWritableDatabase() {
    return sqliteOpenHelper.getWritableDatabase();
  }

  public void setSqliteOpenHelper(TimeTrackerSqliteOpenHelper sqliteOpenHelper) {
    this.sqliteOpenHelper = sqliteOpenHelper;
  }
}
