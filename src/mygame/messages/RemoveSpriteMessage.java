package mygame.messages;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class RemoveSpriteMessage extends AbstractMessage {
  private int ID;
  public RemoveSpriteMessage() {}    // empty constructor
  public RemoveSpriteMessage(int _id){ID=_id;}
  
  public int getID()
  {
      return ID;
  }
  
  }