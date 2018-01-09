package me.veloc1.timetracker.screens.main;

import me.veloc1.timetracker.data.actions.CreateActivityAction;
import me.veloc1.timetracker.data.actions.GetAllActivitiesAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.base.Presenter;

import java.util.List;

public class MainPresenter extends Presenter<MainView> implements ActionSubscriber<List<Activity>> {

  @Override
  public void onStart() {
    super.onStart();
    getView().showProgress();
    execute(new CreateActivityAction("test", "desc", null), new ActionSubscriber<Activity>() {

      @Override
      public void onResult(Activity activity) {

      }

      @Override
      public void onError(Throwable throwable) {

      }
    });
    execute(new GetAllActivitiesAction(), this);
  }

  @Override
  public void onResult(List<Activity> activities) {
    getView().hideProgress();
    getView().setItems(activities);
  }

  @Override
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
    getView().hideProgress();
    getView().showError();
  }
}
