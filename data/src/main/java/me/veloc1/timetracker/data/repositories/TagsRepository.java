package me.veloc1.timetracker.data.repositories;

import me.veloc1.timetracker.data.types.Tag;

import java.util.List;

public interface TagsRepository extends Repository<Tag> {
  /**
   * Find object by tag name
   *
   * @param tag tag title
   * @return tag object or null
   */
  Tag find(String tag);
  List<Tag> findByActivity(int activityId);
}
