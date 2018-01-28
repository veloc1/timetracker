package me.veloc1.timetracker.data;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DurationFormatterTest {
  private static final long SECOND = 1000;
  private static final long MINUTE = SECOND * 60;
  private static final long HOUR   = MINUTE * 60;

  private DurationFormatter durationFormatter;

  @Before
  public void setUp() {
    durationFormatter = new DurationFormatter();
  }

  @Test
  public void oneSecondTest() {
    String formattedString = durationFormatter.getFormattedString(SECOND);
    Assert.assertEquals("00:01", formattedString);
  }

  @Test
  public void oneMinuteTest() {
    String formattedString = durationFormatter.getFormattedString(MINUTE);
    Assert.assertEquals("01:00", formattedString);
  }

  @Test
  public void shortFormatTest() {
    String formattedString = durationFormatter.getFormattedString(30 * MINUTE + 3 * SECOND);
    Assert.assertEquals("30:03", formattedString);
  }

  @Test
  public void longFormatTest() {
    long   duration        = 5 * HOUR + 10 * MINUTE + 42 * SECOND;
    String formattedString = durationFormatter.getFormattedString(duration);
    // todo fix timezone
    Assert.assertEquals("05:10:42", formattedString);
  }
}
