package me.veloc1.timetracker.screens.main;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.GetAllActivitiesAction;
import me.veloc1.timetracker.data.actions.GetCurrentInProgressLogAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.screens.base.Presenter;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPresenter extends Presenter<MainView> implements ActionSubscriber<List<Activity>> {

  private final SimpleDateFormat durationFormat =
      new SimpleDateFormat("mm:ss", Locale.getDefault());

  private boolean shouldHandleActivitiesAsAddAction;
  private boolean isMenuOpen;
  private Log     currentLog;

  @Inject
  private TimeProvider             timeProvider;
  private ScheduledExecutorService scheduledExecutorService;

  @Override
  public void onStart() {
    super.onStart();
    getView().showProgress();
    execute(new GetAllActivitiesAction(), this);
  }

  @Override
  public void onStop() {
    super.onStop();
    scheduledExecutorService.shutdown();
  }

  @Override
  public void onResult(final List<Activity> activities) {
    execute(new GetCurrentInProgressLogAction(), new ActionSubscriber<Log>() {

      @Override
      public void onResult(Log log) {
        showActivities(activities);

        if (log != null) {
          currentLog = log;
          getView().refreshList();

          scheduleUpdate();
        } else {
          currentLog = null;
          getView().refreshList();
        }
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
      }
    });
  }

  private void showActivities(List<Activity> activities) {
    getView().hideProgress();
    getView().hideError();
    getView().hideFirstRunTip();

    getView().setItems(activities);

    if (activities.size() == 0) {
      getView().showFirstRunTip();
      getView().setActivitiesNameToAddActivity();
      shouldHandleActivitiesAsAddAction = true;
    } else {
      getView().setActivitiesNameToDefault();
      shouldHandleActivitiesAsAddAction = false;
    }
  }

  @Override
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
    getView().hideProgress();
    getView().showError();
  }

  public void onTrackClick(int activityId) {
    if (currentLog != null) {
      getRouter().startTrackScreen(currentLog);
    } else {
      getRouter().startTrackScreen(activityId);
    }
  }

  public String getCurrentDuration(int activityId) {
    if (currentLog != null && currentLog.getActivityId() == activityId) {
      return
          durationFormat
              .format(
                  timeProvider.getCurrentTimeInMillis() - currentLog.getStartDate());
    }
    return "";
  }

  private void scheduleUpdate() {
    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    // TODO: 16.01.2018 wrap executor service
    scheduledExecutorService
        .scheduleAtFixedRate(
            new Runnable() {

              @Override
              public void run() {
                refreshCurrentItem();
              }
            },
            0,
            1,
            TimeUnit.SECONDS);
  }

  public boolean shouldViewShowTrackButton(int activityId) {
    if (currentLog == null) {
      return true;
    }
    if (currentLog.getActivityId() == activityId) {
      return true;
    }
    return false;
  }

  public void onActivitiesClick() {
    if (shouldHandleActivitiesAsAddAction) {
      getRouter().startAddActivitiesScreen();
    } else {
      getRouter().startActivitiesListScreen();
    }
  }

  public void onFabClick() {
    openMenu();
  }

  public void onCloseClick() {
    closeMenu();
  }

  private void openMenu() {
    getView().morphFabToBottomBar();
    getView().enableMenu();
    isMenuOpen = true;
  }

  private void closeMenu() {
    getView().morphBottomBarToFab();
    getView().disableMenu();
    isMenuOpen = false;
  }

  private void refreshCurrentItem() {
    // TODO: 16.01.2018 refresh only current item
    getView().refreshList();
  }

  @Override
  public void setView(MainView view) {
    super.setView(view);
    view.setPresenter(this);
  }

  public boolean canHandleBackPress() {
    return isMenuOpen;
  }

  public void handleBackPress() {
    if (isMenuOpen) {
      closeMenu();
    }
  }
}
