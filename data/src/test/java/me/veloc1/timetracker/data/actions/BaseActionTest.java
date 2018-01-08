package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.actions.base.ActionExecutor;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import org.junit.Before;

public class BaseActionTest {

  private ActionExecutor executor;

  @Before
  public void setUp() {
    executor = new SingleThreadActionExecutor();
  }

  protected <T> T execute(Action<T> action) throws Throwable {
    Subscriber<T> subscriber = new Subscriber<>();
    getExecutor().execute(action, subscriber);
    if (subscriber.isSuccessful()) {
      return subscriber.result;
    }
    throw subscriber.error;
  }

  protected ActionExecutor getExecutor() {
    return executor;
  }

  private class Subscriber<T> implements ActionSubscriber<T> {

    private T         result;
    private Throwable error;

    @Override
    public void onResult(T t) {
      result = t;
    }

    @Override
    public void onError(Throwable throwable) {
      error = throwable;
    }

    public boolean isSuccessful() {
      return error == null;
    }
  }
}
