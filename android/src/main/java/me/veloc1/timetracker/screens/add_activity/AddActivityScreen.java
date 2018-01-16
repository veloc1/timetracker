package me.veloc1.timetracker.screens.add_activity;

import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.base.Screen;

public class AddActivityScreen extends Screen<AddActivityPresenter, AddActivityView> {
  public AddActivityScreen() {
    super(new AddActivityPresenter());
  }

  @Override
  public int getViewId() {
    return R.layout.screen_add_activity;
  }
}
