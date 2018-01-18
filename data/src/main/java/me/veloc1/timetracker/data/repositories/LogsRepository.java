package me.veloc1.timetracker.data.repositories;

import java.util.List;

import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

public interface LogsRepository extends Repository<Log> {
  List<Log> getLogsWithStatus(LogStatus status);
  List<Log> getLogsByActivityId(int activityId);
}
