package me.veloc1.timetracker.screens.activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;

public class ActivityViewHolder extends RecyclerView.ViewHolder {

  private final TextView title;
  private final View     remove;
  private final View     edit;

  private final ActivitiesPresenter presenter;
  private final View                color;

  public ActivityViewHolder(View itemView, ActivitiesPresenter presenter) {
    super(itemView);

    title = (TextView) itemView.findViewById(R.id.title);
    color = itemView.findViewById(R.id.color);
    edit = itemView.findViewById(R.id.edit);
    remove = itemView.findViewById(R.id.remove);

    this.presenter = presenter;
  }

  public void bind(final Activity activity) {
    title.setText(activity.getTitle());
    color.setBackgroundColor(activity.getColor());
    edit.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onEditClick(activity.getId());
      }
    });
    remove.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        presenter.onRemoveClick(activity.getId());
      }
    });
  }
}
