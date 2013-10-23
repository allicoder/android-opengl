package uk.smarc.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;


public class GameView extends GLSurfaceView {
  private static final String TAG = "GameView";

  private GameRenderer mRenderer;

  public GameView(Context ctx) {
    super(ctx);

    // Specify that we want GLES 2.0
    Log.d(TAG, "Requesting OpenGL ES 2.0");
    setEGLContextClientVersion(2);

    // Seems to be causing a NPE...
//    // Only render the view when the drawing data changes
//    Log.d(TAG, "Requesting only dirty rendering");
//    setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

    Log.d(TAG, "Creating renderer");
    mRenderer = new GameRenderer();
    Log.v(TAG, "Adding renderer to view");
    setRenderer(mRenderer);
  }


}
