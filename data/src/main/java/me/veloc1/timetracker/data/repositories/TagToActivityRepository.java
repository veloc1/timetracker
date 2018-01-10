package me.veloc1.timetracker.data.repositories;

import me.veloc1.timetracker.data.types.TagToActivity;

import java.util.List;

/**
 * Current implementation is pretty dumb. Feel free to contact me and suggest other many-to-many
 * implementations
 */
public interface TagToActivityRepository extends Repository<TagToActivity> {
  List<TagToActivity> findByActivityId(int activityId);
  void removeTagsFromActivity(int id);
}
