package me.veloc1.timetracker.screens.edit_activity;

import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.base.Screen;

public class EditActivityScreen extends Screen<EditActivityPresenter, EditActivityView> {
  public EditActivityScreen(int activityId) {
    super(new EditActivityPresenter(activityId));
  }

  @Override
  public int getViewId() {
    return R.layout.screen_edit_activity;
  }
}
