package me.veloc1.timetracker.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DurationFormatter {

  public static final long SHORT_FORMAT_LIMIT = 1000 * 60 * 60;

  public final DateFormat shortFormat;
  public final DateFormat longFormat;

  public DurationFormatter() {
    super();
    shortFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
    longFormat = new SimpleDateFormat("kk:mm:ss", Locale.getDefault());

    setTimezoneToZeroOffset();
  }

  private void setTimezoneToZeroOffset() {
    String[] timezoneIds  = TimeZone.getAvailableIDs(0);
    TimeZone zeroTimezone = TimeZone.getTimeZone(timezoneIds[0]);
    shortFormat.setTimeZone(zeroTimezone);
    longFormat.setTimeZone(zeroTimezone);
  }

  public String getFormattedString(long duration) {
    if (duration > SHORT_FORMAT_LIMIT) {
      return longFormat.format(duration);
    }
    return shortFormat.format(duration);
  }
}
