package me.veloc1.timetracker.intenthandlers;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import me.veloc1.timetracker.MainActivity;
import me.veloc1.timetracker.TimeTrackerApp;
import me.veloc1.timetracker.data.actions.ChangeLogStatusAction;
import me.veloc1.timetracker.data.types.LogStatus;
import me.veloc1.timetracker.notifications.NotificationController;
import me.veloc1.timetracker.routing.Router;

public class StopTrackIntentHandler implements IntentHandler {
  public static Intent createIntent(Context context, int logId) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(Router.TO_STOP_LOG_ID, logId);
    return intent;
  }

  @Inject
  private NotificationController notificationController;

  @Override
  public boolean handle(Router router, Intent intent) {
    int logId = intent.getIntExtra(Router.TO_STOP_LOG_ID, -1);
    if (logId > 0) {
      // TODO: 28.01.2018 move this to different place. Currently I didn't sure that this is
      // logical place for this statement
      ChangeLogStatusAction action = new ChangeLogStatusAction(logId, LogStatus.DONE);
      TimeTrackerApp.getApplication().inject(action);
      action.execute();

      TimeTrackerApp.getApplication().inject(this);
      notificationController.showNoLog();

      router.startMainScreen();

      return true;
    }
    return false;
  }
}
