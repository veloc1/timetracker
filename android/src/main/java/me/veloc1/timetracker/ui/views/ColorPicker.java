package me.veloc1.timetracker.ui.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import me.veloc1.timetracker.R;

public class ColorPicker extends LinearLayout {

  private int selectedColor;

  public ColorPicker(Context context) {
    super(context);
  }

  public ColorPicker(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ColorPicker(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public ColorPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    findViewById(R.id.color1).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        selectedColor = ContextCompat.getColor(getContext(), R.color.color1);
      }
    });
    findViewById(R.id.color2).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        selectedColor = ContextCompat.getColor(getContext(), R.color.color2);
      }
    });
    findViewById(R.id.color3).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        selectedColor = ContextCompat.getColor(getContext(), R.color.color3);
      }
    });
    findViewById(R.id.color4).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        selectedColor = ContextCompat.getColor(getContext(), R.color.color4);
      }
    });
    findViewById(R.id.color5).setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        selectedColor = ContextCompat.getColor(getContext(), R.color.color5);
      }
    });
  }

  public int getSelectedColor() {
    return selectedColor;
  }
}
