package uk.smarc.android.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;


public class GameRenderer implements GLSurfaceView.Renderer {

  public GameRenderer() {
  }

  /**
   * Called once when the View is created.
   */
  public void onSurfaceCreated(GL10 unused, EGLConfig config) {
    GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
  }

  /**
   * Called for each redraw of the view.
   */
  public void onDrawFrame(GL10 unused) {
    GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
  }

  /**
   * Called when the view's geometry changes.
   */
  public void onSurfaceChanged(GL10 unused, int width, int height) {
    GLES20.glViewport(0, 0, width, height);
  }

}
