package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

import javax.inject.Inject;

public class GetActivityAction implements Action<Activity> {
  private final int activityId;

  private Activity             result;
  @Inject
  private ActivitiesRepository activitiesRepository;

  public GetActivityAction(int activityId) {
    super();
    this.activityId = activityId;
  }

  @Override
  public void execute() {
    result = activitiesRepository.getById(activityId);
  }

  @Override
  public Activity getResult() {
    return result;
  }

  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }
}
