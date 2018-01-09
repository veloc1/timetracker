package me.veloc1.timetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.veloc1.timetracker.routing.Router;
import me.veloc1.timetracker.routing.ScreenContainer;
import me.veloc1.timetracker.screens.base.Screen;

public class MainActivity extends AppCompatActivity implements ScreenContainer {

  private Router    router;
  private ViewGroup container;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    container = (ViewGroup) findViewById(R.id.container);

    router = new Router(this);
    router.startMainScreen();
  }

  @Override
  public void setScreen(Screen screen) {
    container.removeAllViews();

    LayoutInflater layoutInflater = LayoutInflater.from(this);
    View           screenView     = layoutInflater.inflate(screen.getViewId(), container, false);
    screen.setView(screenView);
    container.addView(screenView);

    screen.start();
  }
}
