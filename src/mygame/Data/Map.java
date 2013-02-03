package mygame.Data;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;

public class Map {
    
    public Queue<Sprite> sprites;
    public Queue<String> deletecache;
    public Queue<WarpPoint> warppoints;
    public Queue<Item> staticitems;
    public Queue<Item> dynamicitems;
    public Node terrain;
    public Node skybox;

    public Map(int _staticitems, int _dynamicitems, int _sprites, int _warps)
    {
        staticitems=new Queue(Item.class,_staticitems);
        dynamicitems = new Queue(Item.class,40);
        sprites = new Queue(Sprite.class,_sprites);
        warppoints = new Queue(WarpPoint.class,_warps);
        deletecache = new Queue(String.class,(_staticitems+_dynamicitems+_sprites));
    }
    
    public void init(AssetManager assets, BulletAppState bullet, Node root, int level, int theme)
    {
        terrain=(Node)assets.loadModel("/Scenes/"+level+"/terrain"+theme+".j3o");
        bullet.getPhysicsSpace().addAll(terrain);
        root.attachChild(terrain);
        
        skybox=(Node)assets.loadModel("/Scenes/"+theme+"/Sky.j3o");
        bullet.getPhysicsSpace().addAll(skybox);
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
    
    public void ChangeTheme(int theme, int type, Node root, AssetManager assets)
    {
        root.detachChild(terrain);
        terrain=(Node)assets.loadModel("/Scenes/"+type+"/terrain"+theme+".j3o");
        root.attachChild(terrain);
        
        root.detachChild(skybox);
        skybox=(Node)assets.loadModel("/Scenes/"+theme+"/Sky.j3o");
        root.attachChild(skybox);
        skybox.setShadowMode(RenderQueue.ShadowMode.Off);
    }
    
}
