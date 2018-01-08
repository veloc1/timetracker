package me.veloc1.timetracker.data;

import java.util.Calendar;
import java.util.Date;

public class TimeProvider {

  public long getCurrentTimeInMillis() {
    return Calendar.getInstance().getTimeInMillis();
  }

  public Date getCurrentTime() {
    return Calendar.getInstance().getTime();
  }
}
