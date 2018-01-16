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
    // we cache all views for now
    View screenView = screen.getView();
    if (screenView == null) {
      LayoutInflater layoutInflater = LayoutInflater.from(this);
      screenView = layoutInflater.inflate(screen.getViewId(), container, false);
      screen.setView(screenView);
    }

    // first - add screenView
    container.addView(screenView);

    // then - remove old views
    for (int i = 0; i < container.getChildCount() - 1; i++) {
      container.removeViewAt(i);
    }

    // animation?
    /* // first - add screenView
    container.addView(screenView);
    screenView.setVisibility(View.GONE);
    Animator animator = new VisibilityAnimation(screenView).toVisible();
    animator.addListener(new AnimatorListenerAdapter() {

      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        // then - remove old views
        for (int i = 0; i < container.getChildCount() - 1; i++) {
          container.removeViewAt(i);
        }
      }
    });
    animator.start();*/
  }

  @Override
  public void onBackPressed() {
    if (router.canHandleBackPress()) {
      router.handleBackPress();
    } else {
      super.onBackPressed();
    }
  }
}
