package me.veloc1.timetracker.screens.activities;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.ui.animations.VisibilityAnimation;

import java.util.List;

public class ActivitiesView extends FrameLayout implements View.OnClickListener {

  private RecyclerView        list;
  private View                progress;
  private VisibilityAnimation progressAnimator;
  private View                error;
  private ActivitiesPresenter presenter;

  public ActivitiesView(Context context) {
    super(context);
  }

  public ActivitiesView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ActivitiesView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public ActivitiesView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    list = (RecyclerView) findViewById(R.id.list);
    list.setLayoutManager(new LinearLayoutManager(getContext()));

    progress = findViewById(R.id.progress);
    progressAnimator = new VisibilityAnimation(progress);

    findViewById(R.id.add).setOnClickListener(this);

    error = findViewById(R.id.error);
  }

  public void setItems(List<Activity> activities) {
    list.setAdapter(new ActivitiesAdapter(activities, presenter));
  }

  public void showProgress() {
    progressAnimator.toVisible().start();
  }

  public void hideProgress() {
    progressAnimator.toGone().start();
  }

  public void showError() {
    error.setVisibility(View.VISIBLE);
  }

  public void hideError() {
    error.setVisibility(View.GONE);
  }

  public void setPresenter(ActivitiesPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.add:
        presenter.onAddClick();
        break;
      default:
        throw new RuntimeException("Default branch not implemented");
    }
  }
}
