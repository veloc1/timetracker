package me.veloc1.timetracker.screens.add_activity;

import me.veloc1.timetracker.data.actions.CreateActivityAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.base.Presenter;

public class AddActivityPresenter
    extends Presenter<AddActivityView>
    implements ActionSubscriber<Activity> {

  public void onAddClick(String title, String description) {
    CreateActivityAction action = new CreateActivityAction(title, description);
    execute(action, this);
  }

  @Override
  public void onResult(Activity activity) {
    getView().hideKeyboard();
    getRouter().handleBackPress();
  }

  @Override
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
    getView().showError();
  }

  @Override
  public void setView(AddActivityView view) {
    super.setView(view);
    view.setPresenter(this);
  }
}
