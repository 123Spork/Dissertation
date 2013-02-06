package mygame.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


@Serializable
public class AddScriptedObjectMessage extends AbstractMessage {
  private String spritename;// custom message data
  Vector3f position, rotation;
  int type;
  public AddScriptedObjectMessage() {}    // empty constructor
  public AddScriptedObjectMessage(String t, int _type, Vector3f pos, Vector3f rot) {type=_type; spritename=t; rotation=rot; position=pos;} // custom constructor    
  public String getSpriteName()
  {
      return spritename;
  }
  public Vector3f getPosition()
  {
      return position;
  }
  public Vector3f getRotation()
  {
      return rotation;
  }
  public int getType()
  {
      return type;
  }
}
