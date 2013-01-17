package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;


     @Serializable
public class CreateUserMessage extends AbstractMessage {
  private String username, password, email;
  public CreateUserMessage() {}    // empty constructor
  public CreateUserMessage(String usr, String pas, String ema) {email = ema; username=usr; password=pas;  } // custom constructor

  public String getUsername()
  {
      return username;
  }
  public String getPassword()
  {
      return password;
  }
   public String getEmail()
   {
      return email;
  }
  
  
  }