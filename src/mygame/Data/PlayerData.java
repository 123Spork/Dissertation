package mygame.Data;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;


public class PlayerData {
    
   public PlayerSprite sprite;
   private String Name;
   private int ID;
    public PlayerData(){}
    
    public PlayerData(String _name, int _id, AssetManager assets,int theme,Vector3f local)
    {
        Name=_name;
            sprite=new PlayerSprite(assets,0.5f,local,theme,Name);
        ID=_id;

    }
    
  
    
    public void setName(String _name)
    {
        Name=_name;
    }
    
    public String getName()
    {
        return Name;
    }

    public int getID() {
        return ID;
    }
    

    
    
}
