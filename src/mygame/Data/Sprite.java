package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Sprite {
    public Node Model; 
    String ModelDir = "/Models/";
    String TextureDir = "/Textures/";
    boolean visible,physical;
    
    
    public Sprite(int id,AssetManager assetManager, String model, String name, float _scale, Vector3f location, String texture)
    {
        Model=(Node)assetManager.loadModel(ModelDir + model);
        Model.setName("sprite"+name);
        Model.scale(_scale);
        Model.setLocalTranslation(location);
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");   
        mat.setTexture("ColorMap", assetManager.loadTexture(TextureDir+texture));
        Model.setMaterial(mat);
    }
    
    public void addModel(Node rootNode)
    {
        rootNode.attachChild(Model);
        visible=true;
    }
    
    public void addPhysics(BulletAppState bullet)
    {
        Model.addControl(new RigidBodyControl(0f));
        bullet.getPhysicsSpace().add(Model.getControl(RigidBodyControl.class));
        physical=true;
    }
    
    public void deleteModel(Node rootNode)
    {
        rootNode.detachChild(Model);
        visible=false;
    }
    
    public void deletePhysics(BulletAppState bullet)
    {
        bullet.getPhysicsSpace().remove(Model.getControl(RigidBodyControl.class));
        physical=false;
    }
    
    public void setPosition(Vector3f _in)
    {
        Model.getControl(RigidBodyControl.class).setPhysicsLocation(_in);
    }
    
    public boolean getVisible()
    {
        return visible;
    }
    public boolean getisPhysical()
    {
        return physical;
    }

    
}
