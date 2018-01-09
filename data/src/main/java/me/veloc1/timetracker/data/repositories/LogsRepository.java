package me.veloc1.timetracker.data.repositories;

import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

import java.util.List;

public interface LogsRepository extends Repository<Log> {
  List<Log> getLogsWithStatus(LogStatus progress);
}
