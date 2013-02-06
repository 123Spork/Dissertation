package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import java.util.concurrent.Callable;
import mygame.Main;

public class Sprite {
    public Node Model; 
    String ModelDir = "/Models/";
    String TextureDir = "/Textures/";
    String spriteName;
    String Name;
    float Scale;
    boolean visible,physical;
    Vector3f rotation;
    public GhostControl Ghost;
    public Node GhostNode;
    Main app;
    
    
    public Sprite(int id,AssetManager assetManager, String model, String name, float _scale, Vector3f location, Vector3f rot, String texture)
    {
        Scale=_scale;
        rotation = rot;
        spriteName = name;
        Name=spriteName;
        Model=(Node)assetManager.loadModel(ModelDir+model);
        Model.setName("sprite"+spriteName);
        Model.scale(Scale);
        Model.setLocalTranslation(location);
       
        if(rotation.x!=0||rotation.y!=0||rotation.z!=0)
        {
            Quaternion test = new Quaternion();
            Model.setLocalRotation(test.fromAngles((float)Math.toRadians(rotation.x),(float)Math.toRadians(rotation.y),(float)Math.toRadians(rotation.z)));
        }
        
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");    
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Back);
        Texture tex = assetManager.loadTexture(TextureDir+texture);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", tex);
        Model.setMaterial(mat);
    }
    
     public Sprite(int id,AssetManager assetManager, String model, String name, String _spriteName, float _scale, Vector3f location, Vector3f rot, String texture)
    {
        Scale=_scale;
        rotation = rot;
        spriteName = _spriteName;
        Name = name;
        
        Model=(Node)assetManager.loadModel(ModelDir+model);
        Model.setName(Name);
        Model.scale(Scale);
        Model.setLocalTranslation(location);
       
        if(rotation.x!=0||rotation.y!=0||rotation.z!=0)
        {
            Quaternion test = new Quaternion();
            Model.setLocalRotation(test.fromAngles((float)Math.toRadians(rotation.x),(float)Math.toRadians(rotation.y),(float)Math.toRadians(rotation.z)));
        }
        
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");    
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Back);
        Texture tex = assetManager.loadTexture(TextureDir+texture);
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", tex);
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
    
    public void resetTheme(Main _app, AssetManager assetManager, Node rootNode, BulletAppState bullet, int theme)
    {
        this.app=_app;
       
        app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {   
                  
                       Vector3f position = getLocation();
        if(visible)
        {
            if(GhostNode!=null)
            {
              Model.detachChild(GhostNode);
            }
            deleteModel(app.getRootNode());
            visible=true;
        }
        if(physical)
        {
            deletePhysics(app.bulletAppState);
            physical=true;
        }
        Model=null;
        Model=(Node)app.getAssetManager().loadModel(ModelDir+app.playscreen.theme+"/"+spriteName+".mesh.j3o");
    
        
        if(Name.equals(spriteName))
        {
            Model.setName("sprite"+spriteName);
        }
        else
        {
            Model.setName(Name);
        }
        Model.scale(Scale);
        Model.setLocalTranslation(position);
        if(rotation.x!=0||rotation.y!=0||rotation.z!=0)
        {
            Quaternion test = new Quaternion();
            Model.setLocalRotation(test.fromAngles((float)Math.toRadians(rotation.x),(float)Math.toRadians(rotation.y),(float)Math.toRadians(rotation.z)));
        }
        Material mat = new Material(app.getAssetManager(),"Common/MatDefs/Misc/Unshaded.j3md");   
        mat.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Back);
        Texture tex = app.getAssetManager().loadTexture(TextureDir+app.playscreen.theme+"/"+spriteName+".png");
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTexture("ColorMap", tex);
        Model.setMaterial(mat);
           
        if(visible)
        {
            addModel(app.getRootNode());
           if(GhostNode!=null)
            {
                Model.attachChild(GhostNode);
            }
        }
        if(physical)
        {
            addPhysics(app.bulletAppState); 
        }
        return null;
             
          }});  
    }
    
    public void addGhostControl(BulletAppState bullet, Vector3f size)
    {
        Ghost = new GhostControl(
        new BoxCollisionShape(size));  // a box-shaped ghost
        GhostNode = new Node("ghostcontrol");
        GhostNode.addControl(Ghost);                         // the ghost follows this node
        Model.attachChild(GhostNode);
        bullet.getPhysicsSpace().add(Ghost);
    }
    
    public void removeGhostControl(BulletAppState bullet)
    {
        Model.detachChild(GhostNode);
        bullet.getPhysicsSpace().remove(Ghost);
    }
    
    public void deleteModel(Node rootNode)
    {
        if(visible)
        {
            rootNode.detachChild(Model);
            visible=false;
        }
    }
    
    public void deletePhysics(BulletAppState bullet)
    {
        if(physical)
        {
            bullet.getPhysicsSpace().remove(Model.getControl(RigidBodyControl.class));
            physical=false;
        }
    }
    
    public Vector3f getLocation()
    {
        return Model.getLocalTranslation();
    }
    
    public void setPosition(Vector3f _in)
    {
        Model.getControl(RigidBodyControl.class).setPhysicsLocation(_in);
    }
       
    public void setRotation(float x, float y, float z)
    {
        Quaternion rot = new Quaternion();
        rot = rot.fromAngles(x, y, z);
        Model.getControl(RigidBodyControl.class).setPhysicsRotation(Model.getControl(RigidBodyControl.class).getPhysicsRotation().mult(rot));
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
