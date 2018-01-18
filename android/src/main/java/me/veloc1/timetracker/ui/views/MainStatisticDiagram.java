package me.veloc1.timetracker.ui.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.screens.main.ActivityStatisticDisplayItem;

public class MainStatisticDiagram extends View {
  private List<ActivityStatisticDisplayItem> items;

  private Paint paint;
  private RectF rect;

  public MainStatisticDiagram(Context context) {
    super(context);
  }

  public MainStatisticDiagram(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MainStatisticDiagram(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MainStatisticDiagram(
      Context context,
      AttributeSet attrs,
      int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setItems(List<ActivityStatisticDisplayItem> items) {
    this.items = items;
    paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);

    rect = new RectF();
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    super.dispatchDraw(canvas);

    if (isInEditMode()) {
      List<ActivityStatisticDisplayItem> previewItems = new ArrayList<>();

      int color = ContextCompat.getColor(getContext(), R.color.colorAccent);

      ActivityStatisticDisplayItem item = new ActivityStatisticDisplayItem(0, "", color, 25);
      previewItems.add(item);

      color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
      item = new ActivityStatisticDisplayItem(1, "", color, 25);
      previewItems.add(item);

      setItems(previewItems);
    }

    int size    = Math.min(getWidth(), getHeight());
    int centerX = getLeft() + getWidth() / 2;
    int centerY = getTop() + getHeight() / 2;

    int left   = centerX - size / 2 - getLeft();
    int top    = centerY - size / 2 - getTop();
    int right  = centerX + size / 2 - getLeft();
    int bottom = centerY + size / 2 - getTop();
    rect.set(left, top, right, bottom);

    float sum = 0;
    for (final ActivityStatisticDisplayItem item : items) {
      // TODO: 18.01.2018 calculate one percent in setItems
      sum += item.getValue();
    }
    float accumulatedValue = -90; // start arc from top
    for (final ActivityStatisticDisplayItem item : items) {
      paint.setColor(item.getColor());
      float value = item.getValue() / sum * 360f;
      canvas.drawArc(rect, accumulatedValue, value, true, paint);
      accumulatedValue += value;
    }
  }
}
