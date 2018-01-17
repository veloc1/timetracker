package me.veloc1.timetracker.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.TimeTrackerApp;
import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.GetActivityAction;
import me.veloc1.timetracker.data.actions.GetCurrentInProgressLogAction;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Log;

public class NotificationService extends Service {

  private NotificationManagerCompat notificationManager;
  private Handler                   uiHandler;

  @Inject
  private TimeProvider timeProvider;

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    android.util.Log.e(getClass().getSimpleName(), "start");
    return START_STICKY;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    TimeTrackerApp.getApplication().inject(this);
    notificationManager = NotificationManagerCompat.from(this);

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(new RefreshStateRunnable(), 0, 1, TimeUnit.MINUTES);
    // TODO: 17.01.2018 change schedule when no activity tracking
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
          NotificationCompat.Builder builder =
              new NotificationCompat.Builder(NotificationService.this);

          builder.setSmallIcon(R.mipmap.ic_launcher);

          if (currentLog == null) {
            builder.setContentTitle(getString(R.string.no_track));
            builder.setContentText(getString(R.string.notification_track_tip));
          } else {
            long duration = timeProvider.getCurrentTimeInMillis() - currentLog.getStartDate();
            int  minutes  = (int) TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);

            if (minutes > 25) {
              builder.setContentTitle(currentActivity.getTitle());
              builder.setContentText(getString(R.string.track_too_long));
            } else {
              builder.setContentTitle(currentActivity.getTitle());
              String notificationMessage =
                  getResources()
                      .getQuantityString(R.plurals.tracked_minutes, minutes, minutes);
              builder.setContentText(notificationMessage);
            }
          }

          notificationManager.notify(1, builder.build());
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
