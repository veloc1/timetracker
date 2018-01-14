package me.veloc1.timetracker.ui.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;

public class VisibilityAnimation extends Animation {

  private final View view;

  public VisibilityAnimation(View view) {
    super();
    this.view = view;
  }

  public Animator toGone() {
    ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
    animator.setDuration(view.getResources().getInteger(android.R.integer.config_mediumAnimTime));
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
    view.setVisibility(View.VISIBLE);

    ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
    animator.setDuration(view.getResources().getInteger(android.R.integer.config_mediumAnimTime));

    return animator;
  }
}
