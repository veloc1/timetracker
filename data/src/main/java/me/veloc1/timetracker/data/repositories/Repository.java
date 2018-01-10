package me.veloc1.timetracker.data.repositories;

public interface Repository<T> {
  /**
   * Creates an object and store it. Returns created object with all fields filled.
   *
   * @param objectToCreate pass an object you want to store
   * @return newly created object
   */
  int add(T objectToCreate);

  /**
   * Updates an object. If this object not exists - will throw
   *
   * @param objectToUpdate object, which should be updated
   * @return updated object
   */
  void update(T objectToUpdate);

  T getById(int id);
}
