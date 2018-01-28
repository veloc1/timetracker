package me.veloc1.timetracker.intenthandlers;

import android.content.Context;
import android.content.Intent;
import me.veloc1.timetracker.MainActivity;
import me.veloc1.timetracker.routing.Router;

public class NoArgIntentHandler implements IntentHandler {

  public static Intent createIntent(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    return intent;
  }

  @Override
  public boolean handle(Router router, Intent intent) {
    router.startMainScreen();
    return true;
  }
}
