package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

import javax.inject.Inject;
import java.util.List;

public class GetCurrentInProgressLogAction implements Action<Log> {

  private Log result;

  @Inject
  private LogsRepository logsRepository;

  @Override
  public void execute() {
    List<Log> status = logsRepository.getLogsWithStatus(LogStatus.IN_PROGRESS);
    if (status.size() > 0) {
      result = status.get(0);
    }
  }

  @Override
  public Log getResult() {
    return result;
  }

  public void setLogsRepository(LogsRepository logsRepository) {
    this.logsRepository = logsRepository;
  }
}
