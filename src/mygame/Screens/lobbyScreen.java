package mygame.Screens;
 
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import mygame.Main;
 
public class lobbyScreen extends AbstractAppState implements ScreenController {
 
  private Nifty nifty;
  private Screen screen;
  private Main app;
  public boolean countdown;
  
  public lobbyScreen(String data,Main _app) { 
      this.app = _app;
  } 
 
  public void bind(Nifty nifty, Screen screen) {
    this.nifty = nifty;
    this.screen = screen; 
  }
 
  public void onStartScreen()
  {
  }
  
  public void onEndScreen()
  {
     
  }

  public Screen getScreen()
  {
      return screen;
  }
 
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
  }
  
  public void setTimer(String _in)
  {
       nifty.getCurrentScreen().findNiftyControl("Timer",  Label.class).setText(_in); 
  }
  
  public void setLevel(int lvl, int thm)
  {
      app.playscreen.setLevel(lvl, thm);
      switch(lvl){
              case 0:
                  nifty.getCurrentScreen().findNiftyControl("Level",  Label.class).setText("Western Level"); break;
              case 1:
                  nifty.getCurrentScreen().findNiftyControl("Level",  Label.class).setText("Film Noir Level"); break;
              case 2:
                  nifty.getCurrentScreen().findNiftyControl("Level",  Label.class).setText("SciFi Level"); break;
      }
      
      switch(thm){
              case 0:
                  nifty.getCurrentScreen().findNiftyControl("Theme",  Label.class).setText("Western Theme"); break;
              case 1:
                  nifty.getCurrentScreen().findNiftyControl("Theme",  Label.class).setText("Film Noir Theme"); break;
              case 2:
                  nifty.getCurrentScreen().findNiftyControl("Theme",  Label.class).setText("SciFi Theme"); break;
      }
  }
  
  public void reset()
  {
      countdown=false;
      setTimer("Waiting for Server"); 
  }

  @Override
  public void update(float tpf) {
      if(nifty.getCurrentScreen().getScreenId().equals("lobby"))
      {
      nifty.getCurrentScreen().findNiftyControl("Player1", Label.class).setText(app.player.getName()); 
      for(int j=2;j<9;j++)
      {
            nifty.getCurrentScreen().findNiftyControl("Player"+j,  Label.class).setText("No Player"); 
      }
      for(int i=0;i<app.players.size;i++)
      {
          if(app.players.buffer[i]!=null)
          {
             for(int j=2;j<9;j++)
             {
                if(nifty.getCurrentScreen().findNiftyControl("Player"+j,  Label.class).getText().equals("No Player"))
                {
                    nifty.getCurrentScreen().findNiftyControl("Player"+j,  Label.class).setText(app.players.buffer[i].getName()); 
                    break;
                }
            }
          }
      }
      }
  }
  
}
 