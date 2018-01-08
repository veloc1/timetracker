package me.veloc1.timetracker.data.actions.base;

public interface ActionSubscriber<RESULT> {
  void onResult(RESULT result);
  void onError(Throwable throwable);
}
