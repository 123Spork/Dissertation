package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

     @Serializable
public class GetMapMessage extends AbstractMessage {
         private byte level;
         
  public GetMapMessage(){}       
  public GetMapMessage(byte _lvl) {level=_lvl;}    // empty constructor

  public byte getLevel()
  {
      return level;
  }
  
     }

