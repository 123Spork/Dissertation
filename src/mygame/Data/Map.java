package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import mygame.Main;

public class Map {
    
    public Queue<Sprite> sprites;
    public Queue<String> deletecache;
    public Queue<WarpPoint> warppoints;
    public Queue<Item> staticitems;
    public Queue<Item> dynamicitems;
    public Queue<ScriptedObject> scriptedobjects;
    public Node terrain;
    public Node skybox;

    public Map(int _staticitems, int _dynamicitems, int _sprites, int _warps, int scripted)
    {
        staticitems=new Queue(Item.class,_staticitems);
        dynamicitems = new Queue(Item.class,40);
        sprites = new Queue(Sprite.class,_sprites);
        scriptedobjects = new Queue(ScriptedObject.class, scripted);
        warppoints = new Queue(WarpPoint.class,_warps);
        deletecache = new Queue(String.class,(_staticitems+_dynamicitems+_sprites));
    }
    
    public void init(AssetManager assets, BulletAppState bullet, Node root, int level, int theme)
    {
        
   
        terrain=(Node)assets.loadModel("/Scenes/"+level+"/terrain"+theme+".j3o");
        bullet.getPhysicsSpace().addAll(terrain);
        root.attachChild(terrain);
        skybox=(Node)assets.loadModel("/Scenes/"+theme+"/Sky.j3o");     
        root.attachChild(skybox);
        skybox.setShadowMode(RenderQueue.ShadowMode.Off);
   }
    
    public void addStaticItem(Item _in, Node root)
    {
        staticitems.Enqueue(_in);
        _in.sprite.addModel(root);
    }
    
        
    public void addDynamicItem(Item _in, Node root)
    {
        dynamicitems.Enqueue(_in);
        _in.sprite.addModel(root);
    }
    
    //public void addSprite(Sprite _in, BulletAppState bullet, Node root)
   // {
   ///     sprites.Enqueue(_in);
    //    _in.addModel(root);
   //     _in.addPhysics(bullet);
    //}
    
    public void ChangeTheme(Main app, int theme, int type, Node root, BulletAppState bullet, AssetManager assets)
    {
        root.detachChild(terrain);
        terrain=(Node)assets.loadModel("/Scenes/"+type+"/terrain"+theme+".j3o");
        root.attachChild(terrain);
        
        root.detachChild(skybox);
        skybox=(Node)assets.loadModel("/Scenes/"+theme+"/Sky.j3o");
        root.attachChild(skybox);
        skybox.setShadowMode(RenderQueue.ShadowMode.Off);
        
        for(int i=0;i<sprites.size;i++)
        {
            if(sprites.buffer[i]!=null)
            {
                sprites.buffer[i].resetTheme(app, assets, root, bullet, theme);
            }
        }
        for(int i=0;i<warppoints.size;i++)
        {
            if(warppoints.buffer[i]!=null)
            {
                warppoints.buffer[i].sprite.resetTheme(app, assets, root, bullet, theme);
            }
        }
        for(int i=0;i<staticitems.size;i++)
        {
            if(staticitems.buffer[i]!=null)
            {
                staticitems.buffer[i].sprite.resetTheme(app, assets, root, bullet, theme);
            }
        }
       for(int i=0;i<dynamicitems.size;i++)
        {
            if(dynamicitems.buffer[i]!=null)
            {
                dynamicitems.buffer[i].sprite.resetTheme(app, assets, root, bullet, theme);
            }
        }
       for(int i=0;i<scriptedobjects.size;i++)
        {
            if(scriptedobjects.buffer[i]!=null)
            {
                scriptedobjects.buffer[i].sprite.resetTheme(app, assets, root, bullet, theme);
            }
        }        
    }
    
}
