package me.veloc1.timetracker.screens.track;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.veloc1.timetracker.R;

public class TrackView extends RelativeLayout implements View.OnClickListener {
  private TrackPresenter presenter;
  private TextView       duration;

  public TrackView(Context context) {
    super(context);
  }

  public TrackView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TrackView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public TrackView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    duration = (TextView) findViewById(R.id.duration);
    findViewById(R.id.done).setOnClickListener(this);
  }

  public void setTrackedTime(final String durationText) {
    getHandler().post(new Runnable() {

      @Override
      public void run() {
        duration.setText(durationText);
      }
    });
  }

  public void hideKeyboard() {
    View view = getFocusedChild();
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.done:
        presenter.onDoneClick();
        break;
      default:
        throw new RuntimeException("Default branch is not implemented");
    }
  }

  public void setPresenter(TrackPresenter presenter) {
    this.presenter = presenter;
  }
}
