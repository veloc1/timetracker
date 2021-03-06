package me.veloc1.timetracker.data.actions;

import javax.inject.Inject;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

/**
 * Creates an activity with given fields and bind newly created activity to given tags.
 */
public class CreateActivityAction implements Action<Activity> {

  private final String title;
  private final String description;
  private final int    color;

  private Activity result;

  @Inject
  private ActivitiesRepository activitiesRepository;
  @Inject
  private TimeProvider         timeProvider;

  public CreateActivityAction(String title, String description, int color) {
    this.title = title;
    this.description = description;
    this.color = color;
  }

  @Override
  public void execute() {
    long currentTime = timeProvider.getCurrentTimeInMillis();
    Activity toCreate =
        new Activity(
            -1,
            title,
            description,
            color,
            currentTime,
            currentTime);

    int newId = activitiesRepository.add(toCreate);
    result =
        new Activity(
            newId,
            title,
            description,
            color,
            currentTime,
            currentTime);
  }

  @Override
  public Activity getResult() {
    return result;
  }

  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }

  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }
}
