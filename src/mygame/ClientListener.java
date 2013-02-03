package mygame;

import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import java.util.concurrent.Callable;
import mygame.Data.Item;
import mygame.Data.Map;
import mygame.Data.PlayerData;
import mygame.Data.Sprite;
import mygame.Data.WarpPoint;
import mygame.Main.State;
import mygame.Screens.playScreen;
import mygame.messages.*;
     
     public class ClientListener implements MessageListener<Client> {
         
   Main app;
   Node rootNode;
   Nifty nifty;
   Client client;
   MessageClasses messages;
   Item itemadder;
   Sprite spriteadder;

     
   
   public ClientListener(Main _app, Node _root, Nifty _nifty, Client _in)
   {
       this.app = _app;
       this.rootNode=_root;
       this.nifty=_nifty;
       this.client = _in;
       messages=new MessageClasses();
       messages.Serialize();
       client.addMessageListener(this, EndServerMsg.class, ByteMessage.class, LevelSetupMessage.class, AddWarpMessage.class, ToLobbyMessage.class, LobbyTimerMessage.class, AddSpriteMessage.class, RemoveSpriteMessage.class, LoggedInMessage.class, WarpToMeMessage.class, LoggedOutMessage.class, GetMapMessage.class, LoginMessage.class, MapSetupMessage.class, AddStaticItemMessage.class, RemoveStaticItemMessage.class, AddDynamicItemMessage.class, RemoveDynamicItemMessage.class, DropItemMessage.class, CreateUserMessage.class, ChatMessage.class, UpdateMessage.class, UpdateMessageNID.class);
   }     
  public void messageReceived(Client source, Message message) {
    if (message instanceof ByteMessage) {
       switch(((ByteMessage)message).getNum())
       {
           case (byte)0: app.startscreen.printmessage("Invalid password."); break;
           case (byte)1: app.startscreen.printmessage("That user doesn't exist."); break;
           case (byte)2: app.startscreen.printmessage("User already logged in."); break;
           case (byte)3: app.startscreen.printmessage("No room on server."); break;
           case (byte)4: app.newuserscreen.printmessage("Username already exists."); break;
           case (byte)5: app.newuserscreen.printmessage("Email already associated with a user."); break;
           case (byte)6: app.newuserscreen.printmessage("User created."); break;
       }              
    }
    
    
    else if (message instanceof ChatMessage) {
        app.playscreen.updatechat(((ChatMessage)message).getMessage());
      }
    
    else if (message instanceof MapSetupMessage)
    {
        app.lobbyscreen.countdown=true;
        app.playscreen.map = new Map(((MapSetupMessage)message).getStaticItems(),((MapSetupMessage)message).getDynamicItems(),((MapSetupMessage)message).getSprites(),((MapSetupMessage)message).getWarps());    
         app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {    
              app.playscreen.map.init(app.playscreen.assetManager, app.bulletAppState, rootNode, app.playscreen.level, app.playscreen.theme);            
               return null;
          } }); 
    }       

    else if(message instanceof AddStaticItemMessage)
    {
        if(app.playscreen!=null && app.playscreen.map!=null)
        {
          app.playscreen.map.staticitems.Enqueue(new Item(((AddStaticItemMessage)message).getID(),app.getAssetManager(),"item"+((AddStaticItemMessage)message).getID(),app.playscreen.theme,((AddStaticItemMessage)message).getType(),((AddStaticItemMessage)message).getPosition()));
 
           
          app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {   
                   for(int i=0;i<app.playscreen.map.staticitems.size;i++)
                   {
                       if(app.playscreen.map.staticitems.buffer[i]!=null){
                       if(!rootNode.hasChild(app.playscreen.map.staticitems.buffer[i].sprite.Model) && !app.playscreen.map.staticitems.buffer[i].sprite.getVisible())
                       {
                         app.playscreen.map.staticitems.buffer[i].sprite.addModel(rootNode);         
                       }
                       }
                       }
                  return null;
          }});
         }
     }
    
     else if(message instanceof AddDynamicItemMessage)
    {
        if(app.playscreen!=null)
        {
          app.playscreen.map.dynamicitems.Enqueue(new Item(((AddDynamicItemMessage)message).getID(),app.getAssetManager(),"item"+((AddDynamicItemMessage)message).getID(),app.playscreen.theme,((AddDynamicItemMessage)message).getType(),((AddDynamicItemMessage)message).getPosition())); 
           app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {   
                   for(int i=0;i<app.playscreen.map.dynamicitems.size;i++)
                   {
                       if(app.playscreen.map.dynamicitems.buffer[i]!=null){
                       if(!rootNode.hasChild(app.playscreen.map.dynamicitems.buffer[i].sprite.Model) && !app.playscreen.map.dynamicitems.buffer[i].sprite.getVisible())
                       {
                         app.playscreen.map.dynamicitems.buffer[i].sprite.addModel(rootNode);         
                       }
                       }
                   }
                      return null;
          }});
         }
     }
     
     
    else if(message instanceof AddSpriteMessage)
    {
        if(app.playscreen!=null)
        {
          app.playscreen.map.sprites.Enqueue(new Sprite(((AddSpriteMessage)message).getID(),app.getAssetManager(),((AddSpriteMessage)message).getModel()+".mesh.j3o","sprite"+((AddSpriteMessage)message).getID(),1f,((AddSpriteMessage)message).getPosition(),((AddSpriteMessage)message).getModel()+".png"));

          app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {   
                   for(int i=0;i<app.playscreen.map.sprites.size;i++)
                   {
                       if(app.playscreen.map.sprites.buffer[i]!=null){
                       if(!rootNode.hasChild(app.playscreen.map.sprites.buffer[i].Model) && !app.playscreen.map.sprites.buffer[i].getVisible())
                       {
                         app.playscreen.map.sprites.buffer[i].addModel(rootNode);
                         app.playscreen.map.sprites.buffer[i].addPhysics(app.bulletAppState);
                       }
                       }
                       }
                  return null;
          }});
         }
     }
    
     else if(message instanceof AddWarpMessage)
    {
        if(app.playscreen!=null)
        {
          app.playscreen.map.warppoints.Enqueue(new WarpPoint(app.getAssetManager(),app.playscreen.theme,((AddWarpMessage)message).getType(),((AddWarpMessage)message).getPosition(),((AddWarpMessage)message).getGoto()));

          app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {   
                   for(int i=0;i<app.playscreen.map.warppoints.size;i++)
                   {
                       if(app.playscreen.map.warppoints.buffer[i]!=null){
                       if(!rootNode.hasChild(app.playscreen.map.warppoints.buffer[i].sprite.Model) && !app.playscreen.map.warppoints.buffer[i].sprite.getVisible())
                       {
                         app.playscreen.map.warppoints.buffer[i].sprite.addModel(rootNode);
                         app.playscreen.map.warppoints.buffer[i].sprite.addPhysics(app.bulletAppState);
                       }
                       }
                   }
                  return null;
          }});
         }
     }
     
     
    
        else if(message instanceof RemoveStaticItemMessage)
    {
        app.playscreen.map.deletecache.Enqueue("item"+((RemoveStaticItemMessage)message).getID()); 
        
         app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {   
                   for(int i=0;i<app.playscreen.map.deletecache.size;i++)
                   {
                       if(app.playscreen.map.deletecache.buffer[i]!=null)
                         {
                             if(app.playscreen.map.staticitems.FindItem(Integer.valueOf(app.playscreen.map.deletecache.buffer[i].substring(4)))>-1)
                             {
                                     app.playscreen.map.staticitems.buffer[app.playscreen.map.staticitems.FindItem(Integer.valueOf(Integer.valueOf(app.playscreen.map.deletecache.buffer[i].substring(4))))].sprite.deleteModel(rootNode);
                                     app.playscreen.map.staticitems.delete(app.playscreen.map.staticitems.FindItem(Integer.valueOf(app.playscreen.map.deletecache.buffer[i].substring(4))));
                                     app.playscreen.map.deletecache.delete(i);
                             }
                         }
                   }
                      return null;
                  }});
    }
    
           
    else if(message instanceof RemoveDynamicItemMessage)
    {
        app.playscreen.map.deletecache.Enqueue("item"+((RemoveDynamicItemMessage)message).getID()); 
        app.enqueue(new Callable<Void>() {
                  public Void call() throws Exception {   
                   for(int i=0;i<app.playscreen.map.deletecache.size;i++)
                   {
                         if(app.playscreen.map.deletecache.buffer[i]!=null)
                         {
                             if(app.playscreen.map.dynamicitems.FindItem(Integer.valueOf(app.playscreen.map.deletecache.buffer[i].substring(4)))>-1)
                             {
                                     app.playscreen.map.dynamicitems.buffer[app.playscreen.map.dynamicitems.FindItem(Integer.valueOf(Integer.valueOf(app.playscreen.map.deletecache.buffer[i].substring(4))))].sprite.deleteModel(rootNode);
                                     app.playscreen.map.dynamicitems.delete(app.playscreen.map.dynamicitems.FindItem(Integer.valueOf(app.playscreen.map.deletecache.buffer[i].substring(4))));
                                     app.playscreen.map.deletecache.delete(i);
                             }
                         }
                   }
                      return null;
                  }});
    }
    
    else if(message instanceof LoggedInMessage)
    {
            byte b = ((LoggedInMessage)message).getID();
            String name =((LoggedInMessage)message).getName();
            
            if(app.players.FindPlayer(name)<0)
            {
            if(name.equals(app.startscreen.getuser()))
            {            
                if(app.player==null)
                {
            app.player = new PlayerData(app.startscreen.getuser(),b,app.playscreen.assetManager,app.playscreen.theme,new Vector3f(0,1,0));
            app.player.sprite.initControl(app.playscreen.inputManager);
            app.startscreen.printmessage("Logging In");
            nifty.gotoScreen("lobby");  
            Main.state=State.LOBBY; 
            }
            }
            else
            {
                if(app.player!=null)
                {
                app.updatePlayer = new PlayerData(name,b, app.playscreen.assetManager,app.playscreen.theme,new Vector3f(0,1,0));
                app.enqueue(new Callable<Void>() {
                 public Void call() throws Exception { 
                    app.players.Enqueue(app.updatePlayer);
                    app.players.buffer[app.players.FindPlayer(app.updatePlayer.getName())].sprite.addModel(app.bulletAppState, rootNode);
                    if(app.updatePlayer.getID()>app.player.getID())
                    {
                        app.playscreen.updatechat(app.updatePlayer.getName()+" has joined the game!");
                    }
                    return null;  
                  } });
            }
            }
        }
    }
    
    else if(message instanceof LobbyTimerMessage)
    {
        if(app.lobbyscreen.countdown)
        {
        if(((LobbyTimerMessage)message).getTime()>0)
        {
            app.lobbyscreen.setTimer(((LobbyTimerMessage)message).getTime() + " Until Play");
        }
        else
        {
            app.lobbyscreen.reset();
            nifty.gotoScreen("play");
             try {Thread.sleep(3000);} 
                      catch (InterruptedException ex) {}
            app.enqueue(new Callable<Void>() {
            public Void call() throws Exception {
                 app.playscreen.initialize();
                 app.player.sprite.addModel(app.bulletAppState, rootNode);
                 app.player.sprite.Model.addControl(app.chaseCam);
                 app.chaseCam.setEnabled(true);
                    app.chaseCam.setSmoothMotion(false);
                    app.chaseCam.setMaxDistance(12);
                    app.chaseCam.setMinDistance(12);
                    app.chaseCam.setDefaultDistance(12);       
                    app.chaseCam.setInvertVerticalAxis(true);
                 return null;
              } }); 
              Main.state=State.PLAYING;
        }
        }
    }
    
    else if(message instanceof ToLobbyMessage)
    {
       Main.state=State.LOBBY;
       nifty.gotoScreen("lobby");
  
       
                    app.enqueue(new Callable<Void>() {
                        public Void call() throws Exception {
                            
                            for(int i=0;i<app.playscreen.map.dynamicitems.size;i++)
                            {
                                if(app.playscreen.map.dynamicitems.buffer[i]!=null && rootNode.hasChild(app.playscreen.map.dynamicitems.buffer[i].sprite.Model))
                                {
                                    app.playscreen.map.dynamicitems.buffer[i].sprite.deleteModel(rootNode);
                                }
                            }
                            
                            for(int i=0;i<app.playscreen.map.staticitems.size;i++)
                            {
                                if(app.playscreen.map.staticitems.buffer[i]!=null && rootNode.hasChild(app.playscreen.map.staticitems.buffer[i].sprite.Model))
                                {
                                       app.playscreen.map.staticitems.buffer[i].sprite.deleteModel(rootNode);
                                }
                            }
                            
                            for(int i=0;i<app.playscreen.map.sprites.size;i++)
                            {
                                if(app.playscreen.map.sprites.buffer[i]!=null && rootNode.hasChild(app.playscreen.map.sprites.buffer[i].Model))
                                {
                                       app.playscreen.map.sprites.buffer[i].deleteModel(rootNode);
                                       app.playscreen.map.sprites.buffer[i].deletePhysics(app.bulletAppState);
                                }
                            }
                            if(rootNode.hasChild(app.playscreen.map.skybox)){
                                rootNode.detachChild(app.playscreen.map.skybox);
                                app.bulletAppState.getPhysicsSpace().removeAll(app.playscreen.map.skybox);
                            }
                             if(rootNode.hasChild(app.playscreen.map.terrain)){
                            rootNode.detachChild(app.playscreen.map.terrain);
                            app.bulletAppState.getPhysicsSpace().removeAll(app.playscreen.map.terrain);
                            }
                            app.playscreen.initialize();
                            return null;  }});
    }
    
    else if(message instanceof LevelSetupMessage)
    {
        app.lobbyscreen.setLevel(((LevelSetupMessage)message).getLevel(),((LevelSetupMessage)message).getTheme());
    }
    
    
    else if(message instanceof LoggedOutMessage)
    {
                    int b = ((LoggedOutMessage)message).getID(); 
                    app.playerDelete = app.players.FindPlayer(b); 
                    if(app.playerDelete>-1&&app.players!=null && nifty.getCurrentScreen().getScreenId().equals("play")||nifty.getCurrentScreen().getScreenId().equals("lobby")&&app.player!=null && b!=app.player.getID())
                    {
                   
                    app.DeleteName = app.players.buffer[app.playerDelete].getName();
                    app.deleteNode=app.players.buffer[app.playerDelete].sprite.Model;
            
                    app.enqueue(new Callable<Void>() {
                        public Void call() throws Exception {                
                          rootNode.detachChild(app.deleteNode);
                          app.players.delete(app.playerDelete);   
                         app.playscreen.updatechat(app.DeleteName+" has left the game!");
                         return null;
                     } }); 
                    }
    }
    else if(message instanceof WarpToMeMessage)
    {
         byte b = ((WarpToMeMessage)message).getPlayer();
         app.updatePlayeri = app.players.FindPlayer((int)b);
         app.updateLocation=app.players.buffer[app.updatePlayeri].sprite.getLocation(); 
              app.enqueue(new Callable<Void>() {
              public Void call() throws Exception {
                  app.player.sprite.setLocation(app.updateLocation);
     return null;  
                 } }); 
    }
    
     
    else if(message instanceof EndServerMsg)
    {
           app.enqueue(new Callable<Void>() {
              public Void call() throws Exception {
          app.restart();     
          return null;
              }});
    } 
     

        
        else if (message instanceof UpdateMessage) {
        app.updateLocation=((UpdateMessage)message).getLoc();
        app.updatePlayeri = app.players.FindPlayer(((UpdateMessage)message).getID());
        if(app.updatePlayeri>-1)
        {
            app.enqueue(new Callable<Void>() {
            public Void call() throws Exception {
                try{
                    app.players.buffer[app.updatePlayeri].sprite.setLocation(app.updateLocation);
                }
                catch(ArrayIndexOutOfBoundsException e)  { }
            return null;  
          } }); 
            app.players.buffer[app.updatePlayeri].sprite.setWalkDirection(((UpdateMessage)message).getDir());
         }
        } 
  }}
  
  

