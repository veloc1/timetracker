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
}
