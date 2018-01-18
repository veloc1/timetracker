package me.veloc1.timetracker.screens.main.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;
import me.veloc1.timetracker.screens.main.MainPresenter;

public class ActivityViewHolder extends RecyclerView.ViewHolder {

  private final TextView      title;
  private final TextView      duration;
  private final View          track;
  private final MainPresenter presenter;

  public ActivityViewHolder(View itemView, MainPresenter presenter) {
    super(itemView);
    title = (TextView) itemView.findViewById(R.id.title);
    duration = (TextView) itemView.findViewById(R.id.duration);
    track = itemView.findViewById(R.id.track);
    this.presenter = presenter;
  }

  public void bind(final Activity activity) {
    title.setText(activity.getTitle());

    duration.setText(presenter.getCurrentDuration(activity.getId()));

    if (presenter.shouldViewShowTrackButton(activity.getId())) {
      track.setVisibility(View.VISIBLE);
    } else {
      track.setVisibility(View.GONE);
    }

    track.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onTrackClick(activity.getId());
      }
    });
  }
}
