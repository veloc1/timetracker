package me.veloc1.timetracker.notifications;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import me.veloc1.timetracker.R;

public class NotificationController {

  private final int notificationId = 1;

  private final Context                    context;
  private final NotificationManagerCompat  notificationManager;
  private final NotificationCompat.Builder notificationBuilder;

  public NotificationController(Context context) {
    super();

    this.context = context;
    notificationManager = NotificationManagerCompat.from(context);
    notificationBuilder = createNotification();
  }

  public void showLogDuration(String activityTitle, long duration) {
    int durationMinutes = (int) TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
    String notificationMessage =
        context
            .getResources()
            .getQuantityString(R.plurals.tracked_minutes, durationMinutes, durationMinutes);

    notificationBuilder.setContentTitle(activityTitle);

    if (durationMinutes > 25) {
      notificationBuilder.setContentText(context.getString(R.string.track_too_long));

      notificationBuilder.setSubText(notificationMessage);
    } else {
      notificationBuilder.setContentText(notificationMessage);
    }

    notificationManager.notify(notificationId, notificationBuilder.build());
  }

  public void showNoLog() {
    notificationBuilder.setContentTitle(context.getString(R.string.no_track));
    notificationBuilder.setContentText(context.getString(R.string.notification_track_tip));
    notificationManager.notify(notificationId, notificationBuilder.build());
  }

  private NotificationCompat.Builder createNotification() {
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(context);

    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setOngoing(true);
    builder.setAutoCancel(false);
    return builder;
  }
}
