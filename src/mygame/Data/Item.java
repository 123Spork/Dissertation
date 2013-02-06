package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import mygame.Data.Sprite;

public class Item {
    
    String name;
    public Sprite sprite;
    int ID;
    
    int type;

    public Item(int _id, AssetManager assetManager, String _name, String spriteName, int _type, int _theme, Vector3f Location)
    {
        ID=_id;
        name=_name;
        type=_type;
        sprite=new Sprite(-1,assetManager, _theme+"/item"+type+".mesh.j3o",name,spriteName, 1f, Location,new Vector3f(0,0,0), _theme+"/item"+type+".png" );
    } 
    public String getName()
    {
        return name;
    }
    
    public int getType()
    {
        return type;
    }
    
    
    public int getID()
    {
        return ID;
    }

    public void setID(int _id) {
        ID=_id;
    }
}
