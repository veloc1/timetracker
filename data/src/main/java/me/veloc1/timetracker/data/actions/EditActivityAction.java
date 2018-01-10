package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.annotations.Nullable;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Tag;
import me.veloc1.timetracker.data.types.TagToActivity;

import javax.inject.Inject;
import java.util.List;

public class EditActivityAction implements Action<Void> {

  private final int      activityId;
  private final String   newTitle;
  private final String   newDescription;

  @Inject
  private ActivitiesRepository    activitiesRepository;
  @Inject
  private TimeProvider            timeProvider;

  public EditActivityAction(int activityId, String newTitle, String newDescription) {
    this.activityId = activityId;
    this.newTitle = newTitle;
    this.newDescription = newDescription;
  }

  @Override
  public void execute() {
    Activity activity = activitiesRepository.getById(activityId);

    activitiesRepository.update(
        new Activity(
            activityId,
            newTitle,
            newDescription,
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
