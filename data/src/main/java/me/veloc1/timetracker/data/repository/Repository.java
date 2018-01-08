package me.veloc1.timetracker.data.repository;

public interface Repository<T> {
  /**
   * Creates an object and store it. Returns created object with all fields filled.
   *
   * @param objectToCreate pass an object you want to store
   * @return newly created object
   */
  T add(T objectToCreate);
}
