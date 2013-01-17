package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


@Serializable
public class ByteMessage extends AbstractMessage {
  private byte text;       // custom message data
  public ByteMessage() {}    // empty constructor
  public ByteMessage(byte _text) {text = _text; } // custom constructor    
  public byte getNum()
  {
      return text;
  }
  
  }
