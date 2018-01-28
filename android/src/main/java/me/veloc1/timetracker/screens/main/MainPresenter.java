package me.veloc1.timetracker.screens.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import me.veloc1.timetracker.data.DurationFormatter;
import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.GetActivitiesUpdatedSinceDateAction;
import me.veloc1.timetracker.data.actions.GetActivityStatisticAction;
import me.veloc1.timetracker.data.actions.GetAllActivitiesAction;
import me.veloc1.timetracker.data.actions.GetCurrentInProgressLogAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.ActivityStatistic;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.screens.base.Presenter;
import me.veloc1.timetracker.screens.main.view.MainView;

public class MainPresenter extends Presenter<MainView> {

  private boolean shouldHandleActivitiesAsAddAction;
  private boolean isMenuOpen;
  private Log     currentLog;

  private boolean isErrorInStatistic;

  private List<ActivityStatisticDisplayItem> statistics;

  @Inject
  private TimeProvider      timeProvider;
  @Inject
  private DurationFormatter durationFormatter;

  private ScheduledExecutorService scheduledExecutorService;

  @Override
  public void onStart() {
    super.onStart();
    getView().showProgress();
    execute(new GetAllActivitiesAction(), new ActivitiesListSubscriber());

    statistics = new ArrayList<>();
    isErrorInStatistic = false;

    long currentTime = timeProvider.getCurrentTimeInMillis();
    long agoTime     = currentTime - TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS);
    execute(new GetActivitiesUpdatedSinceDateAction(agoTime), new StatisticSubscriber());
  }

  @Override
  public void onStop() {
    super.onStop();
    if (scheduledExecutorService != null) {
      scheduledExecutorService.shutdown();
      scheduledExecutorService = null;
    }
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

  public void onTrackClick(int activityId) {
    if (currentLog != null) {
      getRouter().startTrackScreen(currentLog);
    } else {
      getRouter().startTrackScreen(activityId);
    }
  }

  public String getCurrentDuration(int activityId) {
    if (currentLog != null && currentLog.getActivityId() == activityId) {
      long duration = timeProvider.getCurrentTimeInMillis() - currentLog.getStartDate();
      return durationFormatter.getFormattedString(duration);
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

  public List<ActivityStatisticDisplayItem> getStatistics() {
    return statistics;
  }

  public boolean isErrorInStatistic() {
    return isErrorInStatistic;
  }

  private class ActivitiesListSubscriber implements ActionSubscriber<List<Activity>> {

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

    @Override
    public void onError(Throwable throwable) {
      throwable.printStackTrace();
      getView().hideProgress();
      getView().showError();
    }
  }

  private class StatisticSubscriber implements ActionSubscriber<List<Activity>> {
    @Override
    public void onResult(final List<Activity> activities) {
      execute(
          new GetActivityStatisticAction(activities),
          new ActionSubscriber<List<ActivityStatistic>>() {

            @Override
            public void onResult(List<ActivityStatistic> statistic) {
              isErrorInStatistic = false;
              List<ActivityStatisticDisplayItem> displayItems = new ArrayList<>();
              for (final Activity activity : activities) {
                for (ActivityStatistic statisticItem : statistic) {
                  if (activity.getId() == statisticItem.getActivityId()) {
                    ActivityStatisticDisplayItem item =
                        new ActivityStatisticDisplayItem(
                            activity.getId(),
                            activity.getTitle(),
                            activity.getColor(),
                            statisticItem.getTotalDuration());

                    displayItems.add(item);
                    break;
                  }
                }
              }
              Collections.sort(displayItems, new Comparator<ActivityStatisticDisplayItem>() {

                @Override
                public int compare(
                    ActivityStatisticDisplayItem o1, ActivityStatisticDisplayItem o2) {
                  return Float.valueOf(o2.getValue()).compareTo(o1.getValue());
                }
              });
              statistics = displayItems.subList(0, 5);
            }

            @Override
            public void onError(Throwable throwable) {
              throwable.printStackTrace();
              isErrorInStatistic = true;
            }
          });
    }

    @Override
    public void onError(Throwable throwable) {
      throwable.printStackTrace();
      isErrorInStatistic = true;
    }
  }
}
