package me.veloc1.timetracker.data.actions.base;

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
