package me.veloc1.timetracker.routing;

import me.veloc1.timetracker.screens.base.Screen;
import me.veloc1.timetracker.screens.main.MainScreen;

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

  private void startScreen(Screen screen) {
    history.add(screen);

    screenContainer.setScreen(screen);
  }

  public boolean canAppFinish() {
    if (history.size() == 0) {
      return true;
    }
    int lastScreenIndex = history.size() - 1;
    if (!history.get(lastScreenIndex).canGoBack()) {
      return false;
    }

    Screen lastScreen = history.remove(lastScreenIndex);
    screenContainer.setScreen(history.get(lastScreenIndex - 1));
    return false;
  }
}
