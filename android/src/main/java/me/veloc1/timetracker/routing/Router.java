package me.veloc1.timetracker.routing;

import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.screens.activities.ActivitiesScreen;
import me.veloc1.timetracker.screens.add_activity.AddActivityScreen;
import me.veloc1.timetracker.screens.base.Screen;
import me.veloc1.timetracker.screens.edit_activity.EditActivityScreen;
import me.veloc1.timetracker.screens.main.MainScreen;
import me.veloc1.timetracker.screens.track.TrackScreen;

import java.util.LinkedList;
import java.util.List;

public class Router {

  private ScreenContainer screenContainer;
  private List<Screen>    history;

  public Router(ScreenContainer screenContainer) {
    this.screenContainer = screenContainer;

    history = new LinkedList<>();
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

  public void startTrackScreen(int activityId) {
    startScreen(new TrackScreen(activityId));
  }

  public void startTrackScreen(Log log) {
    startScreen(new TrackScreen(log));
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
}
