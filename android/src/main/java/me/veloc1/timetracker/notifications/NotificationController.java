package me.veloc1.timetracker.notifications;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import me.veloc1.timetracker.R;

public class NotificationController {

  private final Context                   context;
  private final NotificationManagerCompat notificationManager;

  public NotificationController(Context context) {
    super();

    this.context = context;
    notificationManager = NotificationManagerCompat.from(context);
  }

  public void showLogDuration(String activityTitle, long duration) {
    NotificationCompat.Builder builder = createNotification();

    int durationMinutes = (int) TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
    String notificationMessage =
        context
            .getResources()
            .getQuantityString(R.plurals.tracked_minutes, durationMinutes, durationMinutes);

    builder.setContentTitle(activityTitle);

    if (durationMinutes > 25) {
      builder.setContentText(context.getString(R.string.track_too_long));

      builder.setSubText(notificationMessage);
    } else {
      builder.setContentText(notificationMessage);
    }

    notificationManager.notify(1, builder.build());
  }

  public void showNoLog() {
    NotificationCompat.Builder builder = createNotification();
    builder.setContentTitle(context.getString(R.string.no_track));
    builder.setContentText(context.getString(R.string.notification_track_tip));
    notificationManager.notify(1, builder.build());
  }

  private NotificationCompat.Builder createNotification() {
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(context);

    builder.setSmallIcon(R.mipmap.ic_launcher);
    return builder;
  }
}
