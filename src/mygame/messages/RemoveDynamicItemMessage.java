package mygame.messages;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class RemoveDynamicItemMessage extends AbstractMessage {
  private int ID;
  public RemoveDynamicItemMessage() {}    // empty constructor
  public RemoveDynamicItemMessage(int _id){ID=_id;}
  
  public int getID()
  {
      return ID;
  }
  
  }