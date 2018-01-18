package me.veloc1.timetracker.screens.main.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.main.ActivityStatisticDisplayItem;

public class StatisticSubItem extends LinearLayout {
  private ActivityStatisticDisplayItem statistic;
  private TextView                     title;
  private View                         color;

  public StatisticSubItem(Context context) {
    super(context);
  }

  public StatisticSubItem(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public StatisticSubItem(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public StatisticSubItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    title = (TextView) findViewById(R.id.title);
    color = findViewById(R.id.color);
  }

  public void setStatistic(ActivityStatisticDisplayItem statistic) {
    this.statistic = statistic;
    title.setText(statistic.getTitle());
    color.setBackgroundColor(statistic.getColor());
  }
}
