package me.veloc1.timetracker.di;

import javax.inject.Singleton;

import android.content.Context;
import me.veloc1.timetracker.AndroidBackgroundActionExecutor;
import me.veloc1.timetracker.data.actions.base.ActionExecutor;
import me.veloc1.timetracker.notifications.NotificationController;
import org.codejargon.feather.Provides;

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
  NotificationController notificationController() {
    return new NotificationController(context);
  }
}
