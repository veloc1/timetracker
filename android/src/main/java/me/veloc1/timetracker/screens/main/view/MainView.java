package me.veloc1.timetracker.screens.main.view;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.main.MainPresenter;
import me.veloc1.timetracker.ui.animations.FabMorphAnimation;
import me.veloc1.timetracker.ui.animations.VisibilityAnimation;

public class MainView extends RelativeLayout implements View.OnClickListener {

  private RecyclerView        list;
  private View                fab;
  private View                firstRunTip;
  private View                error;
  private View                progress;
  private FabMorphAnimation   fabAnimation;
  private View                bottomBar;
  private View                activities;
  private TextView            activitiesText;
  private VisibilityAnimation progressAnimator;

  private MainPresenter     presenter;
  private ActivitiesAdapter adapter;

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

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    list = (RecyclerView) findViewById(R.id.list);
    list.setLayoutManager(new LinearLayoutManager(getContext()));

    fab = findViewById(R.id.fab);
    fab.setOnClickListener(MainView.this);

    activities = findViewById(R.id.activities);
    activities.setOnClickListener(MainView.this);

    activitiesText = (TextView) findViewById(R.id.activities_text);

    bottomBar = findViewById(R.id.bottom_bar);
    bottomBar.setAlpha(0); // we can't set visibility to gone, because view get width = 0
    // this width will be used in animation

    // so we get a little hack. Disable buttons when menu close, and enable on menu open
    disableMenu();

    firstRunTip = findViewById(R.id.first_run_tip);

    progress = findViewById(R.id.progress);
    progressAnimator = new VisibilityAnimation(progress);

    error = findViewById(R.id.error);

    final View transitView = findViewById(R.id.transit_view);
    fabAnimation = new FabMorphAnimation(fab, bottomBar, transitView);

    findViewById(R.id.close).setOnClickListener(this);
  }

  public void setItems(List<Activity> activities) {
    adapter = new ActivitiesAdapter(activities, presenter);
    list.setAdapter(adapter);
  }

  public void refreshList() {
    // TODO: 16.01.2018 remove handler call
    getHandler().post(new Runnable() {

      @Override
      public void run() {
        adapter.notifyDataSetChanged();
      }
    });
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

  public void showFirstRunTip() {
    firstRunTip.setVisibility(View.VISIBLE);
  }

  public void hideFirstRunTip() {
    firstRunTip.setVisibility(View.GONE);
  }

  public void setActivitiesNameToAddActivity() {
    activitiesText.setText(R.string.add_activity);
  }

  public void setActivitiesNameToDefault() {
    activitiesText.setText(R.string.activities);
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
      case R.id.activities:
        presenter.onActivitiesClick();
        break;
      case R.id.close:
        presenter.onCloseClick();
        break;
      default:
        throw new RuntimeException("Default branch is not implemented");
    }
  }

  public void disableMenu() {
    activities.setEnabled(false);
  }

  public void enableMenu() {
    activities.setEnabled(true);
  }

  public void setPresenter(MainPresenter presenter) {
    this.presenter = presenter;
  }
}
