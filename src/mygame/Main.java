package mygame;

import com.jme3.app.SettingsDialog;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.NetworkClient;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import mygame.Data.Item;
import mygame.Data.PlayerData;
import mygame.Data.Queue;
import mygame.Data.Sprite;
import mygame.Screens.*;
import mygame.messages.*;

public class Main extends SimpleApplication{

    public Client client;
    public PlayerData player, updatePlayer;
    public int playerDelete;
    public Node deleteNode;
    public Queue<PlayerData> players;
    MessageClasses messages;
    String chatMessage;
    public Nifty nifty;
    public Item tempitem;
            public Vector3f tempItemPos;
        public Vector3f updateLocation;
    public int updatePlayeri;
 
    public BulletAppState bulletAppState;
    private PssmShadowRenderer pssmRenderer;
    public String DeleteName;
    public Item UpdateItem;
    int findItem;
    Sprite UpdateSprite;
    public static enum State {BEGIN,INITIALIZED, MAINMENU, LOBBY, PLAYING, PAUSED}
    public static State state;
    public startScreen startscreen;
    public lobbyScreen lobbyscreen;
    public newuserScreen newuserscreen;
    public playScreen playscreen;
    boolean init=false;
    public ClientListener Listener;
    public ChaseCamera chaseCam; 
    public int PlayerSpacial;
    public int[][] Inventory;

    public static void main(String[] args) {
        System.setSecurityManager(null);
        Main app = new Main();
        AppSettings settings = new AppSettings(false);
        settings.setResolution(640,480);
        settings.setFrameRate(24);   
        app.setSettings(settings);
        app.start();
    } 
    
    @Override
    public void simpleInitApp() {      
        if(state != State.BEGIN)
        {
            state=State.BEGIN;
        }
        this.getContext().getSettings().setResolution(640,480);
        this.getContext().getSettings().setFrameRate(24);   
        setDisplayStatView(false);
        setDisplayFps(true);
        setPauseOnLostFocus(false);
        this.getCamera().setRotation(Quaternion.IDENTITY);
      
        System.setSecurityManager(null); 
        flyCam.setEnabled(false); 
        chaseCam = new ChaseCamera(cam, inputManager);
        chaseCam.setDefaultHorizontalRotation(-FastMath.DEG_TO_RAD * 90);
        players = new Queue(PlayerData.class,7);
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        pssmRenderer = new PssmShadowRenderer(assetManager, 1024, 3);
        pssmRenderer.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal()); // light direction
        viewPort.addProcessor(pssmRenderer); 
        rootNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
            
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        
        startscreen = new startScreen("",this,inputManager);
        newuserscreen = new newuserScreen("",this);   
        playscreen = new playScreen(this,assetManager,rootNode,bulletAppState,inputManager);
        lobbyscreen = new lobbyScreen("",this);
        if(nifty==null)
        {  
            NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,inputManager,audioRenderer,guiViewPort);
            guiViewPort.addProcessor(niftyDisplay); 
            nifty = niftyDisplay.getNifty();  
            nifty.fromXml("Interface/Nifty/HelloJME.xml","start",startscreen); 
            nifty.fromXml("Interface/Nifty/HelloJME.xml","newuser",newuserscreen);
            nifty.fromXml("Interface/Nifty/HelloJME.xml","play",playscreen);  
            nifty.fromXml("Interface/Nifty/HelloJME.xml","lobby",lobbyscreen);
        }
        startscreen.bind(nifty, nifty.getScreen("start"));
        newuserscreen.bind(nifty, nifty.getScreen("newuser"));
        playscreen.bind(nifty, nifty.getScreen("play"));
        lobbyscreen.bind(nifty, nifty.getScreen("lobby"));
        
        
        nifty.gotoScreen("start");
        state=State.INITIALIZED;
    }  
    
    public void setClient()
    {
    client=null; 
    Listener=null;
        try 
        {
            client = Network.connectToServer("127.0.01",6143);
            client.start();
            Listener = new ClientListener(this,rootNode,nifty,client); 
            startscreen.printservermessage("Server Online");
        } 
        catch (Exception ex)
        {
              startscreen.printservermessage("Server Offline");
        }               
  }
    
    
    
   @Override
    public void simpleUpdate(float tpf) {
       switch(state){
           //////////////////////////
           case INITIALIZED:
               if(client==null)
               {
                    setClient();
               }
              break;
          ////////////////////////////
          case LOBBY:
            lobbyscreen.update(tpf);
            break;
          ////////////////////////////
          case PLAYING:
            playscreen.update(tpf);
            break;
       }
    }
   
  @Override
  public void restart()
  {
       nifty.setIgnoreKeyboardEvents(false);
       client.close();
       client=null;
       Listener=null;
       state=State.BEGIN;
       rootNode.detachAllChildren();
       stateManager.detach(bulletAppState);
       viewPort.clearProcessors();
       player=null;
       simpleInitApp();
  }
   
  @Override
  public void destroy() {
     if(client!=null && client.isConnected()){
         client.close();
     try {Thread.sleep(3000);} catch (InterruptedException ex) {}
     }
      super.destroy();
  }
}
    
    
    
    
    
    

