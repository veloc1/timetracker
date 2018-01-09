package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

import javax.inject.Inject;
import java.util.List;

public class GetAllActivitiesAction implements Action<List<Activity>> {
  private List<Activity> result;
  @Inject
  private ActivitiesRepository activitiesRepository;

  @Override
  public void execute() {
    result = activitiesRepository.getActivitiesSortedByUpdateDate();
  }

  @Override
  public List<Activity> getResult() {
    return result;
  }

  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }
}
