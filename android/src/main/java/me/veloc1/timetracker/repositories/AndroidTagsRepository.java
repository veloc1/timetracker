package me.veloc1.timetracker.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.data.types.Tag;

import java.util.ArrayList;
import java.util.List;

public class AndroidTagsRepository
    extends SqliteRepository<Tag>
    implements TagsRepository {

  private static final String TABLE_NAME   = "tags";
  private static final String COLUMN_TITLE = "title";

  private static final String[] COLUMNS
      = new String[]{
      COLUMN_ID,
      COLUMN_TITLE};

  @Override
  public void createTable(SQLiteDatabase database) {
    database.execSQL(
        String.format(
            "CREATE TABLE %1$s (" +
            "%2$s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%3$s TEXT)",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_TITLE));
  }

  @Override
  protected String getId(Tag object) {
    return String.valueOf(object.getId());
  }

  @Override
  protected ContentValues objectToContentValues(Tag object) {
    ContentValues result = new ContentValues();
    result.put(COLUMN_ID, object.getId());
    result.put(COLUMN_TITLE, object.getTitle());
    return result;
  }

  @Override
  protected Tag createObjectFromCursor(Cursor cursor) {
    return
        new Tag(
            cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
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
  public Tag find(String tag) {
    return findFirst(COLUMN_TITLE, tag);
  }

  @Override
  public List<Tag> getByIds(int[] ids) {
    StringBuilder builder = new StringBuilder();
    for (final int id : ids) {
      builder.append(id);
      builder.append(',');
    }
    builder.deleteCharAt(builder.length() - 1);
    String selectionArgs = builder.toString();

    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        TABLE_NAME,
        COLUMNS,
        String.format("%1$s IN (?)", COLUMN_ID),
        new String[]{selectionArgs},
        null,
        null,
        null);

    List<Tag> result = new ArrayList<>();

    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      do {
        result.add(createObjectFromCursor(cursor));
      } while (cursor.moveToNext());
    }

    cursor.close();
    return result;
  }

  private Tag findFirst(String column, String argument) {
    SQLiteDatabase database = getReadableDatabase();
    Cursor cursor = database.query(
        TABLE_NAME,
        COLUMNS,
        String.format("%1$s = ?", column),
        new String[]{argument},
        null,
        null,
        null);

    if (cursor.getCount() > 0) {
      cursor.moveToFirst();
      Tag result = createObjectFromCursor(cursor);
      cursor.close();

      return result;
    }

    cursor.close();
    return null;
  }
}
