package me.veloc1.timetracker.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import me.veloc1.timetracker.TimeTrackerApp;
import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.GetActivityAction;
import me.veloc1.timetracker.data.actions.GetCurrentInProgressLogAction;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.notifications.NotificationController;

public class NotificationService extends Service {

  private Handler                uiHandler;
  private NotificationController notificationController;

  @Inject
  private TimeProvider timeProvider;

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    notificationController = new NotificationController(this);

    TimeTrackerApp.getApplication().inject(this);

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new RefreshStateRunnable(), 0, 10, TimeUnit.SECONDS);

    return START_STICKY;
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  private class RefreshStateRunnable implements Runnable {

    @Override
    public void run() {
      final Log      currentLog      = getCurrentLog();
      final Activity currentActivity = getActivityByLog(currentLog);

      uiHandler = new Handler(NotificationService.this.getMainLooper());
      uiHandler.post(new Runnable() {

        @Override
        public void run() {
          if (currentLog == null) {
            notificationController.showNoLog();
          } else {
            long duration = timeProvider.getCurrentTimeInMillis() - currentLog.getStartDate();
            notificationController.showLogDuration(currentActivity.getTitle(), duration);
          }
        }
      });
    }

    private Log getCurrentLog() {
      GetCurrentInProgressLogAction action = new GetCurrentInProgressLogAction();
      // TODO: 17.01.2018 make executor for actions
      TimeTrackerApp.getApplication().inject(action);
      action.execute();
      return action.getResult();
    }

    private Activity getActivityByLog(Log log) {
      if (log == null) {
        return null;
      }
      GetActivityAction action = new GetActivityAction(log.getActivityId());
      TimeTrackerApp.getApplication().inject(action);
      action.execute();
      return action.getResult();
    }
  }
}
