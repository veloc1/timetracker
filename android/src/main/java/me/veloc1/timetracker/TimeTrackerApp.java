package me.veloc1.timetracker;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import me.veloc1.timetracker.di.ApplicationModule;
import me.veloc1.timetracker.di.DatabaseModule;
import me.veloc1.timetracker.repositories.AndroidActivitiesRepository;
import me.veloc1.timetracker.repositories.AndroidLogsRepository;
import me.veloc1.timetracker.repositories.AndroidTagToActivityRepository;
import me.veloc1.timetracker.repositories.AndroidTagsRepository;
import me.veloc1.timetracker.repositories.SqliteRepository;
import me.veloc1.timetracker.repositories.TimeTrackerSqliteOpenHelper;
import me.veloc1.timetracker.services.NotificationService;
import org.codejargon.feather.Feather;

public class TimeTrackerApp extends Application {

  private static TimeTrackerApp sInstance;

  private Feather injector;

  @Override
  public void onCreate() {
    super.onCreate();

    AndroidActivitiesRepository    activitiesRepository    = new AndroidActivitiesRepository();
    AndroidTagsRepository          tagsRepository          = new AndroidTagsRepository();
    AndroidTagToActivityRepository tagToActivityRepository = new AndroidTagToActivityRepository();
    AndroidLogsRepository          logsRepository          = new AndroidLogsRepository();
    initDatabase(activitiesRepository, tagsRepository, tagToActivityRepository, logsRepository);

    sInstance = this;

    injector = Feather.with(
        new ApplicationModule(this),
        new DatabaseModule(
            activitiesRepository,
            tagsRepository,
            tagToActivityRepository,
            logsRepository));

    initAlarmManager();
  }

  private void initDatabase(
      AndroidActivitiesRepository activitiesRepository,
      AndroidTagsRepository tagsRepository,
      AndroidTagToActivityRepository tagToActivityRepository,
      AndroidLogsRepository logsRepository) {

    new TimeTrackerSqliteOpenHelper(
        this,
        new SqliteRepository[]{
            activitiesRepository,
            tagsRepository,
            tagToActivityRepository,
            logsRepository
        }
    );
  }

  private void initAlarmManager() {
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    Intent        intent        = new Intent(this, NotificationService.class);
    PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
    alarmManager
        .setInexactRepeating(
            AlarmManager.RTC,
            0,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent);
  }

  public void inject(Object object) {
    injector.injectFields(object);
  }

  public static TimeTrackerApp getApplication() {
    return sInstance;
  }
}
