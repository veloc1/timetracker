package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.LogStatus;
import org.junit.Test;
import org.mockito.Mockito;

public class GetCurrentInProgressLogActionTest extends BaseActionTest {

  @Test
  public void getCurrentLog() throws Throwable {
    GetCurrentInProgressLogAction action = new GetCurrentInProgressLogAction();

    LogsRepository logsRepository = Mockito.mock(LogsRepository.class);
    action.setLogsRepository(logsRepository);

    execute(action);

    Mockito.verify(logsRepository).getLogsWithStatus(LogStatus.IN_PROGRESS);

    Mockito.verifyNoMoreInteractions(logsRepository);
  }
}
