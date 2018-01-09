package me.veloc1.timetracker.screens.main;

import me.veloc1.timetracker.data.actions.GetAllActivitiesAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.base.Presenter;

import java.util.List;

public class MainPresenter extends Presenter<MainView> implements ActionSubscriber<List<Activity>> {

  @Override
  public void onStart() {
    super.onStart();
    execute(new GetAllActivitiesAction(), this);
    getView().setText("started");
  }

  @Override
  public void onResult(List<Activity> activities) {
    getView().setText("result");
  }

  @Override
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
    getView().setText("errror");
  }
}
