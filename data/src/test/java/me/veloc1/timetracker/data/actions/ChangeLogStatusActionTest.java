package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;

public class ChangeLogStatusActionTest extends BaseActionTest {

  @Test
  public void changeLogStatusAction() throws Throwable {
    ChangeLogStatusAction action = new ChangeLogStatusAction(1, LogStatus.DONE);

    LogsRepository logsRepository = initLogsRepository(action);

    action.setTimeProvider(new TimeProvider());

    long timeBeforeExecute = Calendar.getInstance().getTimeInMillis();
    execute(action);
    long timeAfterExecute = Calendar.getInstance().getTimeInMillis();

    Mockito.verify(logsRepository).getById(1);

    verifyUpdateInvocation(logsRepository, timeBeforeExecute, timeAfterExecute);

    Mockito.verifyNoMoreInteractions(logsRepository);
  }

  private LogsRepository initLogsRepository(ChangeLogStatusAction action) {
    LogsRepository logsRepository = Mockito.mock(LogsRepository.class);

    BDDMockito
        .given(logsRepository.getById(1))
        .will(new Answer<Log>() {

          @Override
          public Log answer(InvocationOnMock invocation) throws Throwable {
            return new Log(
                (Integer) invocation.getArguments()[0],
                "",
                LogStatus.IN_PROGRESS,
                100,
                200);
          }
        });

    action.setLogsRepository(logsRepository);
    return logsRepository;
  }

  private void verifyUpdateInvocation(
      LogsRepository logsRepository,
      long timeBeforeExecute,
      long timeAfterExecute) {

    ArgumentCaptor<Log> logArgumentCaptor = ArgumentCaptor.forClass(Log.class);

    Mockito.verify(logsRepository).update(logArgumentCaptor.capture());
    
    Log actualLog = logArgumentCaptor.getValue();
    Assert.assertEquals(1, actualLog.getId());
    Assert.assertEquals("", actualLog.getDescription());
    Assert.assertEquals(LogStatus.DONE, actualLog.getStatus());
    Assert.assertEquals(100, actualLog.getStartDate());
    Assert.assertTrue(actualLog.getEndDate() >= timeBeforeExecute &&
                      actualLog.getEndDate() <= timeAfterExecute);
  }
}
