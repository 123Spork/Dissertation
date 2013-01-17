package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class LoggedOutMessage extends AbstractMessage {
  private byte id;
  public LoggedOutMessage() {}    // empty constructor
  public LoggedOutMessage(byte _id) {id=_id;} // custom constructor

  public byte getID()
  {
      return id;
  }

  }