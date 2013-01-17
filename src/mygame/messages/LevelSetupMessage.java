package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

     @Serializable
public class LevelSetupMessage extends AbstractMessage {
  private int setters[];
  public LevelSetupMessage() {}    // empty constructor
  public LevelSetupMessage(int _level, int _theme) {setters=new int[2]; setters[0]=_level; setters[1]=_theme;} // custom constructor

  public int getLevel()
  {
      return setters[0];
  }
  public int getTheme()
  {
      return setters[1];
  }
  
  
  }