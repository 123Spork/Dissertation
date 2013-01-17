package mygame.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class AddDynamicItemMessage extends AbstractMessage {
  private int ID,Type;
  private Vector3f position;
  public AddDynamicItemMessage() {}    // empty constructor
  public AddDynamicItemMessage(int _id, int _type, Vector3f _pos){ID=_id; Type=_type; position=_pos;}
  
  public int getID()
  {
      return ID;
  }
  public int getType()
  {
      return Type;
  }
  public Vector3f getPosition()
  {
      return position;
  }
 
  
  }