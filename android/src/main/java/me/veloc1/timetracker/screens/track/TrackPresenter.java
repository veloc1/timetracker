package me.veloc1.timetracker.screens.track;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import me.veloc1.timetracker.data.DurationFormatter;
import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.ChangeLogStatusAction;
import me.veloc1.timetracker.data.actions.CreateLogAction;
import me.veloc1.timetracker.data.actions.GetActivityAction;
import me.veloc1.timetracker.data.actions.GetLogAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;
import me.veloc1.timetracker.notifications.NotificationController;
import me.veloc1.timetracker.screens.base.Presenter;

public class TrackPresenter extends Presenter<TrackView> {

  private final int activityId;
  private final int logId;

  private Log log;

  private ScheduledExecutorService executorService;

  @Inject
  private TimeProvider      timeProvider;
  @Inject
  private DurationFormatter durationFormatter;

  @Inject
  private NotificationController notificationController;

  /**
   * This constructor creates new {@link Log} object, and start track immediately
   */
  public TrackPresenter(int activityId) {
    super();
    this.activityId = activityId;
    this.logId = -1;
  }

  /**
   * This constructor uses already created {@link Log} object
   */
  public TrackPresenter(int logId, int activityId) {
    super();
    this.activityId = activityId;
    this.logId = logId;
  }

  @Override
  public void onStart() {
    super.onStart();
    executorService = Executors.newSingleThreadScheduledExecutor();

    if (this.log == null && this.logId < 0) {
      execute(new CreateLogAction("", activityId), new ActionSubscriber<Log>() {

        @Override
        public void onResult(Log log) {
          TrackPresenter.this.log = log;
          scheduleRefresh();
          refreshNotification(activityId, log);
        }

        @Override
        public void onError(Throwable throwable) {
          // TODO: 28.01.2018 handle error
          throwable.printStackTrace();
        }
      });
    } else {
      execute(new GetLogAction(logId), new ActionSubscriber<Log>() {

        @Override
        public void onResult(Log log) {
          TrackPresenter.this.log = log;
          scheduleRefresh();
          refreshNotification(activityId, log);
        }

        @Override
        public void onError(Throwable throwable) {
          // TODO: 28.01.2018 handle error
          throwable.printStackTrace();
        }
      });
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    executorService.shutdown();
  }

  private void scheduleRefresh() {
    // TODO: 16.01.2018 wrap executor service
    executorService
        .scheduleAtFixedRate(
            new Runnable() {

              @Override
              public void run() {
                calculateDuration();
              }
            },
            0,
            1,
            TimeUnit.SECONDS);
  }

  private void refreshNotification(final int activityId, final Log log) {
    execute(new GetActivityAction(activityId), new ActionSubscriber<Activity>() {

      @Override
      public void onResult(Activity activity) {
        notificationController
            .showLogDuration(
                log.getId(),
                activityId,
                activity.getTitle(),
                timeProvider.getCurrentTimeInMillis() - log.getStartDate());
      }

      @Override
      public void onError(Throwable throwable) {

      }
    });
  }

  private void calculateDuration() {
    long duration = timeProvider.getCurrentTimeInMillis() - log.getStartDate();
    getView().setTrackedTime(durationFormatter.getFormattedString(duration));
  }

  public void onDoneClick() {
    execute(new ChangeLogStatusAction(log.getId(), LogStatus.DONE), new ActionSubscriber<Void>() {

      @Override
      public void onResult(Void aVoid) {
        notificationController.showNoLog();
        getRouter().handleBackPress();
        getView().hideKeyboard();
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
      }
    });
  }

  @Override
  public void setView(TrackView view) {
    super.setView(view);
    view.setPresenter(this);
  }
}
