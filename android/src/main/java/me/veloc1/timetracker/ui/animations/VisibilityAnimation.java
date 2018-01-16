package me.veloc1.timetracker.ui.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;

public class VisibilityAnimation extends Animation {

  private final View           view;
  private final ObjectAnimator animator;

  public VisibilityAnimation(View view) {
    super();
    this.view = view;
    animator = new ObjectAnimator();
    animator.setTarget(view);
    animator.setPropertyName("alpha");
    animator.setDuration(view.getResources().getInteger(android.R.integer.config_mediumAnimTime));
  }

  public Animator toGone() {
    if (animator.isRunning()) {
      animator.cancel();
    }

    animator.setFloatValues(1, 0);
    animator.addListener(new AnimatorListenerAdapter() {

      @Override
      public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        view.setVisibility(View.GONE);
      }
    });
    return animator;
  }

  public Animator toVisible() {
    if (animator.isRunning()) {
      animator.cancel();
    }
    view.setVisibility(View.VISIBLE);

    animator.setFloatValues(0, 1);

    return animator;
  }
}
