package mygame.messages;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

     @Serializable
public class UpdateMessageNID extends AbstractMessage {
  private Vector3f location, walkDirection;
  public UpdateMessageNID() {}    // empty constructor
  public UpdateMessageNID(Vector3f _loc, Vector3f _walkdir) {location = _loc; walkDirection=_walkdir; } // custom constructor
  
  public Vector3f getLoc()
  {
      return location;
  }
  public Vector3f getDir()
  {
      return walkDirection;
  }
  }