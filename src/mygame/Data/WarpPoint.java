package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

public class WarpPoint {
    
    public Sprite sprite;
    String spritename;
    Vector3f gotoPosition;

    public WarpPoint(AssetManager assetManager, int _theme, String _spritename, Vector3f Location, Vector3f Rotation, Vector3f _goto)
    {
        this.gotoPosition = _goto;
        spritename=_spritename;
        sprite=new Sprite(-1,assetManager, _theme+"/"+spritename+".mesh.j3o",spritename, 1f, Location,Rotation, _theme+"/"+spritename+".png" );    
    } 
    public String getSpriteName()
    {
        return spritename;
    }
    
    public Vector3f getGoto()
    {
        return gotoPosition;
    }

}
