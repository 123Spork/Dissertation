package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class ChatMessage extends AbstractMessage {
  private String message;
  public ChatMessage() {}    // empty constructor
  public ChatMessage(String _message) {message=_message;} // custom constructor

  public String getMessage()
  {
      return message;
  } 
  
  }