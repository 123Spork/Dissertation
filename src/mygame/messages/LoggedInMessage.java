package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class LoggedInMessage extends AbstractMessage {
  private byte id;
  private String Name;
  public LoggedInMessage() {}    // empty constructor
  public LoggedInMessage(byte _id, String _name) {id=_id; Name=_name;} // custom constructor

  public byte getID()
  {
      return id;
  }
  public String getName()
  {
      return Name;
  }

  }