package me.veloc1.timetracker.data.actions.base;

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
