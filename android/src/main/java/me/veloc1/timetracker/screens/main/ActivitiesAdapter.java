package me.veloc1.timetracker.screens.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;

import java.util.List;

public class ActivitiesAdapter extends RecyclerView.Adapter<ActivityViewHolder> {
  private final List<Activity> activities;

  public ActivitiesAdapter(List<Activity> activities) {
    super();
    this.activities = activities;
  }

  @Override
  public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ActivityViewHolder(
        LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_activity, parent, false));
  }

  @Override
  public void onBindViewHolder(ActivityViewHolder holder, int position) {
    holder.bind(activities.get(position));
  }

  @Override
  public int getItemCount() {
    return activities.size();
  }
}
