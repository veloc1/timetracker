package me.veloc1.timetracker.screens.main;

import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.base.Screen;

public class MainScreen extends Screen<MainPresenter, MainView> {
  public MainScreen() {
    super(new MainPresenter());
  }

  @Override
  public int getViewId() {
    return R.layout.screen_main;
  }

  @Override
  public boolean canHandleBackPress() {
    return getPresenter().canHandleBackPress();
  }

  @Override
  public void handleBackPress() {
    super.handleBackPress();
    getPresenter().handleBackPress();
  }
}
