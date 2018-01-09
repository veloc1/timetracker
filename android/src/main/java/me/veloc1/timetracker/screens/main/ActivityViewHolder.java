package me.veloc1.timetracker.screens.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.data.types.Activity;

public class ActivityViewHolder extends RecyclerView.ViewHolder {

  private final TextView id;
  private final TextView title;

  public ActivityViewHolder(View itemView) {
    super(itemView);
    id = (TextView) itemView.findViewById(R.id.item_id);
    title = (TextView) itemView.findViewById(R.id.title);
  }

  public void bind(Activity activity) {
    id.setText(String.valueOf(activity.getId()));
    title.setText(activity.getTitle());
  }
}
