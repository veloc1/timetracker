package me.veloc1.timetracker.screens.base;

import android.view.View;
import me.veloc1.timetracker.TimeTrackerApp;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.actions.base.ActionExecutor;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;

import javax.inject.Inject;

public class Presenter<V extends View> {
  private   V              view;
  @Inject
  protected ActionExecutor actionExecutor;

  public void onStart() {
  }

  protected <R> void execute(Action<R> action, ActionSubscriber<R> subscriber) {
    // this place isn't great
    TimeTrackerApp.getApplication().inject(action);

    actionExecutor.execute(action, subscriber);
  }

  protected V getView() {
    return view;
  }

  public void setView(V view) {
    this.view = view;
  }
}
