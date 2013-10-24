package uk.smarc.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * Represents a drawable square.
 * To draw squares, we decompose them into triangles. So to describe this shape
 * to OpenGL, we need an array of vertices /and/ an array of shorts to tell it
 * how those inner triangles are configured, which depends on how we do the
 * splitting.
 */
public class Square implements Drawable {

  private static final int BYTES_PER_FLOAT = 4;
  private static final int BYTES_PER_SHORT = 2;

  /**
   * Buffer to hold vertices of the square.
   */
  FloatBuffer mVertices;

  /**
   * Buffer to hold the drawing order of the vertices.
   */
  ShortBuffer mVertexOrder;

  /**
   * Number of coordinates per vertex in mCoords.
   * i.e. the dimension of the bundle space spanned by all the coordinates of a
   * vertex, e.g. spatial, colour, texture...
   */
  private final int D = 3;

  /**
   * Coordinates of this shape. Specified in the usual counterclockwise 
   * winding order.
   */
  private float[] mCoords;

  /**
   * The order in which we draw the vertices. This relates to how we split the
   * square into triangles.
   */
  private short[] mDrawOrder;

  public Square() {
    // We describe squares to OpenGL by splitting them into triangles.
    mCoords = new float[] { -0.5f,  0.5f, 0.0f,   // top left
                            -0.5f, -0.5f, 0.0f,   // bottom left
                             0.5f, -0.5f, 0.0f,   // bottom right
                             0.5f,  0.5f, 0.0f }; // top right
    mDrawOrder = new short[] { 0, 1, 2, 0, 2, 3 };

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

    // We now do the same with mVertexOrder.
    ByteBuffer vo = ByteBuffer.allocateDirect(
        mDrawOrder.length * BYTES_PER_SHORT);
    vo.order(ByteOrder.nativeOrder());
    mVertexOrder = vo.asShortBuffer();
    mVertexOrder.put(mDrawOrder);
    mVertexOrder.position(0);
  }

  @Override
  public void draw() {
  }

}
