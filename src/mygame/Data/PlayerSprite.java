package mygame.Data;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.control.BillboardControl;

public class PlayerSprite implements ActionListener, AnimEventListener  {
    public Node Model; 
    String ModelDir = "/Models/";
    String TextureDir = "/Textures/";
    public CapsuleCollisionShape collisionshape;
    public CharacterControl Control;
    private AnimChannel animationChannel;
    private AnimChannel attackChannel;
    private AnimControl animationControl;  
    private Node textNode = new Node( "LabelNode" );
    private BitmapFont font;
    public enum playerState {STATIONARY, ATTACKING, MINING, RUNNING}
    public playerState Wstate;
    public playerState Sstate; 
    private Vector3f walkDirection,camDir,camLeft;
   private boolean left = false, right = false, up = false, down = false;
   public boolean updateScene=false;
   BitmapText label2;
   private int health,stamina,totalhealth,totalstamina;
       public GhostControl Ghost;
    public Node GhostNode;
    
   
    public PlayerSprite(AssetManager assetManager, float _scale, Vector3f _loc, int theme, String name)
    {
        walkDirection = Vector3f.ZERO;
        camDir=Vector3f.ZERO;
        camLeft=Vector3f.ZERO;
        Control=new CharacterControl(new CapsuleCollisionShape(0.4f,1.45f),1f);
        Model=(Node)assetManager.loadModel(ModelDir + theme+"/player.mesh.j3o");
        Model.scale(_scale);
        Model.setLocalTranslation(_loc);
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");   
        mat.setTexture("ColorMap", assetManager.loadTexture(TextureDir+theme+"/player.png"));
        Model.setMaterial(mat);
        Control.setMaxSlope(0.2f);
        Model.addControl(Control);
        
        animationControl = Model.getControl(AnimControl.class);
        animationControl.addListener(this);
        animationChannel = animationControl.createChannel();
        animationChannel.addFromRootBone("RightThigh");
        animationChannel.addBone("LeftThigh");
        attackChannel = animationControl.createChannel();
        attackChannel.addFromRootBone("RightShoulder");
        attackChannel.addFromRootBone("LeftShoulder");
        attackChannel.addFromRootBone("Spine");
        font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText label = new BitmapText( font, false );
        label.setSize(0.4f);
        label.setText(name);
        float textWidth = label.getLineWidth();
        float textOffset = textWidth / 2;
        label.setBox( new Rectangle(-textOffset,0, textWidth, label.getHeight()) );
        label.setColor( new ColorRGBA( 0, 1, 1, 1 ) );
        label.setQueueBucket( RenderQueue.Bucket.Transparent );
        BillboardControl bc = new BillboardControl();
        bc.setAlignment( BillboardControl.Alignment.Screen );
        label.addControl(bc);
        textNode.setLocalTranslation( 0, label.getHeight()+3, 0 );        
        textNode.attachChild( label );   
        Model.attachChild(textNode);
        Wstate=playerState.STATIONARY;
        Sstate=playerState.STATIONARY;
        totalhealth=150;
        totalstamina=150;
        respawn();
    }
    
     public float getHealthPercent()
    {
        return Float.valueOf((float)health/(float)totalhealth);
    }
    public float getStaminaPercent()
    {
        return Float.valueOf((float)stamina/(float)totalstamina);
    }
    public void useHealth(int in)
    {
        health-=in;
        if(health<0)
        {
            health=0;
        }
    }
    
    public void addGhostControl(BulletAppState bullet)
    {
        Ghost = new GhostControl(
        new BoxCollisionShape(new Vector3f(1.5f,1.5f,1.5f)));  // a box-shaped ghost
        GhostNode = new Node("ghostcontrol");
        GhostNode.addControl(Ghost);                         // the ghost follows this node
        Model.attachChild(GhostNode);
        bullet.getPhysicsSpace().add(Ghost);
    }
    
    
    
    public void useStamina(int in)
    {
        stamina-=in;
        if(stamina<0)
        {
            stamina=0;
        }
    }
     public void addHealth(int in)
    {
        health+=in;
        if(health>totalhealth)
        {
            health=totalhealth;
        }
    }
    public void addStamina(int in)
    {
        stamina+=in;
        if(stamina>totalstamina)
        {
            stamina=totalstamina;
        }
    }
    
    
    
    public void respawn()
    {
        health=totalhealth;
        stamina=totalstamina;
    }  
        
     public void setLocation(Vector3f _loc)
    {
          Control.warp(_loc);
    }
    
    public void setLocation(float x, float y, float z)
    {
          Control.warp(new Vector3f(x,y,z));
    }
    
        public void setWalkDirection(Vector3f _walk)
    {
        walkDirection=_walk;
    }
    
    public void setWalkDirection(float x, float y, float z)
    {
        walkDirection = new Vector3f(x,y,z);
    }
    
    public void resetTheme(int theme, AssetManager assets, Node rootNode)
    {
         rootNode.detachChild(Model);
         Model.detachChild(textNode);
         Material mat = new Material(assets,"Common/MatDefs/Misc/Unshaded.j3md");
         
         mat.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Back);
         
