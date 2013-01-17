package mygame.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class AddSpriteMessage extends AbstractMessage {
  private int ID;
  private String model;
  private Vector3f position;
  public AddSpriteMessage() {}    // empty constructor
  public AddSpriteMessage(int _id,Vector3f _pos,String _model){ID=_id; model=_model; position=_pos;}
  
  public int getID()
  {
      return ID;
  }
  public String getModel()
  {
      return model;
  }
  public Vector3f getPosition()
  {
      return position;
  }
 
  
  }