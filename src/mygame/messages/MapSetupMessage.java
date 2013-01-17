package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

     @Serializable
public class MapSetupMessage extends AbstractMessage {
  private int setters[];
  public MapSetupMessage() {}    // empty constructor
  public MapSetupMessage(int _staticitems, int _dynamicitems, int _sprites) {setters=new int[3]; setters[0]=_staticitems; setters[2]=_sprites; setters[1]=_dynamicitems;} // custom constructor

  public int getStaticItems()
  {
      return setters[0];
  }
  public int getDynamicItems()
  {
      return setters[0];
  }
    public int getSprites()
  {
      return setters[1];
  }
  
  
  }