         mat.setTexture("ColorMap", assets.loadTexture(TextureDir+theme+"/player.png"));
         Model.setMaterial(mat);
         Model.attachChild(textNode);
         rootNode.attachChild(Model);
    }

    public Vector3f getLocation()
    {
        return Model.getLocalTranslation();
    }
    
    public Vector3f getPhysicsLocation()
    {
        return Control.getPhysicsLocation();
    }
    
    public Vector3f getWalkDirection()
    {
        return walkDirection;
    }
    
     public void initControl(InputManager inputManager)
    {
        inputManager.addMapping("CharLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("CharRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("CharForward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("CharBackward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping("CharMine", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "CharLeft", "CharRight");
        inputManager.addListener(this, "CharForward", "CharBackward");
        inputManager.addListener(this, "CharJump", "CharMine");
    }
    
    
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
}
 
public void onAnimChange(AnimControl control, AnimChannel channel, String animName) { }
  
      

@Override
public void onAction(String binding, boolean value, float tpf) {
  if (binding.equals("CharLeft"))
  {
      if (value)
      {
          left = true;
      }
      else
      {
          left = false;
      }
     updateScene=true;
  }
  else if (binding.equals("CharRight"))
  {
      if (value)
      {
          right = true;
      }
      else
      {
          right = false;
      }
           updateScene=true;
  } 
  else if (binding.equals("CharForward"))
  {
      if (value)
      {
          up = true;
      }
      else
      {
          up = false;
      }
             updateScene=true;
  } 
  else if (binding.equals("CharBackward"))
  {
      if (value)
      {
          down = true;
      }
      else
      {
          down = false;
      }
             updateScene=true;
  }
  if (binding.equals("CharMine") && value==true)
  {  
      if(stamina>0)
      {
        Sstate = playerState.MINING;
      } 
      updateScene=true;
  }
  else if (binding.equals("CharMine") && value==false)
  {  
      Sstate = playerState.STATIONARY;
      updateScene=true;
  }
}


public void InternalUpdate(Camera cam, float tpf) {
  if(camDir.x!=cam.getDirection().clone().multLocal(0.25f).x&&camDir.z!=cam.getDirection().clone().multLocal(0.25f).z)
  {
      camDir = cam.getDirection().clone().multLocal(0.25f);
      camDir.y = 0;
      updateScene=true;
  }
  if(camLeft.x!=cam.getLeft().clone().multLocal(0.25f).x&&camLeft.z!=cam.getLeft().clone().multLocal(0.25f).z)
  {
      camLeft = cam.getLeft().clone().multLocal(0.25f);   
      camLeft.y = 0;
      updateScene=true;
  }
  walkDirection.set(0, 0, 0);
  if (left)  {  walkDirection.addLocal(camLeft.mult(0.4f)); }
  if (right) {  walkDirection.addLocal(camLeft.negate().mult(0.4f)); }
  if (up)    {  walkDirection.addLocal(camDir.mult(0.4f)); } 
  if (down)  {  walkDirection.addLocal(camDir.negate().mult(0.4f));  }
  Update(tpf);
}

public void Update(float tpf)
{
  if (walkDirection.length() == 0) {
      Wstate = playerState.STATIONARY;
      if(Sstate!=playerState.ATTACKING && Sstate!=playerState.MINING)
      {
          Sstate = playerState.STATIONARY;
      }
  }
  else
  {
      Wstate = playerState.RUNNING;
      if(Sstate!=playerState.ATTACKING)
      {
          Sstate = playerState.RUNNING;
      }
  }
  if(Wstate == playerState.STATIONARY)
  {
      if (!"Stand".equals(animationChannel.getAnimationName())) 
      {
      animationChannel.setAnim("Stand", 0.3f);
      }
  }
  else if(Wstate == playerState.RUNNING)
  {
     Control.setViewDirection(walkDirection);
      if(!"Run".equals(animationChannel.getAnimationName())) {
        animationChannel.setAnim("Run", 0.7f);
      }
  }
  
  if(Sstate==playerState.ATTACKING)
  {
     if(!"Shoot".equals(attackChannel.getAnimationName()))
     {
          attackChannel.setAnim("Shoot");
     }
  }
  else if(Sstate==playerState.MINING)
  {
     if(stamina>0)
     {
         if(!"Mine".equals(attackChannel.getAnimationName()))
         {
            attackChannel.setAnim("Mine",0.2f);
         }
     }
     else
     {
         attackChannel.setAnim("Stand",0.7f);
     }
  }
  else if(Sstate==playerState.RUNNING)
  {
     if(!"Run".equals(attackChannel.getAnimationName()))
     {
          attackChannel.setAnim("Run",0.7f);
     }
  }
  else if(Sstate==playerState.STATIONARY)
  {
     if(!"Stand".equals(attackChannel.getAnimationName()))
     {
          attackChannel.setAnim("Stand",0.7f);
     }
  }
  Control.setWalkDirection(walkDirection); // THIS IS WHERE THE WALKING HAPPENS 
}

    public void addModel(BulletAppState bullet, Node rootNode)
    {
        bullet.getPhysicsSpace().add(Control);
        rootNode.attachChild(Model);
    }
    
    public void deleteModel(BulletAppState bullet, Node rootNode)
    {
        bullet.getPhysicsSpace().remove(Control);
        rootNode.detachChild(Model);
    }
    
    
}
