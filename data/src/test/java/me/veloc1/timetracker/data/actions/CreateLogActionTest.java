package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.repositories.LogsRepository;
import me.veloc1.timetracker.data.types.Activity;
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

public class CreateLogActionTest extends BaseActionTest {
  @Test
  public void createLog() throws Throwable {
    CreateLogAction action = new CreateLogAction(null, 1);

    LogsRepository       logsRepository       = initLogsRepository(action);
    ActivitiesRepository activitiesRepository = initActivitiesRepository(action);

    action.setTimeProvider(new TimeProvider());

    long timeBeforeExecute = Calendar.getInstance().getTimeInMillis();
    Log  log               = execute(action);
    long timeAfterExecute  = Calendar.getInstance().getTimeInMillis();

    verifyReturnedLog(timeBeforeExecute, log, timeAfterExecute, 1);

    ArgumentCaptor<Log> logArgumentCaptor = ArgumentCaptor.forClass(Log.class);
    Mockito.verify(logsRepository).add(logArgumentCaptor.capture());
    Log actualLog = logArgumentCaptor.getValue();
    verifyReturnedLog(timeBeforeExecute, actualLog, timeAfterExecute, -1);

    Mockito.verify(activitiesRepository).getById(1);

    verifyUpdateActivity(activitiesRepository, timeBeforeExecute, timeAfterExecute);

    Mockito.verifyNoMoreInteractions(logsRepository, activitiesRepository);
  }

  private LogsRepository initLogsRepository(CreateLogAction action) {
    LogsRepository logsRepository = mockLogsRepository();
    action.setLogsRepository(logsRepository);
    return logsRepository;
  }

  private ActivitiesRepository initActivitiesRepository(CreateLogAction action) {
    ActivitiesRepository activitiesRepository = mockActivitiesRepository();
    action.setActivitiesRepository(activitiesRepository);
    return activitiesRepository;
  }

  private void verifyReturnedLog(
      long timeBeforeExecute,
      Log log,
      long timeAfterExecute,
      int expectedId) {

    Assert.assertEquals(expectedId, log.getId());
    Assert.assertEquals(null, log.getDescription());
    Assert.assertEquals(LogStatus.IN_PROGRESS, log.getStatus());
    Assert.assertTrue(log.getStartDate() >= timeBeforeExecute &&
                      log.getStartDate() <= timeAfterExecute);
    Assert.assertEquals(Log.NO_DATE, log.getEndDate());
  }

  private void verifyUpdateActivity(
      ActivitiesRepository activitiesRepository,
      long timeBeforeExecute,
      long timeAfterExecute) {

    ArgumentCaptor<Activity> activityArgumentCaptor = ArgumentCaptor.forClass(Activity.class);
    Mockito.verify(activitiesRepository).update(activityArgumentCaptor.capture());
    Activity actualActivity = activityArgumentCaptor.getValue();
    Assert.assertEquals(1, actualActivity.getId());
    Assert.assertEquals("", actualActivity.getTitle());
    Assert.assertEquals("", actualActivity.getDescription());
    Assert.assertEquals(100L, actualActivity.getCreatedAt());
    Assert.assertTrue(actualActivity.getUpdatedAt() >= timeBeforeExecute &&
                      actualActivity.getUpdatedAt() <= timeAfterExecute);
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

  private ActivitiesRepository mockActivitiesRepository() {
    ActivitiesRepository repository = Mockito.mock(ActivitiesRepository.class);
    BDDMockito
        .given(repository.getById(Mockito.anyInt()))
        .will(new Answer<Activity>() {

          @Override
          public Activity answer(InvocationOnMock invocation) throws Throwable {
            return
                new Activity(
                    1,
                    "",
                    "",
                    100L,
                    300L);
          }
        });

    return repository;
  }
}
