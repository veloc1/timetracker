package me.veloc1.timetracker.screens.main;

import me.veloc1.timetracker.data.actions.GetAllActivitiesAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.base.Presenter;

import java.util.List;

public class MainPresenter extends Presenter<MainView> implements ActionSubscriber<List<Activity>> {

  private boolean shouldHandleActivitiesAsAddAction;
  private boolean isMenuOpen;

  @Override
  public void onStart() {
    super.onStart();
    getView().showProgress();
    execute(new GetAllActivitiesAction(), this);
  }

  @Override
  public void onResult(List<Activity> activities) {
    getView().hideProgress();
    getView().hideError();
    getView().hideFirstRunTip();

    if (activities.size() == 0) {
      getView().showFirstRunTip();
      getView().setActivitiesNameToAddActivity();
      shouldHandleActivitiesAsAddAction = true;
    } else {
      getView().setItems(activities);
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

  public void onFabClick() {
    openMenu();
  }

  private void openMenu() {
    getView().morphFabToBottomBar();
    isMenuOpen = true;
  }

  private void closeMenu() {
    getView().morphBottomBarToFab();
    isMenuOpen = false;
  }

  @Override
  public void setView(MainView view) {
    super.setView(view);
    view.setPresenter(this);
  }

  public boolean canGoBack() {
    if (isMenuOpen) {
      closeMenu();
      return false;
    }
    return true;
  }
}
