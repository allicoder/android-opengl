package uk.smarc.android.opengl;


/**
 * Represents an object in a virtual space that can draw itself to a graphics
 * context.
 * TODO: add way to specify/detect version in use.
 * TODO: add way to specify a context if needed, e.g. override different
 * methods.
 */
public interface Drawable {

  /**
   * Context should have been suitably initialised with initial values for
   * colour, coordinate system etc. before calling this.
   */
  public void draw();

}
