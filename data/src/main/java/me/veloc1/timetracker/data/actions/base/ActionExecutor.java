package me.veloc1.timetracker.data.actions.base;

public interface ActionExecutor {
  /**
   * Executes an action and returns result to subscriber
   * @param action action to execute
   * @param subscriber subscriber, which will handle action result
   * @param <RESULT> return type of action
   */
  <RESULT> void execute(Action<RESULT> action, ActionSubscriber<RESULT> subscriber);
}
