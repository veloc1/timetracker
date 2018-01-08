package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.actions.base.ActionExecutor;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;

public class SingleThreadActionExecutor implements ActionExecutor {
  @Override
  public <RESULT> void execute(Action<RESULT> action, ActionSubscriber<RESULT> subscriber) {
    try {
      action.execute();

      subscriber.onResult(action.getResult());
    } catch (Throwable t) {
      subscriber.onError(t);
    }
  }
}
