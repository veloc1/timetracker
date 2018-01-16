package me.veloc1.timetracker.screens.activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivityViewHolder> {

  private final List<Activity>      data;
  private final ActivitiesPresenter presenter;

  public ActivitiesAdapter(List<Activity> activities, ActivitiesPresenter presenter) {
    super();
    data = activities;
    this.presenter = presenter;
  }

  @Override
  public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_activity, parent, false);
    return new ActivityViewHolder(view, presenter);
  }

  @Override
  public void onBindViewHolder(ActivityViewHolder holder, int position) {
    holder.bind(data.get(position));
  }

  @Override
  public int getItemCount() {
    return data.size();
  }
}
