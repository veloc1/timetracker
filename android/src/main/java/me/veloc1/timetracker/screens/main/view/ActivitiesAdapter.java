package me.veloc1.timetracker.screens.main.view;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.main.MainPresenter;

public class ActivitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public static final int HEADER = 0;
  public static final int ITEM   = 1;

  private final List<Activity> activities;
  private final MainPresenter  presenter;

  public ActivitiesAdapter(List<Activity> activities, MainPresenter presenter) {
    super();
    this.activities = activities;
    this.presenter = presenter;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    if (viewType == HEADER) {
      View view = inflater.inflate(R.layout.item_header_main_statistic, parent, false);
      return new StatisticViewHolder(view, presenter);
    } else {
      View view = inflater.inflate(R.layout.item_main, parent, false);
      return new ActivityViewHolder(view, presenter);
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (getItemViewType(position) == HEADER) {
      ((StatisticViewHolder) holder).bind();
    } else {
      ((ActivityViewHolder) holder).bind(activities.get(position - 1));
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return HEADER;
    }
    return ITEM;
  }

  @Override
  public int getItemCount() {
    return activities.size() + 1;
  }
}
