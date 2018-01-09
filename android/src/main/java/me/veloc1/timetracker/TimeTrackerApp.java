package me.veloc1.timetracker;

import android.app.Application;
import me.veloc1.timetracker.di.ApplicationModule;
import org.codejargon.feather.Feather;

public class TimeTrackerApp extends Application {

  private static TimeTrackerApp sInstance;

  private Feather injector;

  @Override
  public void onCreate() {
    super.onCreate();
    sInstance = this;

    injector = Feather.with(new ApplicationModule(this));
  }

  public void inject(Object object) {
    injector.injectFields(object);
  }

  public static TimeTrackerApp getApplication() {
    return sInstance;
  }
}
