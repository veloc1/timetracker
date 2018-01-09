package me.veloc1.timetracker;

import android.app.Application;
import me.veloc1.timetracker.di.ApplicationModule;
import me.veloc1.timetracker.di.DatabaseModule;
import me.veloc1.timetracker.repositories.AndroidActivitiesRepository;
import me.veloc1.timetracker.repositories.AndroidTagToActivityRepository;
import me.veloc1.timetracker.repositories.AndroidTagsRepository;
import me.veloc1.timetracker.repositories.SqliteRepository;
import me.veloc1.timetracker.repositories.TimeTrackerSqliteOpenHelper;
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
    initDatabase(activitiesRepository, tagsRepository, tagToActivityRepository);

    sInstance = this;

    injector = Feather.with(
        new ApplicationModule(this),
        new DatabaseModule(
            activitiesRepository,
            tagsRepository,
            tagToActivityRepository));
  }

  private void initDatabase(
      AndroidActivitiesRepository activitiesRepository,
      AndroidTagsRepository tagsRepository,
      AndroidTagToActivityRepository tagToActivityRepository) {
    new TimeTrackerSqliteOpenHelper(
        this,
        new SqliteRepository[]{
            activitiesRepository,
            tagsRepository,
            tagToActivityRepository
        }
    );
  }

  public void inject(Object object) {
    injector.injectFields(object);
  }

  public static TimeTrackerApp getApplication() {
    return sInstance;
  }
}
