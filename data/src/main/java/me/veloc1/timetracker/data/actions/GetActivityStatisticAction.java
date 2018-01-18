package me.veloc1.timetracker.data.actions;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.Action;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.data.types.ActivityStatistic;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;

public class GetActivityStatisticAction implements Action<List<ActivityStatistic>> {

  private final List<Activity>          activities;
  private       List<ActivityStatistic> result;

  @Inject
  private LogsRepository logsRepository;
  @Inject
  private TimeProvider   timeProvider;

  public GetActivityStatisticAction(List<Activity> activities) {
    super();
    this.activities = activities;
  }

  @Override
  public void execute() {
    result = new ArrayList<>();
    for (final Activity activity : activities) {
      List<Log> logs     = logsRepository.getLogsByActivityId(activity.getId());
      long      duration = 0;
      for (final Log log : logs) {
        if (log.getStatus() == LogStatus.DONE) {
          duration += log.getEndDate() - log.getStartDate();
        } else if (log.getStatus() == LogStatus.IN_PROGRESS) {
          duration += timeProvider.getCurrentTimeInMillis() - log.getStartDate();
        }
      }
      result.add(new ActivityStatistic(activity.getId(), duration, logs));
    }
  }

  @Override
  public List<ActivityStatistic> getResult() {
    return result;
  }
}
