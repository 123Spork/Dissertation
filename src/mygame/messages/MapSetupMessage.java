package mygame.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

     @Serializable
public class MapSetupMessage extends AbstractMessage {
  private int setters[];
  public MapSetupMessage() {}    // empty constructor
  public MapSetupMessage(int _staticitems, int _dynamicitems, int _sprites, int _warps, int _scripts) {setters=new int[5]; setters[0]=_staticitems; setters[2]=_sprites; setters[1]=_dynamicitems; setters[3]=_warps; setters[4]=_scripts;} // custom constructor

  public int getStaticItems()
  {
      return setters[0];
  }
  public int getDynamicItems()
  {
      return setters[1];
  }
    public int getSprites()
  {
      return setters[2];
  }
    
  public int getWarps()
  {
      return setters[3];
  }
  public int getScripts()
  {
      return setters[4];
  }
  
  
  }