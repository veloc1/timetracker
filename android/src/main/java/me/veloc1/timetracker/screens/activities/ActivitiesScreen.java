package me.veloc1.timetracker.screens.activities;

import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.base.Screen;

public class ActivitiesScreen extends Screen<ActivitiesPresenter, ActivitiesView> {
  public ActivitiesScreen() {
    super(new ActivitiesPresenter());
  }

  @Override
  public int getViewId() {
    return R.layout.screen_activities;
  }
}
