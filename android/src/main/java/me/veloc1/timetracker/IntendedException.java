package me.veloc1.timetracker;

public class IntendedException extends RuntimeException {

  public IntendedException() {
    super();
  }

  public IntendedException(String message) {
    super(message);
  }

  public IntendedException(String message, Throwable cause) {
    super(message, cause);
  }

  public IntendedException(Throwable cause) {
    super(cause);
  }
}
