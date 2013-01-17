package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import mygame.Data.Sprite;

public class Item {
    
    String name;
    public Sprite sprite;
    int type;
    int ID;

    public Item(int _id, AssetManager assetManager, String _name, int _theme, int _type, Vector3f Location)
    {
        ID=_id;
        type=_type;
        name=_name;
        sprite=new Sprite(-1,assetManager, _theme+"/items/item"+_type+".mesh.j3o",""+ID, 1f, Location, _theme+"/items/item"+_type+".png" );
    } 
    public int getType()
    {
        return type;
    }
    public String getName()
    {
        return name;
    }
    public int getID()
    {
        return ID;
    }

    public void setID(int _id) {
        ID=_id;
    }
}
