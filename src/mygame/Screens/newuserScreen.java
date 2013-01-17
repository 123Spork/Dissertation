package mygame.Screens;

 
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;
import mygame.messages.CreateUserMessage;
import mygame.utilities.EmailChecker;
 
public class newuserScreen extends AbstractAppState implements ScreenController {
 
  private Nifty nifty;
  private Screen screen;
  private Main app;
  EmailChecker emailcheck;
 
  public newuserScreen(String data,Main _app) { 
      this.app = _app;
  } 
 
  public void bind(Nifty nifty, Screen screen) {
    this.nifty = nifty;
    this.screen = screen;
    System.out.println("startScreen::onStartScreen called");
  }
 
  public void onStartScreen()
  {
     System.out.println("startScreen::onStartScreen called");
    
  }
  
  public void createuser()
  {
      emailcheck = new EmailChecker();
      String pas = screen.findNiftyControl("Password", TextField.class).getText();
      String reppas = screen.findNiftyControl("RepeatPassword", TextField.class).getText();
      String usr = screen.findNiftyControl("Username", TextField.class).getText();
      String email = screen.findNiftyControl("Email", TextField.class).getText();
      if(!pas.equals(reppas))
      {
          printmessage("Passwords do not match.");
      }
      else if(reppas.equals(""))
      {
          printmessage("Re-enter a password.");
      } 
      else if(pas.equals(""))
      {
          printmessage("Enter a password.");
      }
      else if(usr.equals(""))
      {
          printmessage("Enter a username.");
      }
      else if(email.equals(""))
      {
          printmessage("Enter an email.");
      }
      else if(!emailcheck.checkEmail(email))
      {
          printmessage("Not a valid email.");
      }
      else
      {
         app.client.send(new CreateUserMessage(usr,pas,email));
      }
  }
  
  public void printmessage(String msg)
  {
       screen.findNiftyControl("Message", Label.class).setText(msg);
  }
  
  
  public void backtologin()
  {
      nifty.gotoScreen("start");  
      app.startscreen.bind(nifty, nifty.getCurrentScreen());
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
 
  
  
  
  @Override
  public void update(float tpf) { 
    /** jME update loop! */ 
  }
}
 