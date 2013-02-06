package mygame.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


@Serializable
public class AddWarpMessage extends AbstractMessage {
  private String spritename;// custom message data
  Vector3f position, rotation, gotoposition;
  public AddWarpMessage() {}    // empty constructor
  public AddWarpMessage(String t, Vector3f pos, Vector3f rot, Vector3f newpos) {spritename=t; rotation=rot; position=pos; gotoposition=newpos;} // custom constructor    
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
  public Vector3f getGoto()
  {
      return gotoposition;
  }
}
