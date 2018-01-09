package me.veloc1.timetracker.screens.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import me.veloc1.timetracker.TimeTrackerApp;

public abstract class Screen<P extends Presenter<V>, V extends View> {

  private P presenter;

  public Screen(P presenter) {
    this.presenter = presenter;
    TimeTrackerApp.getApplication().inject(presenter);
  }

  @LayoutRes
  public abstract int getViewId();

  protected P getPresenter() {
    return presenter;
  }

  public void setView(View view) {
    presenter.setView((V) view);
  }

  public void start() {
    presenter.onStart();
  }
}
