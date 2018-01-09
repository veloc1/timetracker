package me.veloc1.timetracker.di;

import android.content.Context;
import me.veloc1.timetracker.AndroidBackgroundActionExecutor;
import me.veloc1.timetracker.data.actions.base.ActionExecutor;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.repositories.AndroidActivitiesRepository;
import org.codejargon.feather.Provides;

import javax.inject.Singleton;

public class ApplicationModule {
  private Context context;

  public ApplicationModule(Context context) {
    super();
    this.context = context;
  }

  @Provides
  @Singleton
  ActionExecutor actionExecutor() {
    return new AndroidBackgroundActionExecutor(context);
  }

  @Provides
  @Singleton
  ActivitiesRepository activitiesRepository() {
    return new AndroidActivitiesRepository();
  }
}
