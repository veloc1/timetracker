package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.TimeProvider;
import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import me.veloc1.timetracker.data.types.Activity;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Calendar;
import java.util.Date;

public class CreateActivityActionTest extends BaseActionTest {

  @Test
  public void createActivityWithoutTags() throws Throwable {
    String title       = "title";
    String description = "test description";

    CreateActivityAction action = new CreateActivityAction(title, description, color);

    ActivitiesRepository repository = initActivitiesRepository(action);

    action.setTimeProvider(new TimeProvider());

    Date     timeBeforeExecute = Calendar.getInstance().getTime();
    Activity result            = execute(action);
    Date     timeAfterExecute  = Calendar.getInstance().getTime();

    verifyReturnedActivity(title, description, result, timeBeforeExecute, timeAfterExecute);
    verifyAddInvocation(repository, result);

    Mockito.verifyNoMoreInteractions(repository);
  }

  @Test(expected = NullPointerException.class)
  public void createActivityWithError() throws Throwable {
    CreateActivityAction action = new CreateActivityAction(null, null, color);

    ActivitiesRepository repository = initActivitiesRepository(action);

    action.setTimeProvider(new TimeProvider());

    execute(action); //this line should throw

    Mockito.verifyNoMoreInteractions(repository);
  }


  private ActivitiesRepository initActivitiesRepository(CreateActivityAction action) {
    ActivitiesRepository repository = mockActivitiesRepository();
    action.setActivitiesRepository(repository);
    return repository;
  }

  private void verifyReturnedActivity(
      String title,
      String description,
      Activity result,
      Date timeBeforeExecute,
      Date timeAfterExecute) {

    Assert.assertEquals(1, result.getId());
    Assert.assertEquals(title, result.getTitle());
    Assert.assertEquals(description, result.getDescription());

    Assert.assertTrue(
        result.getCreatedAt() >= timeBeforeExecute.getTime()
        && result.getCreatedAt() <= timeAfterExecute.getTime());
    Assert.assertTrue(
        result.getUpdatedAt() >= timeBeforeExecute.getTime()
        && result.getUpdatedAt() <= timeAfterExecute.getTime());
  }

  private void verifyAddInvocation(ActivitiesRepository repository, Activity result) {
    ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);
    Mockito.verify(repository).add(captor.capture());

    Assert.assertEquals(-1, captor.getValue().getId());
    Assert.assertEquals(result.getTitle(), captor.getValue().getTitle());
    Assert.assertEquals(result.getDescription(), captor.getValue().getDescription());
    Assert.assertEquals(result.getCreatedAt(), captor.getValue().getCreatedAt());
    Assert.assertEquals(result.getUpdatedAt(), captor.getValue().getUpdatedAt());
  }

  private ActivitiesRepository mockActivitiesRepository() {
    ActivitiesRepository repository = Mockito.mock(ActivitiesRepository.class);
    BDDMockito
        .given(repository.add(Mockito.any(Activity.class)))
        .will(new Answer<Integer>() {

          @Override
          public Integer answer(InvocationOnMock invocation) {
            Activity activityToCreate = invocation.getArgument(0);

            if (activityToCreate.getTitle() == null) {
              throw new NullPointerException();
            }

            return 1;
          }
        });
    return repository;
  }
}
