package me.veloc1.timetracker.data.actions;

import javax.inject.Inject;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

public class EditActivityAction implements Action<Void> {

  private final int    activityId;
  private final String newTitle;
  private final String newDescription;
  private final int newColor;

  @Inject
  private ActivitiesRepository activitiesRepository;
  @Inject
  private TimeProvider         timeProvider;

  public EditActivityAction(int activityId, String newTitle, String newDescription, int newColor) {
    this.activityId = activityId;
    this.newTitle = newTitle;
    this.newDescription = newDescription;
    this.newColor = newColor;
  }

  @Override
  public void execute() {
    Activity activity = activitiesRepository.getById(activityId);

    activitiesRepository.update(
        new Activity(
            activityId,
            newTitle,
            newDescription,
            newColor,
            activity.getCreatedAt(),
            timeProvider.getCurrentTimeInMillis()));
  }

  @Override
  public Void getResult() {
    return null;
  }

  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }

  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }
}
