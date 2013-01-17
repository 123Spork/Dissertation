package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class LoginMessage extends AbstractMessage {
  private String username, password;
  public LoginMessage() {}    // empty constructor
  public LoginMessage(String usr, String pas) {username=usr; password=pas;} // custom constructor

  public String getUsername()
  {
      return username;
  }
  public String getPassword()
  {
      return password;
  }
  
  }