package me.veloc1.timetracker.data.repository;

import me.veloc1.timetracker.data.types.Tag;

public interface TagsRepository extends Repository<Tag> {
  /**
   * Find object by tag name
   *
   * @param tag tag title
   * @return tag object or null
   */
  Tag find(String tag);
}
