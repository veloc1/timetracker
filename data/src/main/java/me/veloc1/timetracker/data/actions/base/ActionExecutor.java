package me.veloc1.timetracker.data.actions.base;

public interface ActionExecutor {
  <RESULT> void execute(Action<RESULT> action, ActionSubscriber<RESULT> subscriber);
}
