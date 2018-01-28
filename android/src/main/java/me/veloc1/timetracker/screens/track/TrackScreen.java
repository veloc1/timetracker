package me.veloc1.timetracker.screens.track;

import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.base.Screen;

public class TrackScreen extends Screen<TrackPresenter, TrackView> {
  public TrackScreen(int activityId) {
    super(new TrackPresenter(activityId));
  }

  public TrackScreen(int logId, int activityId) {
    super(new TrackPresenter(logId, activityId));
  }

  @Override
  public int getViewId() {
    return R.layout.screen_track;
  }
}
