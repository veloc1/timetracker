package me.veloc1.timetracker.ui.animations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import me.veloc1.timetracker.R;

public class FabMorphAnimation {

  private final View fab;
  private final View bottomBar;
  private final View transitView;

  public FabMorphAnimation(View fab, View bottomBar, View transitView) {
    this.fab = fab;
    this.bottomBar = bottomBar;
    this.transitView = transitView;
  }

  public void toBottomBar() {
    ShapeDrawable transitDrawable = createTransitDrawableInFabState(fab);
    copyParamsFromFabToTransitDrawable(fab, transitDrawable);

    choreographFabToBottomBarAnimation(fab, bottomBar, transitView, transitDrawable);
  }

  public void toFab() {
    ShapeDrawable transitDrawable = createTransitDrawableInBottomBarState(bottomBar);
    copyParamsFromBottomBarToTransitDrawable(bottomBar, transitDrawable);

    choreographBottomBarToFabAnimation(fab, bottomBar, transitView, transitDrawable);
  }

  private ShapeDrawable createTransitDrawableInFabState(View fab) {
    ShapeDrawable drawable = new ShapeDrawable();

    int color = ContextCompat.getColor(fab.getContext(), R.color.colorAccent);

    PorterDuffColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
    drawable.setColorFilter(filter);

    setRoundedShapeToDrawable(0, drawable);
    return drawable;
  }

  private ShapeDrawable createTransitDrawableInBottomBarState(View bottomBar) {
    ShapeDrawable drawable = new ShapeDrawable();

    int color = ContextCompat.getColor(bottomBar.getContext(), R.color.colorAccent);

    PorterDuffColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
    drawable.setColorFilter(filter);

    setRoundedShapeToDrawable(0, drawable);
    return drawable;
  }

  private void setRoundedShapeToDrawable(float radius, ShapeDrawable drawable) {
    float[] r = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};

    RoundRectShape shape = new RoundRectShape(r, null, null);
    drawable.setShape(shape);
  }

  private void copyParamsFromFabToTransitDrawable(View fab, ShapeDrawable transitDrawable) {
    setRoundedShapeToDrawable(fab.getWidth() / 2, transitDrawable);

    ViewCompat.setBackground(transitView, transitDrawable);
  }

  private void copyParamsFromBottomBarToTransitDrawable(View bottomBar, ShapeDrawable drawable) {
    setRoundedShapeToDrawable(0, drawable);

    ViewCompat.setBackground(transitView, drawable);
  }

  private void choreographFabToBottomBarAnimation(
      View fab,
      View bottomBar,
      View transitView,
      ShapeDrawable transitDrawable) {

    Animator hideFab = new VisibilityAnimation(fab).toGone();

    Animator transitAnimation =
        createFabToBottomBarTransitAnimation(fab, bottomBar, transitView, transitDrawable);

    Animator showBottomBar = new VisibilityAnimation(bottomBar).toVisible();

    int duration = fab.getResources().getInteger(android.R.integer.config_shortAnimTime);

    AnimatorSet fullAnimation = new AnimatorSet();
    fullAnimation.setDuration(duration);
    fullAnimation.playSequentially(hideFab, transitAnimation, showBottomBar);
    fullAnimation.start();
  }

  private void choreographBottomBarToFabAnimation(
      View fab,
      View bottomBar,
      View transitView,
      ShapeDrawable transitDrawable) {

    Animator hideBar = new VisibilityAnimation(bottomBar).toGone();

    Animator transitAnimation =
        createBottomBarToFabTransitAnimation(fab, bottomBar, transitView, transitDrawable);

    Animator showFab = new VisibilityAnimation(fab).toVisible();

    int duration = fab.getResources().getInteger(android.R.integer.config_shortAnimTime);

    AnimatorSet fullAnimation = new AnimatorSet();
    fullAnimation.setDuration(duration);
    fullAnimation.playSequentially(hideBar, transitAnimation, showFab);
    fullAnimation.start();
  }

  private AnimatorSet createFabToBottomBarTransitAnimation(
      View fab,
      View bottomBar,
      View transitView,
      ShapeDrawable transitDrawable) {

    ViewGroup.MarginLayoutParams layoutParams =
        (ViewGroup.MarginLayoutParams) fab.getLayoutParams();

    Animator removeMargin = animateMargins(transitView, layoutParams.rightMargin, 0);
    Animator removeRadius = animateRadius(transitDrawable, fab.getWidth() / 2, 0);
    Animator changeWidth =
        animateWidth(transitView, fab.getWidth(), bottomBar.getMeasuredWidth());
    Animator changeHeight =
        animateHeight(transitView, fab.getHeight(), bottomBar.getMeasuredHeight());

    AnimatorSet transitAnimation = new AnimatorSet();
    transitAnimation.setInterpolator(new AnticipateOvershootInterpolator(0.5f));
    transitAnimation.playTogether(removeMargin, removeRadius, changeWidth, changeHeight);
    return transitAnimation;
  }

  private AnimatorSet createBottomBarToFabTransitAnimation(
      View fab,
      View bottomBar,
      View transitView,
      ShapeDrawable transitDrawable) {

    ViewGroup.MarginLayoutParams layoutParams =
        (ViewGroup.MarginLayoutParams) fab.getLayoutParams();

    Animator removeMargin = animateMargins(transitView, 0, layoutParams.rightMargin);
    Animator removeRadius = animateRadius(transitDrawable, 0, fab.getWidth() / 2);
    Animator changeWidth  = animateWidth(transitView, bottomBar.getWidth(), fab.getWidth());
    Animator changeHeight = animateHeight(transitView, bottomBar.getHeight(), fab.getHeight());

    AnimatorSet transitAnimation = new AnimatorSet();
    transitAnimation.setInterpolator(new AnticipateOvershootInterpolator(0.5f));
    transitAnimation.playTogether(removeMargin, removeRadius, changeWidth, changeHeight);
    return transitAnimation;
  }

  private Animator animateMargins(final View transitView, int from, int to) {
    ValueAnimator marginAnimator = ValueAnimator.ofInt(from, to);

    marginAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        int value = (int) animation.getAnimatedValue();

        ViewGroup.MarginLayoutParams layoutParams =
            (ViewGroup.MarginLayoutParams) transitView.getLayoutParams();
        layoutParams.setMargins(value, value, value, value);
        transitView.setLayoutParams(layoutParams);
      }
    });

    return marginAnimator;
  }

  private Animator animateRadius(
      final ShapeDrawable drawable,
      float fromRadius,
      float toRadius) {

    ValueAnimator valueAnimator = ValueAnimator.ofFloat(fromRadius, toRadius);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        setRoundedShapeToDrawable((Float) animation.getAnimatedValue(), drawable);
      }
    });
    return valueAnimator;
  }

  private Animator animateWidth(final View transitView, int from, int to) {
    ValueAnimator widthAnimator = ValueAnimator.ofInt(from, to);
    widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int val = (int) valueAnimator.getAnimatedValue();

        ViewGroup.LayoutParams layoutParams = transitView.getLayoutParams();
        layoutParams.width = val;
        transitView.setLayoutParams(layoutParams);
      }
    });
    return widthAnimator;
  }

  private Animator animateHeight(final View transitView, int from, int to) {
    ValueAnimator heightAnimator = ValueAnimator.ofInt(from, to);
    heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int val = (int) valueAnimator.getAnimatedValue();

        ViewGroup.LayoutParams layoutParams = transitView.getLayoutParams();
        layoutParams.height = val;
        transitView.setLayoutParams(layoutParams);
      }
    });
    return heightAnimator;
  }
}
