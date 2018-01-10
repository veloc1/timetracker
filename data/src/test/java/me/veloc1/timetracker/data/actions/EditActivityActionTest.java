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

public class EditActivityActionTest extends BaseActionTest {

  @Test
  public void editActivityWithTags() throws Throwable {
    String title       = "title";
    String description = "test description";

    EditActivityAction action = new EditActivityAction(1, title, description);

    ActivitiesRepository activitiesRepository = initActivitiesRepository(action);

    action.setTimeProvider(new TimeProvider());

    Date timeBeforeExecute = Calendar.getInstance().getTime();
    execute(action);
    Date timeAfterExecute = Calendar.getInstance().getTime();

    Mockito.verify(activitiesRepository).getById(1);

    verifyUpdateActivityInvocation(
        title,
        description,
        activitiesRepository,
        timeBeforeExecute,
        timeAfterExecute);

    Mockito.verifyNoMoreInteractions(activitiesRepository);
  }

  private ActivitiesRepository initActivitiesRepository(EditActivityAction action) {
    ActivitiesRepository activitiesRepository = mockActivitiesRepository();
    action.setActivitiesRepository(activitiesRepository);
    return activitiesRepository;
  }

  private void verifyUpdateActivityInvocation(
      String title,
      String description,
      ActivitiesRepository activitiesRepository,
      Date timeBeforeExecute,
      Date timeAfterExecute) {

    ArgumentCaptor<Activity> captor = ArgumentCaptor.forClass(Activity.class);
    Mockito.verify(activitiesRepository).update(captor.capture());

    Assert.assertEquals(1, captor.getValue().getId());
    Assert.assertEquals(title, captor.getValue().getTitle());
    Assert.assertEquals(description, captor.getValue().getDescription());
    Assert.assertEquals(100L, captor.getValue().getCreatedAt());
    Assert.assertTrue(
        captor.getValue().getUpdatedAt() >= timeBeforeExecute.getTime()
        && captor.getValue().getUpdatedAt() <= timeAfterExecute.getTime());
  }

  private ActivitiesRepository mockActivitiesRepository() {
    ActivitiesRepository repository = Mockito.mock(ActivitiesRepository.class);

    BDDMockito
        .given(repository.getById(1))
        .will(new Answer<Activity>() {

          @Override
          public Activity answer(InvocationOnMock invocation) {
            return
                new Activity(
                    (Integer) invocation.getArguments()[0],
                    "",
                    "",
                    100L,
                    200L);
          }
        });

    return repository;
  }
}
