package me.veloc1.timetracker.data.actions.base;

import me.veloc1.timetracker.data.actions.base.Action;

public class DummyAction implements Action<String> {
  private String result;

  @Override
  public void execute() {
    result = "Dummy action execute";
  }

  @Override
  public String getResult() {
    return result;
  }
}
