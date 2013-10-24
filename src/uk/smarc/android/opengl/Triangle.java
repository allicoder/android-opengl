package uk.smarc.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.util.Log;


/**
 * Represents a drawable triangle.
 */
public class Triangle implements Drawable {
  private static final String TAG = "Triangle";

  private static final int BYTES_PER_FLOAT = 4;

  /**
   * Buffer to hold vertices of the triangel.
   */
  FloatBuffer mVertices;

  /**
   * Number of coordinates per vertex in mCoords.
   * i.e. the dimension of the bundle space spanned by all the coordinates of a
   * vertex, e.g. spatial, colour, texture...
   */
  private final int COORDS_PER_VERTEX = 3;

  /**
   * Coordinates of this triangle. Specified in the usual counterclockwise 
   * winding order.
   */
  private float[] mCoords;


  // OpenGL stuff

  /**
   * We need a vertex shader...
   */
  private final String mVertexShaderCode;

  /**
   * and a fragment shader.
   */
  private final String mFragmentShaderCode;

  /**
   * We also need a program that'll run both shaders.
   */
  private int mProgram;

  /**
   * Handle to the position of the vertices in the vertex shader.
   */
  private int mPositionHandle;

  /**
   * Handle to the position of the colour in the fragment shader.
   */
  private int mColorHandle;

  /**
   * Colour that we'll draw the triangle as.
   */
  private float[] mColor;

  /**
   * Number of vertices we have. In general, this is
   * mCoords.length / COORDS_PER_VERTEX; in practice, this is a triangle. So we have 3.
   */
  private int mVertexCount;
  
  private int mVertexStride;

  private int err;

  public Triangle() {
    Log.d(TAG, "Creating triangle");
    mCoords = new float[] {
         0.0f,  0.622008459f, 0.0f,   // top
        -0.5f, -0.311004243f, 0.0f,   // bottom left
         0.5f, -0.311004243f, 0.0f    // bottom right
    };
    mColor = new float[] { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    mVertexCount = mCoords.length / COORDS_PER_VERTEX;
    mVertexStride = COORDS_PER_VERTEX * BYTES_PER_FLOAT;

    // Initialise a ByteBuffer for storing the vertices.
    ByteBuffer vb = ByteBuffer.allocateDirect(
        // Need space for the number of coordinates, but in bytes.
        mCoords.length * BYTES_PER_FLOAT);
    // Use the device hardware's native byte order, so that no extra
    // conversions are necessary.
    vb.order(ByteOrder.nativeOrder());

    // Turn the bytebuffer into a floatbuffer, and add the coordinates.
    mVertices = vb.asFloatBuffer();
    mVertices.put(mCoords);
    mVertices.position(0);

    // ** We now set up the OpenGL shaders, and compile them (since they're
    // already known).
    mVertexShaderCode = 
        "attribute vec4 vPosition;"
      + "void main() {"
      + "  gl_Position = vPosition;"
      + "}";

    mFragmentShaderCode = 
        "precision mediump float;"
      + "uniform vec4 vColor;"
      + "void main() {"
      + "  gl_FragColor = vColor;"
      + "}";

    // Compile and load the shaders
    // This is expesnive, so we do it here once only.
    int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, mVertexShaderCode);
    int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, 
        mFragmentShaderCode);

    // Create a program to run the shaders.
    mProgram = GLES20.glCreateProgram();
    GLES20.glAttachShader(mProgram, vertexShader);
    GLES20.glAttachShader(mProgram, fragmentShader);

    // Create the OpenGL program executables.
    GLES20.glLinkProgram(mProgram);

    err = GLES20.glGetError();
    if (0 != err) {
      Log.d(TAG, "glGetError: " + GLES20.glGetError());
    }

  }

  @Override
  public void draw() {
//    Log.d(TAG, "Drawing triangle");
    // So, we're going to need the program we created in the constructor.
    GLES20.glUseProgram(mProgram);

    // We'll need a handle to the vPosition member of the vertex shader.
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
    // Next we tell OpenGL it's where we're going to store some vertices
    GLES20.glEnableVertexAttribArray(mPositionHandle);
    // Use that to get a handle to the triangle's vertices.
    GLES20.glVertexAttribPointer(mPositionHandle, mVertexCount /* number of vertices */,
        GLES20.GL_FLOAT, false, mVertexStride /* stride is 0, since the vertices are 
                                     tightly packed (we dont' have the 
                                     texture coordinates or anything else in 
                                     the same array)*/,
        mVertices);

    // Get a handle to the fragment shader's vColor member:
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    // Use that to set the colour for drawing the triangle.
    GLES20.glUniform4fv(mColorHandle, 1, mColor, 0);

    // Finally, draw the triangle
    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);

    // Disable the vertex array now that we no longer need it.
    GLES20.glDisableVertexAttribArray(mPositionHandle);

    err = GLES20.glGetError();
    if (0 != err) {
      Log.d(TAG, "glGetError: " + GLES20.glGetError());
    }
  }

  /**
   * Compile the shader code given.
   * @param type e.g. GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER.
   */
  private static int loadShader(int type, String shaderCode) {
    int shader = GLES20.glCreateShader(type);
    GLES20.glShaderSource(shader, shaderCode);
    GLES20.glCompileShader(shader);

    return shader;
  }

}
