package me.veloc1.timetracker;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.actions.base.ActionExecutor;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * When you look into an abyss, the abyss also looks into you.
 * Friedrich Nietzsche
 */
public class AndroidBackgroundActionExecutor implements ActionExecutor {

  private final ExecutorService executorService;
  private final ActionHandler   handler;

  public AndroidBackgroundActionExecutor(Context context) {
    super();
    executorService = Executors.newCachedThreadPool();

    handler = new ActionHandler(context.getMainLooper());
  }

  @Override
  public <RESULT> void execute(Action<RESULT> action, ActionSubscriber<RESULT> subscriber) {
    ActionCallback callback = new ActionCallback();

    Future<RESULT> resultFuture = executorService.submit(new ActionCallable<>(action, callback));
    handler.addFuture(resultFuture, subscriber);
  }

  private class ActionCallable<R> implements Callable<R> {
    private final Action<R>      action;
    private final ActionCallback callback;

    public ActionCallable(Action<R> action, ActionCallback callback) {
      super();
      this.action = action;
      this.callback = callback;
    }

    @Override
    public R call() throws Exception {
      try {
        action.execute();
        callback.onComplete();
        return action.getResult();
      } catch (Throwable t) {
        callback.onComplete();
        throw t;
      }
    }
  }

  private class ActionHandler extends Handler {

    private List<Pair<Future, ActionSubscriber>> futures;

    public ActionHandler(Looper looper) {
      super(looper);
      futures = new ArrayList<>();
    }

    private <RESULT> void addFuture(Future<RESULT> future, ActionSubscriber<RESULT> subscriber) {
      futures.add(new Pair<Future, ActionSubscriber>(future, subscriber));
    }

    private void futureComplete() {
      post(new Runnable() {

        @Override
        public void run() {
          for (int i = 0; i < futures.size(); i++) {
            Pair<Future, ActionSubscriber> pair = futures.get(i);

            Future           future     = pair.first;
            ActionSubscriber subscriber = pair.second;
            if (future.isDone()) {
              try {
                Object result = future.get();
                subscriber.onResult(result);
              } catch (Throwable t) {
                subscriber.onError(t);
              }
              futures.remove(i);
              i--;
            }
          }
        }
      });
    }
  }

  private class ActionCallback {
    public void onComplete() {
      handler.futureComplete();
    }
  }
}
