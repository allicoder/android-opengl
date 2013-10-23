package uk.smarc.android.opengl;

import android.app.Activity;
import android.os.Bundle;


public class GameActivity extends Activity {

  private GameView mView;

  /** 
   * Called when the activity is first created. 
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mView = new GameView(this);
    setContentView(mView);
  }

}
