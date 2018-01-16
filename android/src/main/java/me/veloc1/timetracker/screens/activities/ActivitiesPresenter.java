package me.veloc1.timetracker.screens.activities;

import me.veloc1.timetracker.data.actions.GetAllActivitiesAction;
import me.veloc1.timetracker.data.actions.RemoveActivityAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.base.Presenter;

import java.util.List;

public class ActivitiesPresenter
    extends Presenter<ActivitiesView>
    implements ActionSubscriber<List<Activity>> {

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

    getView().setItems(activities);
  }

  @Override
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
    getView().hideProgress();
    getView().showError();
  }

  public void onAddClick() {
    getRouter().startAddActivitiesScreen();
  }

  public void onEditClick(int activityId) {
    getRouter().startActivityEditScreen(activityId);
  }

  public void onRemoveClick(int activityId) {
    execute(new RemoveActivityAction(activityId), new ActionSubscriber<Void>() {

      @Override
      public void onResult(Void aVoid) {
        onStart();
      }

      @Override
      public void onError(Throwable throwable) {
        ActivitiesPresenter.this.onError(throwable);
      }
    });
  }

  @Override
  public void setView(ActivitiesView view) {
    super.setView(view);
    view.setPresenter(this);
  }
}
