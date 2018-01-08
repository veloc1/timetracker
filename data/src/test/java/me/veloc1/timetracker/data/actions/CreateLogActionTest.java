package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repository.LogsRepository;
import me.veloc1.timetracker.data.types.Log;
import me.veloc1.timetracker.data.types.LogStatus;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;

public class CreateLogActionTest extends BaseActionTest {
  @Test
  public void createLog() throws Throwable {
    CreateLogAction action = new CreateLogAction(null);

    LogsRepository logsRepository = mockLogsRepository();
    action.setLogsRepository(logsRepository);

    action.setTimeProvider(new TimeProvider());

    long timeBeforeExecute = Calendar.getInstance().getTimeInMillis();
    Log  log               = execute(action);
    long timeAfterExecute  = Calendar.getInstance().getTimeInMillis();

    Assert.assertEquals(1, log.getId());
    Assert.assertEquals(null, log.getDescription());
    Assert.assertEquals(LogStatus.IN_PROGRESS, log.getStatus());
    Assert.assertTrue(log.getStartDate() >= timeBeforeExecute &&
                      log.getStartDate() <= timeAfterExecute);
    Assert.assertEquals(Log.NO_DATE, log.getEndDate());
  }

  private LogsRepository mockLogsRepository() {
    LogsRepository repository = Mockito.mock(LogsRepository.class);
    BDDMockito
        .given(repository.add(Mockito.any(Log.class)))
        .will(new Answer<Log>() {

          @Override
          public Log answer(InvocationOnMock invocation) throws Throwable {
            Log logToCreate = (Log) invocation.getArguments()[0];
            return
                new Log(
                    1,
                    logToCreate.getDescription(),
                    logToCreate.getStatus(),
                    logToCreate.getStartDate(),
                    logToCreate.getEndDate());
          }
        });

    return repository;
  }
}
