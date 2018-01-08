package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.annotations.Nullable;
import me.veloc1.timetracker.data.repository.LogsRepository;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

import javax.inject.Inject;

public class CreateLogAction implements Action<Log> {

  @Nullable
  private String         description;
  private Log            result;
  private LogsRepository logsRepository;
  private TimeProvider   timeProvider;

  public CreateLogAction(@Nullable String description) {
    super();
    this.description = description;
  }

  @Override
  public void execute() {
    Log toCreate =
        new Log(
            -1,
            description,
            LogStatus.IN_PROGRESS,
            timeProvider.getCurrentTimeInMillis(),
            Log.NO_DATE);

    result = logsRepository.add(toCreate);
  }

  @Override
  public Log getResult() {
    return result;
  }

  @Inject
  public void setLogsRepository(LogsRepository logsRepository) {
    this.logsRepository = logsRepository;
  }

  @Inject
  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }
}
