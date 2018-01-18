package me.veloc1.timetracker.screens.main.view;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.main.ActivityStatisticDisplayItem;
import me.veloc1.timetracker.screens.main.MainPresenter;
import me.veloc1.timetracker.ui.views.MainStatisticDiagram;

public class StatisticViewHolder extends RecyclerView.ViewHolder {

  private final MainStatisticDiagram diagram;
  private final View                 error;
  private final MainPresenter        presenter;
  private final ViewGroup            itemsLayout;
  private       LayoutInflater       layoutInflater;

  public StatisticViewHolder(View itemView, MainPresenter presenter) {
    super(itemView);
    itemsLayout = (ViewGroup) itemView.findViewById(R.id.items);
    diagram = (MainStatisticDiagram) itemView.findViewById(R.id.statistic_diagram);
    error = itemView.findViewById(R.id.statistic_error);

    layoutInflater = LayoutInflater.from(itemsLayout.getContext());

    this.presenter = presenter;
  }

  public void bind() {
    if (presenter.isErrorInStatistic()) {
      error.setVisibility(View.VISIBLE);
    } else {
      error.setVisibility(View.GONE);
    }

    List<ActivityStatisticDisplayItem> statistics = presenter.getStatistics();
    diagram.setItems(statistics);

    itemsLayout.removeAllViews();
    for (final ActivityStatisticDisplayItem statistic : statistics) {
      StatisticSubItem statisticItem =
          (StatisticSubItem) layoutInflater.inflate(R.layout.item_statistic, itemsLayout, false);

      statisticItem.setStatistic(statistic);
      itemsLayout.addView(statisticItem);
    }
  }
}
