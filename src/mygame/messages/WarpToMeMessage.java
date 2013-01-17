package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class WarpToMeMessage extends AbstractMessage {
  private byte b;
  public WarpToMeMessage() {}    // empty constructor
  public WarpToMeMessage(byte _b) {b=_b;} // custom constructor

  public byte getPlayer()
  {
      return b;
  } 

  }