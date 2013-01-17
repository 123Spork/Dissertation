package mygame.messages;
import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

     @Serializable
public class UpdateMessage extends AbstractMessage {
  private Vector3f location,walkDirection;
  private byte ID;
  public UpdateMessage() {}    // empty constructor
  public UpdateMessage(int _id, Vector3f _loc, Vector3f _walkdir) {ID=(byte)_id; location = _loc; walkDirection=_walkdir; } // custom constructor
  
  public Vector3f getLoc()
  {
      return location;
  }
  public Vector3f getDir()
  {
      return walkDirection;
  }
  public byte getID()
  {
      return ID;
  }
  }