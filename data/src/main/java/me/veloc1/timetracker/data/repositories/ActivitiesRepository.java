package me.veloc1.timetracker.data.repositories;

import java.util.List;

import me.veloc1.timetracker.data.types.Activity;

public interface ActivitiesRepository extends Repository<Activity> {

  List<Activity> getActivitiesSortedByUpdateDate();
  List<Activity> getActivitiesUpdatedSinceDate(long sinceDate);
}
