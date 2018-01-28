package me.veloc1.timetracker.notifications;

import java.util.concurrent.TimeUnit;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.intenthandlers.NoArgIntentHandler;
import me.veloc1.timetracker.intenthandlers.StopTrackIntentHandler;
import me.veloc1.timetracker.intenthandlers.TrackScreenIntentHandler;

public class NotificationController {

  private static final int CODE_OPEN_APP   = 20001;
  private static final int CODE_OPEN_TRACK = 20002;
  private static final int CODE_STOP_TRACK = 20003;

  private final int notificationId = 1;

  private final Context context;

  private final NotificationManagerCompat  notificationManager;
  private final NotificationCompat.Builder notificationBuilder;

  public NotificationController(Context context) {
    super();

    this.context = context;
    notificationManager = NotificationManagerCompat.from(context);
    notificationBuilder = createNotification();
  }

  public void showLogDuration(int logId, int activityId, String activityTitle, long duration) {
    int durationMinutes = (int) TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
    int durationHours   = (int) TimeUnit.HOURS.convert(duration, TimeUnit.MILLISECONDS);

    String    notificationMessage = null;
    Resources resources           = context.getResources();
    if (durationHours < 1) {
      notificationMessage =
          resources
              .getQuantityString(
                  R.plurals.tracked_minutes,
                  durationMinutes,
                  durationMinutes);
    } else {
      notificationMessage =
          resources
              .getQuantityString(
                  R.plurals.tracked_hours,
                  durationHours,
                  durationHours);
    }

    notificationBuilder.setContentTitle(activityTitle);

    StringBuilder bigMessage = new StringBuilder();

    bigMessage.append(notificationMessage);

    if (durationMinutes > 25) {
      bigMessage.append('\n');

      String tooLongAlert = context.getString(R.string.track_too_long);
      bigMessage.append(tooLongAlert);
    }

    notificationBuilder.setContentText(bigMessage.toString());
    notificationBuilder
        .setStyle(
            new NotificationCompat
                .BigTextStyle()
                .bigText(bigMessage.toString()));

    notificationBuilder.mActions.clear();

    Intent openActivity = TrackScreenIntentHandler.createIntent(context, logId, activityId);
    PendingIntent openIntent =
        PendingIntent
            .getActivity(
                context,
                CODE_OPEN_TRACK,
                openActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);
    notificationBuilder.addAction(0, context.getString(R.string.action_open), openIntent);

    Intent stopActivity = StopTrackIntentHandler.createIntent(context, logId);
    PendingIntent stopIntent =
        PendingIntent
            .getActivity(
                context,
                CODE_STOP_TRACK,
                stopActivity,
                PendingIntent.FLAG_UPDATE_CURRENT);
    notificationBuilder.addAction(0, context.getString(R.string.action_stop), stopIntent);

    notificationManager.notify(notificationId, notificationBuilder.build());
  }

  public void showNoLog() {
    notificationBuilder.setContentTitle(context.getString(R.string.no_track));

    String notificationMessage = context.getString(R.string.notification_track_tip);
    notificationBuilder.setContentText(notificationMessage);
    notificationBuilder
        .setStyle(
            new NotificationCompat
                .BigTextStyle()
                .bigText(notificationMessage));

    notificationBuilder.mActions.clear();

    notificationManager.notify(notificationId, notificationBuilder.build());
  }

  private NotificationCompat.Builder createNotification() {
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(context);

    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setOngoing(true);
    builder.setAutoCancel(false);

    addIntent(builder);
    return builder;
  }

  private void addIntent(NotificationCompat.Builder builder) {
    Intent resultIntent = NoArgIntentHandler.createIntent(context);

    PendingIntent resultPendingIntent =
        PendingIntent
            .getActivity(
                context,
                CODE_OPEN_APP,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

    builder.setContentIntent(resultPendingIntent);
  }
}
