package me.veloc1.timetracker.routing;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import me.veloc1.timetracker.intenthandlers.IntentHandler;
import me.veloc1.timetracker.intenthandlers.NoArgIntentHandler;
import me.veloc1.timetracker.intenthandlers.StopTrackIntentHandler;
import me.veloc1.timetracker.intenthandlers.TrackScreenIntentHandler;
import me.veloc1.timetracker.screens.activities.ActivitiesScreen;
import me.veloc1.timetracker.screens.add_activity.AddActivityScreen;
import me.veloc1.timetracker.screens.base.Screen;
import me.veloc1.timetracker.screens.edit_activity.EditActivityScreen;
import me.veloc1.timetracker.screens.main.MainScreen;
import me.veloc1.timetracker.screens.track.TrackScreen;

public class Router {

  public static final String LOG_ID      = "log_id";
  public static final String ACTIVITY_ID = "activity_id";

  public static final String TO_STOP_LOG_ID = "log_id_to_stop";

  private ScreenContainer     screenContainer;
  private List<Screen>        history;
  private List<IntentHandler> intentHandlers;

  public Router(ScreenContainer screenContainer) {
    this.screenContainer = screenContainer;

    history = new LinkedList<>();
    createStarters();
  }

  public void start(Intent intent) {
    clearHistory();
    for (final IntentHandler intentHandler : intentHandlers) {
      if (intentHandler.handle(this, intent)) {
        break;
      }
    }
  }

  public void startMainScreen() {
    startScreen(new MainScreen());
  }

  public void startAddActivitiesScreen() {
    startScreen(new AddActivityScreen());
  }

  public void startActivitiesListScreen() {
    startScreen(new ActivitiesScreen());
  }

  public void startActivityEditScreen(int activityId) {
    startScreen(new EditActivityScreen(activityId));
  }

  public void startNewTrackScreen(int activityId) {
    startScreen(new TrackScreen(activityId));
  }

  public void startTrackScreen(int logId, int activityId) {
    startScreen(new TrackScreen(logId, activityId));
  }

  private void startScreen(Screen screen) {
    if (history.size() > 0) {
      history.get(history.size() - 1).stop();
    }

    history.add(screen);

    screen.setRouter(this);

    screenContainer.setScreen(screen);
    screen.start();
  }

  public boolean canHandleBackPress() {
    // should not happen
    if (history.size() == 0) {
      return false;
    }
    // screen can intercept this event
    int lastScreenIndex = history.size() - 1;
    if (history.get(lastScreenIndex).canHandleBackPress()) {
      return true;
    }
    // if has last screen in stack and previous conditions is ok -> close app
    if (history.size() == 1) {
      return false;
    }
    return true;
  }

  public void handleBackPress() {
    int lastScreenIndex = history.size() - 1;
    if (history.get(lastScreenIndex).canHandleBackPress()) {
      history.get(lastScreenIndex).handleBackPress();
    } else {
      Screen lastScreen = history.remove(lastScreenIndex);
      Screen previous   = history.get(lastScreenIndex - 1);

      lastScreen.stop();
      screenContainer.setScreen(previous);
      previous.start();
    }
  }

  public int getHistoryLength() {
    return history.size();
  }

  private void createStarters() {
    intentHandlers = new LinkedList<>();

    intentHandlers.add(new StopTrackIntentHandler());
    intentHandlers.add(new TrackScreenIntentHandler());
    intentHandlers.add(new NoArgIntentHandler());
  }

  private void clearHistory() {
    history.clear();
  }
}
