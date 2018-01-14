package me.veloc1.timetracker.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeTrackerSqliteOpenHelper extends SQLiteOpenHelper {

  private static final String NAME    = "timetracker.db";
  private static final int    VERSION = 1; // initial setup
  private final SqliteRepository[] repositories;

  public TimeTrackerSqliteOpenHelper(Context context, SqliteRepository[] repositories) {
    super(context, NAME, null, VERSION);
    this.repositories = repositories;

    // triggers onCreate and onUpdate
    getWritableDatabase().close();

    for (final SqliteRepository repository : repositories) {
      repository.setSqliteOpenHelper(this);
    }
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    for (final SqliteRepository repository : repositories) {
      repository.createTable(db);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // TODO: 09.01.2018 migrations
  }
}
