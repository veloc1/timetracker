package me.veloc1.timetracker.intenthandlers;

import android.content.Intent;
import me.veloc1.timetracker.routing.Router;

public interface IntentHandler {

  /**
   * Start screen if supplied intent has needed arguments
   *
   * @param router where this starter will start screen
   * @param intent activity intent
   * @return true if handled
   */
  boolean handle(Router router, Intent intent);
}
