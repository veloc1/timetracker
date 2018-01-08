package me.veloc1.timetracker.data.actions.base;

import org.junit.Assert;
import org.junit.Test;

public class ActionTest {

  @Test
  public void createAndExecuteAction() {
    Action action = new DummyAction();

    Assert.assertNull(action.getResult());
    action.execute();
    Assert.assertNotNull(action.getResult());
  }

  @Test
  public void executeOnExecutor() {
    ActionExecutor executor = new SingleThreadActionExecutor();

    Action<String>  action     = new DummyAction();
    DummySubscriber subscriber = new DummySubscriber();
    Assert.assertNull(subscriber.result);

    executor.execute(action, subscriber);

    Assert.assertNull(subscriber.error);
    Assert.assertNotNull(subscriber.result);
  }

  private class DummySubscriber implements ActionSubscriber<String> {

    private String    result;
    private Throwable error;

    @Override
    public void onResult(String s) {
      result = s;
    }

    @Override
    public void onError(Throwable throwable) {
      error = throwable;
    }
  }
}
