package me.veloc1.timetracker.data.actions.base;

public interface Action<RESULT> {

  void execute();

  RESULT getResult();

}
