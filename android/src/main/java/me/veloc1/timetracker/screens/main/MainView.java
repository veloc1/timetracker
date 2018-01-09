package me.veloc1.timetracker.screens.main;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.TextView;
import me.veloc1.timetracker.R;

public class MainView extends ConstraintLayout {

  public MainView(Context context) {
    super(context);
    init();
  }

  public MainView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
  }

  public void setText(String text) {
    ((TextView) findViewById(R.id.text)).setText(text);
  }
}
