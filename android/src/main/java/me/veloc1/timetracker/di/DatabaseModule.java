package me.veloc1.timetracker.di;

import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.repositories.TagToActivityRepository;
import me.veloc1.timetracker.data.repositories.TagsRepository;
import me.veloc1.timetracker.repositories.AndroidActivitiesRepository;
import me.veloc1.timetracker.repositories.AndroidTagToActivityRepository;
import me.veloc1.timetracker.repositories.AndroidTagsRepository;
import org.codejargon.feather.Provides;

import javax.inject.Singleton;

public class DatabaseModule {

  private final AndroidActivitiesRepository    activitiesRepository;
  private final AndroidTagsRepository          tagsRepository;
  private final AndroidTagToActivityRepository tagToActivityRepository;

  public DatabaseModule(
      AndroidActivitiesRepository activitiesRepository,
      AndroidTagsRepository tagsRepository,
      AndroidTagToActivityRepository tagToActivityRepository) {
    this.activitiesRepository = activitiesRepository;
    this.tagsRepository = tagsRepository;
    this.tagToActivityRepository = tagToActivityRepository;
  }

  @Provides
  @Singleton
  ActivitiesRepository activitiesRepository() {
    return activitiesRepository;
  }

  @Provides
  @Singleton
  TagsRepository tagsRepository() {
    return tagsRepository;
  }

  @Provides
  @Singleton
  TagToActivityRepository tagToActivityRepository() {
    return tagToActivityRepository;
  }
}
