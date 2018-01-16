package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;

import javax.inject.Inject;

public class RemoveActivityAction implements Action<Void> {

  private int activityId;

  @Inject
  private ActivitiesRepository activitiesRepository;

  public RemoveActivityAction(int activityId) {
    this.activityId = activityId;
  }

  @Override
  public void execute() {
    activitiesRepository.remove(activityId);
  }

  @Override
  public Void getResult() {
    return null;
  }
}
