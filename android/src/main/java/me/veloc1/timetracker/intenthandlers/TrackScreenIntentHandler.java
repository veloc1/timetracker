package me.veloc1.timetracker.intenthandlers;

import android.content.Context;
import android.content.Intent;
import me.veloc1.timetracker.MainActivity;
import me.veloc1.timetracker.routing.Router;

public class TrackScreenIntentHandler implements IntentHandler {

  public static Intent createIntent(Context context, int logId, int activityId) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(Router.LOG_ID, logId);
    intent.putExtra(Router.ACTIVITY_ID, activityId);
    return intent;
  }

  @Override
  public boolean handle(Router router, Intent intent) {
    int logId      = intent.getIntExtra(Router.LOG_ID, -1);
    int activityId = intent.getIntExtra(Router.ACTIVITY_ID, -1);
    if (logId > 0) {
      router.startMainScreen();
      router.startTrackScreen(logId, activityId);
      return true;
    }
    return false;
  }
}
