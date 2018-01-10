package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.annotations.Nullable;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

import javax.inject.Inject;

public class CreateLogAction implements Action<Log> {

  @Nullable
  private final String description;
  private final int    activityId;

  private Log result;

  @Inject
  private LogsRepository       logsRepository;
  @Inject
  private ActivitiesRepository activitiesRepository;
  @Inject
  private TimeProvider         timeProvider;

  public CreateLogAction(@Nullable String description, int activityId) {
    super();
    this.description = description;
    this.activityId = activityId;
  }

  @Override
  public void execute() {
    long currentTime = timeProvider.getCurrentTimeInMillis();
    Log toCreate =
        new Log(
            -1,
            description,
            LogStatus.IN_PROGRESS,
            currentTime,
            Log.NO_DATE);

    int newId = logsRepository.add(toCreate);
    result =
        new Log(
            newId,
            description,
            LogStatus.IN_PROGRESS,
            currentTime,
            Log.NO_DATE);

    Activity activity = activitiesRepository.getById(activityId);
    activitiesRepository.update(
        new Activity(
            activityId,
            activity.getTitle(),
            activity.getDescription(),
            activity.getCreatedAt(),
            currentTime));
  }

  @Override
  public Log getResult() {
    return result;
  }

  public void setLogsRepository(LogsRepository logsRepository) {
    this.logsRepository = logsRepository;
  }

  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }

  public void setActivitiesRepository(ActivitiesRepository activitiesRepository) {
    this.activitiesRepository = activitiesRepository;
  }
}
