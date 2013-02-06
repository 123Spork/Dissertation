package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

public class ScriptedObject {
    
    public Sprite sprite;
    String spritename;
    int type;

    public ScriptedObject(AssetManager assetManager, int _theme, int _type, String _spritename, Vector3f Location, Vector3f Rotation)
    {
        spritename=_spritename;
        type=_type;
        sprite=new Sprite(-1,assetManager, _theme+"/"+spritename+".mesh.j3o",spritename, 1f, Location,Rotation, _theme+"/"+spritename+".png" );    
    } 
    public String getSpriteName()
    {
        return spritename;
    }
    public int getType()
    {
        return type;
    }
}
