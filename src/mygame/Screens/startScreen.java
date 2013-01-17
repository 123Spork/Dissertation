package mygame.Screens;

 
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.ClientListener;
import mygame.Main;
import mygame.messages.LoginMessage;
 
public class startScreen extends AbstractAppState implements ScreenController {
 
  private Nifty nifty;
  private Screen screen;
  private Main app;
  private String usr,pas;
   InputManager inputManager;
  public startScreen(String data,Main _app,InputManager input)
  { 
    this.app = _app;
    this.inputManager = input;               
    inputManager.addMapping("Go", new KeyTrigger(KeyInput.KEY_RETURN)); // A and left arrow
    inputManager.addListener(actionListener, "Go");
  } 
 
  public void bind(Nifty nifty, Screen screen) {
    this.nifty = nifty;
    this.screen = screen;
  }
  
  
  public void setClient()
  {
    app.setClient();
  }
  
 
  public void onStartScreen()
  {
  }
  
  public void login()
  {
      try{
         
        usr =  nifty.getCurrentScreen().findNiftyControl("Username", TextField.class).getText();
        pas =  nifty.getCurrentScreen().findNiftyControl("Password", TextField.class).getText();
        app.client.send(new LoginMessage(usr,pas));
      }
      catch(IllegalStateException e)
      {
          setClient();
      }
      try{
         
      usr =  nifty.getCurrentScreen().findNiftyControl("Username", TextField.class).getText();
      pas =  nifty.getCurrentScreen().findNiftyControl("Password", TextField.class).getText();
      app.client.send(new LoginMessage(usr,pas));
      }
       catch(IllegalStateException e)
      {
          printservermessage("Server Offline");
      }
  }
      
  public void printmessage(String msg)
  {
       nifty.getCurrentScreen().findNiftyControl("Message", Label.class).setText(msg);
  }
  
  public void printservermessage(String msg)
  {
       nifty.getCurrentScreen().findNiftyControl("ServerMessage", Label.class).setText(msg);
  }
  
  public void createuser()
  {
      if(app.client!=null)
      {
         nifty.gotoScreen("newuser");
      }
  }

  public String getuser()
  {
      return usr;
  }
  
  public String getpass()
  {
      return pas;
  }

  public void onEndScreen()
  {
     
  }

  public Screen getScreen()
  {
      return screen;
  }
  
  public void quitGame() {
    app.stop(); 
  }
  
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
  }
  
 private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Go") && keyPressed) {
               if(app.client!=null)
               {
                   login();
               }
            }
        }};
 
  
  
  
  @Override
  public void update(float tpf) { 
    /** jME update loop! */ 
  }
}
 