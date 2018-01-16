package me.veloc1.timetracker.screens.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import me.veloc1.timetracker.TimeTrackerApp;
import me.veloc1.timetracker.routing.Router;

public abstract class Screen<P extends Presenter<V>, V extends View> {

  private P      presenter;
  private Router router;
  private View   view;

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
    this.view = view;
    presenter.setView((V) view);
  }

  public View getView() {
    return view;
  }

  public void start() {
    presenter.onStart();
  }

  public void stop() {
    presenter.onStop();
  }

  public boolean canHandleBackPress() {
    return false;
  }

  public void handleBackPress() {
  }

  public void setRouter(Router router) {
    this.router = router;
    presenter.setRouter(router);
  }
}
