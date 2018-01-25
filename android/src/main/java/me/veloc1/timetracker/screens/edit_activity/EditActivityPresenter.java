package me.veloc1.timetracker.screens.edit_activity;

import me.veloc1.timetracker.data.actions.EditActivityAction;
import me.veloc1.timetracker.data.actions.GetActivityAction;
import me.veloc1.timetracker.data.actions.base.ActionSubscriber;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.base.Presenter;

public class EditActivityPresenter
    extends Presenter<EditActivityView>
    implements ActionSubscriber<Void> {

  private final int activityId;

  public EditActivityPresenter(int activityId) {
    super();
    this.activityId = activityId;
  }

  @Override
  public void onStart() {
    super.onStart();
    execute(new GetActivityAction(activityId), new ActionSubscriber<Activity>() {

      @Override
      public void onResult(Activity activity) {
        getView().setTitle(activity.getTitle());
        getView().setDescription(activity.getDescription());
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
        getView().showError();
      }
    });
  }

  public void onAddClick(String title, String description, int newColor) {
    EditActivityAction action = new EditActivityAction(activityId, title, description, newColor);
    execute(action, this);
  }

  @Override
  public void onResult(Void none) {
    getView().closeKeyboard();
    getRouter().handleBackPress();
  }

  @Override
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
    getView().showError();
  }

  @Override
  public void setView(EditActivityView view) {
    super.setView(view);
    view.setPresenter(this);
  }
}
