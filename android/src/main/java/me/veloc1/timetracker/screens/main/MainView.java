package me.veloc1.timetracker.screens.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.ui.animations.FabMorphAnimation;

import java.util.List;

public class MainView extends RelativeLayout implements View.OnClickListener {

  private RecyclerView      list;
  private View              fab;
  private View              firstRunTip;
  private View              error;
  private View              progress;
  private MainPresenter     presenter;
  private FabMorphAnimation fabAnimation;
  private View              bottomBar;

  public MainView(Context context) {
    super(context);
    init();
  }

  public MainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    // View not inflated yet
  }

  public void setItems(List<Activity> activities) {
    list.setAdapter(new ActivitiesAdapter(activities));
  }

  public void showProgress() {
    progress.setVisibility(View.VISIBLE);
  }

  public void hideProgress() {
    progress.setVisibility(View.GONE);
  }

  public void showError() {
    error.setVisibility(View.VISIBLE);
  }

  public void hideError() {
    error.setVisibility(View.GONE);
  }

  public void showFirstRunTip() {
    firstRunTip.setVisibility(View.VISIBLE);
  }

  public void hideFirstRunTip() {
    firstRunTip.setVisibility(View.GONE);
  }

  public void setActivitiesNameToAddActivity() {

  }

  public void setActivitiesNameToDefault() {

  }

  public void morphFabToBottomBar() {
    fabAnimation.toBottomBar();
  }

  public void morphBottomBarToFab() {
    fabAnimation.toFab();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.fab:
        presenter.onFabClick();
        break;
    }
  }

  public void setPresenter(MainPresenter presenter) {
    this.presenter = presenter;

    list = (RecyclerView) findViewById(R.id.list);
    list.setLayoutManager(new LinearLayoutManager(getContext()));

    fab = findViewById(R.id.fab);
    bottomBar = findViewById(R.id.bottom_bar);
    firstRunTip = findViewById(R.id.first_run_tip);
    progress = findViewById(R.id.progress);
    error = findViewById(R.id.error);
    final View transitView = findViewById(R.id.transit_view);

    fab.setOnClickListener(MainView.this);

    fabAnimation = new FabMorphAnimation(fab, bottomBar, transitView);
  }
}
