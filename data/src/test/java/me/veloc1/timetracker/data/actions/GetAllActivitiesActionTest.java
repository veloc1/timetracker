package me.veloc1.timetracker.data.actions;

import me.veloc1.timetracker.data.actions.base.BaseActionTest;
import me.veloc1.timetracker.data.repositories.ActivitiesRepository;
import org.junit.Test;
import org.mockito.Mockito;

public class GetAllActivitiesActionTest extends BaseActionTest {

  @Test
  public void getAllActivities() throws Throwable {
    GetAllActivitiesAction action               = new GetAllActivitiesAction();
    ActivitiesRepository   activitiesRepository = Mockito.mock(ActivitiesRepository.class);
    action.setActivitiesRepository(activitiesRepository);

    execute(action);

    Mockito.verify(activitiesRepository).getActivitiesSortedByUpdateDate();

    Mockito.verifyNoMoreInteractions(activitiesRepository);
  }
}
