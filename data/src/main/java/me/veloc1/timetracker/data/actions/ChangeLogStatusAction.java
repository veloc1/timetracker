package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

import javax.inject.Inject;

public class ChangeLogStatusAction implements Action<Void> {

  private final int       logId;
  private final LogStatus newStatus;

  @Inject
  private LogsRepository logsRepository;
  @Inject
  private TimeProvider   timeProvider;

  public ChangeLogStatusAction(int logId, LogStatus newStatus) {
    this.logId = logId;
    this.newStatus = newStatus;
  }

  @Override
  public void execute() {
    Log  log     = logsRepository.getById(logId);
    long endDate = log.getEndDate();

    if (newStatus == LogStatus.DONE) {
      endDate = timeProvider.getCurrentTimeInMillis();
    }

    logsRepository.update(
        new Log(
            logId, log.getDescription(), newStatus, log.getStartDate(), endDate));
  }

  @Override
  public Void getResult() {
    return null;
  }

  public void setLogsRepository(LogsRepository logsRepository) {
    this.logsRepository = logsRepository;
  }


  public void setTimeProvider(TimeProvider timeProvider) {
    this.timeProvider = timeProvider;
  }
}
