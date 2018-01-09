package me.veloc1.timetracker.screens.main;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;

import java.util.List;

public class MainView extends ConstraintLayout {

  private RecyclerView list;

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
  }

  public void setItems(List<Activity> activities) {
    findViewById(R.id.error).setVisibility(View.GONE);

    list = (RecyclerView) findViewById(R.id.list);
    list.setLayoutManager(new LinearLayoutManager(getContext()));
    list.setAdapter(new ActivitiesAdapter(activities));
  }

  public void showProgress() {
    findViewById(R.id.progress).setVisibility(View.VISIBLE);
  }

  public void hideProgress() {
    findViewById(R.id.progress).setVisibility(View.GONE);
  }

  public void showError() {
    findViewById(R.id.error).setVisibility(View.VISIBLE);
  }
}
