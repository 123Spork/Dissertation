package mygame.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


@Serializable
public class AddWarpMessage extends AbstractMessage {
  private int type;// custom message data
  Vector3f position, gotoposition;
  public AddWarpMessage() {}    // empty constructor
  public AddWarpMessage(int t, Vector3f pos, Vector3f newpos) {type=t; position=pos; gotoposition=newpos;} // custom constructor    
  public int getType()
  {
      return type;
  }
  public Vector3f getPosition()
  {
      return position;
  }
  public Vector3f getGoto()
  {
      return gotoposition;
  }
}
