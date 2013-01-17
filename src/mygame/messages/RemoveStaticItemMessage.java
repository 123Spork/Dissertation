package mygame.messages;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class RemoveStaticItemMessage extends AbstractMessage {
  private int ID;
  public RemoveStaticItemMessage() {}    // empty constructor
  public RemoveStaticItemMessage(int _id){ID=_id;}
  
  public int getID()
  {
      return ID;
  }
  
  }