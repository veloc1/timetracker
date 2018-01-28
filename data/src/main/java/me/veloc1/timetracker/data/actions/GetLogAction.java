package me.veloc1.timetracker.data.actions;

import javax.inject.Inject;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Log;

public class GetLogAction implements Action<Log> {
  private final int logId;

  private Log result;

  @Inject
  private LogsRepository logsRepository;

  public GetLogAction(int logId) {
    super();
    this.logId = logId;
  }

  @Override
  public void execute() {
    result = logsRepository.getById(logId);
  }

  @Override
  public Log getResult() {
    return result;
  }

  public void setLogsRepository(LogsRepository logsRepository) {
    this.logsRepository = logsRepository;
  }
}
