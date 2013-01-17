package mygame.messages;
import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class LobbyTimerMessage extends AbstractMessage {
  private int Time;
  public LobbyTimerMessage() {}    // empty constructor
  public LobbyTimerMessage(int _time){Time=_time;}
  
  public int getTime()
  {
      return Time;
  }
  
  }