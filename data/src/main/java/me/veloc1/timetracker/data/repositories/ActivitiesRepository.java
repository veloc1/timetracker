package me.veloc1.timetracker.data.repositories;

import me.veloc1.timetracker.data.types.Activity;

import java.util.List;

public interface ActivitiesRepository extends Repository<Activity> {

  List<Activity> getActivitiesSortedByUpdateDate();
}
