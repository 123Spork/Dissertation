package mygame.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class DropItemMessage extends AbstractMessage {
  private int Type;
  private Vector3f position;
  public DropItemMessage() {}    // empty constructor
  public DropItemMessage(int _type,Vector3f _pos){Type=_type; position=_pos;}
  
  public int getType()
  {
      return Type;
  }
  public Vector3f getPosition()
  {
      return position;
  }
 
  
  }