package me.veloc1.timetracker.repositories;

import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;

import java.util.ArrayList;
import java.util.List;

public class AndroidActivitiesRepository implements ActivitiesRepository {

  @Override
  public List<Activity> getActivitiesSortedByUpdateDate() {
    return new ArrayList<>();
  }

  @Override
  public Activity add(Activity objectToCreate) {
    return null;
  }

  @Override
  public Activity update(Activity objectToUpdate) {
    return null;
  }
}
