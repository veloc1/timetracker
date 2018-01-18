package me.veloc1.timetracker.data.actions;

import java.util.List;
import javax.inject.Inject;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

public class GetActivitiesUpdatedSinceDateAction implements Action<List<Activity>> {

  private final long           sinceDate;
  private       List<Activity> result;

  @Inject
  private ActivitiesRepository activitiesRepository;

  public GetActivitiesUpdatedSinceDateAction(long sinceDate) {
    super();
    this.sinceDate = sinceDate;
  }

  @Override
  public void execute() {
    result = activitiesRepository.getActivitiesUpdatedSinceDate(sinceDate);
  }

  @Override
  public List<Activity> getResult() {
    return result;
  }

  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }
}
