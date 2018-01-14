package me.veloc1.timetracker.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import me.veloc1.timetracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ActivityStatisticDiagram extends View {
  private static final int DEFAULT_GRID_PAINT_WIDTH   = 4;
  private static final int DEFAULT_COLUMN_PAINT_WIDTH = 16;

  private Paint gridPaint;
  private Paint columnPaint;

  private float gridPaintWidth;
  private float columnPaintWidth;
  private int   gridSectionsCount;

  public ActivityStatisticDiagram(Context context) {
    super(context);
    init(null);
  }

  public ActivityStatisticDiagram(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public ActivityStatisticDiagram(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public ActivityStatisticDiagram(
      Context context,
      AttributeSet attrs,
      int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray attributes =
        getContext().obtainStyledAttributes(attrs, R.styleable.ActivityStatisticDiagram);

    gridPaint = new Paint();
    gridPaint.setColor(getResources().getColor(R.color.divider));
    gridPaintWidth =
        attributes.getDimension(
            R.styleable.ActivityStatisticDiagram_gridWidth, DEFAULT_GRID_PAINT_WIDTH);
    gridPaint.setStrokeWidth(gridPaintWidth);

    columnPaint = new Paint();
    columnPaint.setColor(getResources().getColor(R.color.column));
    columnPaintWidth =
        attributes.getDimension(
            R.styleable.ActivityStatisticDiagram_columnWidth, DEFAULT_COLUMN_PAINT_WIDTH);
    columnPaint.setStrokeWidth(columnPaintWidth);

    gridSectionsCount =
        attributes.getInt(
            R.styleable.ActivityStatisticDiagram_gridSectionCount, 4);

    attributes.recycle();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    drawGrid(canvas);
    drawColumns(canvas);
  }

  private void drawGrid(Canvas canvas) {
    int currentHeight   = (int) (gridPaintWidth / 2);
    int heightOfSection = (int) ((getHeight() - gridPaintWidth) / gridSectionsCount);

    canvas.drawLine(0, currentHeight, getWidth(), currentHeight, gridPaint);
    for (int i = 0; i < gridSectionsCount; i++) {
      currentHeight += heightOfSection;
      canvas.drawLine(0, currentHeight, getWidth(), currentHeight, gridPaint);
    }
  }

  private void drawColumns(Canvas canvas) {
    List<Integer> dummyValues = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      dummyValues.add(new Random().nextInt(30));
    }

    int maxValue       = Collections.max(dummyValues);
    int widthOfSection = (int) ((getWidth() - columnPaintWidth) / (dummyValues.size() - 1));

    int currentX = (int) columnPaintWidth / 2;

    drawColumnForValue(canvas, maxValue, currentX, dummyValues.get(0));

    for (int i = 1; i < dummyValues.size() - 1; i++) {
      int currentValue = dummyValues.get(i);

      currentX += widthOfSection;
      drawColumnForValue(canvas, maxValue, currentX, currentValue);
    }

    // last one should be drawed with explicit X, because of wrong rounding in
    // widthOfSection we get little x translation
    currentX = (int) (getWidth() - columnPaintWidth / 2);
    drawColumnForValue(canvas, maxValue, currentX, dummyValues.get(dummyValues.size() - 1));
  }

  private void drawColumnForValue(Canvas canvas, float maxValue, int currentX, int currentValue) {
    float scaledY = currentValue / maxValue * getHeight();

    canvas.drawLine(currentX, getHeight(), currentX, getHeight() - scaledY, columnPaint);
  }
}
