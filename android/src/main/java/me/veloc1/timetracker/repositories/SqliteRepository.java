package me.veloc1.timetracker.repositories;

import android.database.sqlite.SQLiteDatabase;

public abstract class SqliteRepository {

  private TimeTrackerSqliteOpenHelper sqliteOpenHelper;

  public SqliteRepository() {
    super();
  }

  public abstract void createTable(SQLiteDatabase database);

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
