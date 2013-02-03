package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

public class WarpPoint {
    
    public Sprite sprite;
    int type;
    Vector3f gotoPosition;

    public WarpPoint(AssetManager assetManager, int _theme, int _type, Vector3f Location, Vector3f _goto)
    {
        this.gotoPosition = _goto;
        type=_type;
        sprite=new Sprite(-1,assetManager, _theme+"/warps/"+_type+".mesh.j3o","", 1f, Location, _theme+"/warps/"+_type+".png" );
    } 
    public int getType()
    {
        return type;
    }
    
    public Vector3f getGoto()
    {
        return gotoPosition;
    }

}
