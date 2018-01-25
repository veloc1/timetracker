package me.veloc1.timetracker.screens.edit_activity;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import me.veloc1.timetracker.R;
import me.veloc1.timetracker.ui.views.ColorPicker;

public class EditActivityView extends LinearLayout implements View.OnClickListener {

  private TextView title;
  private TextView description;

  private EditActivityPresenter presenter;
  private ColorPicker colorPicker;

  public EditActivityView(Context context) {
    super(context);
  }

  public EditActivityView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public EditActivityView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public EditActivityView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    findViewById(R.id.add).setOnClickListener(this);

    title = (TextView) findViewById(R.id.title);
    description = (TextView) findViewById(R.id.description);
    colorPicker = (ColorPicker) findViewById(R.id.color_picker);
  }

  public void setTitle(String title) {
    this.title.setText(title);
  }

  public void setDescription(String description) {
    this.description.setText(description);
  }

  public void closeKeyboard() {
    View view = getFocusedChild();
    if (view != null) {
      InputMethodManager imm =
          (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  public void showError() {
    Snackbar.make(this, R.string.error_loading_activities, Snackbar.LENGTH_LONG).show();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.add:
        presenter
            .onAddClick(
                title.getText().toString(),
                description.getText().toString(),
                colorPicker.getSelectedColor());
        break;
      default:
        throw new RuntimeException("Default branch is not implemented");
    }
  }

  public void setPresenter(EditActivityPresenter presenter) {
    this.presenter = presenter;
  }
}
