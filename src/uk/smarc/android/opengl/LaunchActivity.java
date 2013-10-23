package uk.smarc.android.opengl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class LaunchActivity extends Activity {

  /** 
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  public void onStartBtnClick(View v) {
    Intent intent = new Intent(this, GameActivity.class);
    startActivity(intent);
  }

}
