package mygame.Screens;

import com.jme3.app.state.AbstractAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import java.util.concurrent.Callable;
import mygame.Data.Map;
import mygame.Data.PlayerSprite;
import mygame.Main;
import mygame.messages.AddStaticItemMessage;
import mygame.messages.ChatMessage;
import mygame.messages.DropItemMessage;
import mygame.messages.GetMapMessage;
import mygame.messages.RemoveDynamicItemMessage;
import mygame.messages.RemoveStaticItemMessage;
import mygame.messages.UpdateMessageNID;
import mygame.messages.WarpToMeMessage;

public class playScreen extends AbstractAppState implements ScreenController {

    private Nifty nifty;
    private Screen screen;
    private Main app;
    public int theme, level;
    public Map map;
    public Node rootNode;
    boolean showInventory,showChat;
    ViewPort viewPort;
    public AssetManager assetManager;
    public InputManager inputManager;
    Boolean isChatting = false;
    public Boolean loaded=false;
    public BulletAppState bulletAppState;

    public playScreen(Main _app, AssetManager assets, Node root, BulletAppState _bullet, InputManager input) {
        this.app = _app;
        this.inputManager = input;
        this.rootNode = root;
        this.assetManager = assets;
        this.bulletAppState = _bullet;
    }

    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    public void setLevel(int lvl, int thm)
    {
        level=lvl;
        theme=thm;
    }
    
    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public Screen getScreen() {
        return screen;
    }

    public void initialize() {
        loaded=false;
        showInventory = true; showChat = true;
        nifty.getScreen("play").findElementByName("load").show();
        nifty.setIgnoreKeyboardEvents(true);
        nifty.setIgnoreMouseEvents(true);
        screen.findNiftyControl("UserName", Label.class).setText(app.player.getName());
        inputManager.addMapping("Chat", new KeyTrigger(KeyInput.KEY_RETURN)); // A and left arrow
        inputManager.addMapping("ClickScreen", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        app.Inventory = new int[18][3];
        NiftyImage img = nifty.getRenderEngine().createImage("Textures/" + theme + "/items/0.png", false);
        for (int i = 0; i < app.Inventory.length; i++) {
            app.Inventory[i][0] = 0;
            app.Inventory[i][1] = 0;
            app.Inventory[i][2] = -1;
            Element niftyElement = nifty.getScreen("play").findElementByName("item" + (i + 1));
            niftyElement.getRenderer(ImageRenderer.class).setImage(img);
        }
        screen.findNiftyControl("ChatBox", TextField.class).disable();
        screen.findNiftyControl("ChatBox", TextField.class).setText("Press enter to chat.");
        resetchat();
        updatechat("Welcome to the game, type /help for a list of available commands.");
        isChatting=false; 
        //   bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }

    //   for (int i = 0; i < app.Inventory.length; i++) {
    //   ImageBuilder frame = new ImageBuilder("imageblarg"+i);
    //   frame.filename("Textures/" + theme + "/items/" + 0 + ".png");
    //    frame.childLayout(ChildLayoutType.Overlay);
    //    frame.x(""+((1024-32)-(50*(i+1))));
    //    frame.build(nifty, screen, nifty.getCurrentScreen().findElementByName("panel2"));
    //   }
    public void Chat() {
        if (!isChatting) {
            nifty.setIgnoreKeyboardEvents(false);
            screen.findNiftyControl("ChatBox", TextField.class).enable();
            screen.findNiftyControl("ChatBox", TextField.class).setFocus();
            screen.findNiftyControl("ChatBox", TextField.class).setText("");
            isChatting = true;
        } else {
            nifty.setIgnoreKeyboardEvents(true);
            String Blarg = screen.findNiftyControl("ChatBox", TextField.class).getText();
                    if (Blarg.startsWith("/warptoplayer")) {
                        String playerName = Blarg.substring(14);
                        int i = app.players.FindPlayer(playerName);
                        System.out.println(playerName);
                        if (i > -1) {
                            app.player.sprite.setLocation(app.players.buffer[i].sprite.getLocation());
                            app.player.sprite.updateScene = true;
                        } else {
                            updatechat(playerName + " is not online.");
                        }
                    } else if (Blarg.startsWith("/warptome")) {
                        String playerName = Blarg.substring(10);
                        int i = app.players.FindPlayer(playerName);
                        if (i > -1) {
                            app.players.buffer[i].sprite.setLocation(app.player.sprite.getLocation());
                            app.client.send(new WarpToMeMessage((byte) app.players.buffer[i].getID()));
                        } else {
                            updatechat(playerName + " is not online.");
                        }
                    } else if (Blarg.startsWith("/respawn")) {
                        app.player.sprite.setLocation(new Vector3f(0, 1, 0));
                        app.player.sprite.updateScene = true;
                    } else if (Blarg.startsWith("/help")) {
                        updatechat("/warptoplayer <playername>, /warptome <playername>, /respawn");
                    } else if (Blarg.equals("")) {
                    
                    } else {
                        if (Blarg.length() >= 45) {
                            app.client.send(new ChatMessage(app.player.getName() + ": " + Blarg.subSequence(0, 42) + "..."));
                        } else {
                            app.client.send(new ChatMessage(app.player.getName() + ": " + screen.findNiftyControl("ChatBox", TextField.class).getText()));
                        }
                    }
            screen.findNiftyControl("ChatBox", TextField.class).disable();
            screen.findNiftyControl("ChatBox", TextField.class).setText("Press enter to chat.");
            isChatting = false;
        }
    }
    
    public void showChat()
    {
        if (showChat) {
            screen.findElementByName("panel").setVisible(false);
            showChat = false;
        } else {
            screen.findElementByName("panel").setVisible(true);
            showChat = true;
        }
    }

    public void ShowInventory() {
        if (showInventory) {
            screen.findElementByName("panel2").setVisible(false);
            showInventory = false;
        } else {
            screen.findElementByName("panel2").setVisible(true);
            showInventory = true;
        }
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {

            if (name.equals("Chat") && keyPressed) {
                Chat();
            }
            if (name.equals("ClickScreen") && keyPressed) {
                if (!app.player.sprite.updateScene) {
                    Vector2f click2d = inputManager.getCursorPosition();
                    Vector3f click3d = app.getCamera().getWorldCoordinates(
                            new Vector2f(click2d.x, click2d.y), 0f).clone();
                    Vector3f dir = app.getCamera().getWorldCoordinates(
                            new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
                    Ray ray = new Ray(click3d, dir);


                    for (int i = 0; i < map.staticitems.size; i++) {
                        if (map.staticitems.buffer[i] != null && map.staticitems.buffer[i].sprite.getVisible()) {
                            CollisionResults results = new CollisionResults();
                            map.staticitems.buffer[i].sprite.Model.collideWith(ray, results);
                            if (results.size() > 0 && results.getCollision(0).getDistance() < 20) {
                                addItem(map.staticitems.buffer[i].getType());
                                app.client.send(new RemoveStaticItemMessage(map.staticitems.buffer[i].getID()));
                            }
                        }
                    }
                    for (int i = 0; i < map.dynamicitems.size; i++) {
                        if (map.dynamicitems.buffer[i] != null && map.dynamicitems.buffer[i].sprite.getVisible()) {
                            CollisionResults results = new CollisionResults();
                            map.dynamicitems.buffer[i].sprite.Model.collideWith(ray, results);
                            if (results.size() > 0 && results.getCollision(0).getDistance() < 20) {
                                addItem(map.dynamicitems.buffer[i].getType());
                                app.client.send(new RemoveDynamicItemMessage(map.dynamicitems.buffer[i].getID()));
                            }
                        }
                    }
                }
            }
        }
    };
    
    public void logout()
    {
        app.enqueue(new Callable<Void>() {
                        public Void call() throws Exception {          
        app.restart();
        return null;
                        }});
    }

    public void addItem(int id) {
        boolean exists = false;
        for (int i = 0; i < app.Inventory.length; i++) {
            if (app.Inventory[i][0] == 2 && id == 2 || app.Inventory[i][0] == 4 && id == 4) {
                app.Inventory[i][1]++;
                nifty.getScreen("play").findNiftyControl("item" + (i + 1) + "_label", Label.class).setText("" + app.Inventory[i][1]);
                exists = true;
                break;
            }
        }
        for (int i = 0; i < app.Inventory.length; i++) {
            if (app.Inventory[i][0] == 0 && id != 0 && !exists) {
                app.Inventory[i][1]++;
                app.Inventory[i][0] = id;
                NiftyImage img = nifty.getRenderEngine().createImage("Textures/" + theme + "/items/" + id + ".png", false);



                Element niftyElement = nifty.getScreen("play").findElementByName("item" + (i + 1));

                niftyElement.getRenderer(ImageRenderer.class).setImage(img);
                if (app.Inventory[i][1] > 1) {
                    nifty.getCurrentScreen().findNiftyControl("item" + (i + 1) + "_label", Label.class).setText("" + app.Inventory[i][1]);
                }
                break;
            }
        }
    }

    public void i1click() {
        itemClicked(1);
    }

    public void i2click() {
        itemClicked(2);
    }

    public void i3click() {
        itemClicked(3);
    }

    public void i4click() {
        itemClicked(4);
    }

    public void i5click() {
        itemClicked(5);
    }

    public void i6click() {
        itemClicked(6);
    }

    public void i7click() {
        itemClicked(7);
    }

    public void i8click() {
        itemClicked(8);
    }

    public void i9click() {
        itemClicked(9);
    }

    public void i10click() {
        itemClicked(10);
    }

    public void i11click() {
        itemClicked(11);
    }

    public void i12click() {
        itemClicked(12);
    }

    public void i13click() {
        itemClicked(13);
    }

    public void i14click() {
        itemClicked(14);
    }

    public void i15click() {
        itemClicked(15);
    }

    public void i16click() {
        itemClicked(16);
    }

    public void i17click() {
        itemClicked(17);
    }

    public void i18click() {
        itemClicked(18);
    }

    public void i1unclick() {
        itemunClicked(1);
    }

    public void i2unclick() {
        itemunClicked(2);
    }

    public void i3unclick() {
        itemunClicked(3);
    }

    public void i4unclick() {
        itemunClicked(4);
    }

    public void i5unclick() {
        itemunClicked(5);
    }

    public void i6unclick() {
        itemunClicked(6);
    }

    public void i7unclick() {
        itemunClicked(7);
    }

    public void i8unclick() {
        itemunClicked(8);
    }

    public void i9unclick() {
        itemunClicked(9);
    }

    public void i10unclick() {
        itemunClicked(10);
    }

    public void i11unclick() {
        itemunClicked(11);
    }

    public void i12unclick() {
        itemunClicked(12);
    }

    public void i13unclick() {
        itemunClicked(13);
    }

    public void i14unclick() {
        itemunClicked(14);
    }

    public void i15unclick() {
        itemunClicked(15);
    }

    public void i16unclick() {
        itemunClicked(16);
    }

    public void i17unclick() {
        itemunClicked(17);
    }

    public void i18unclick() {
        itemunClicked(18);
    }

    public void itemClicked(int id) {
        if (app.Inventory[id - 1][1] > 0) {
            app.Inventory[id - 1][1]--;

            if (app.Inventory[id - 1][0] == 1) {
                app.player.sprite.addStamina(20);
            }
            
            
            
            updateStats();

            if (app.Inventory[id - 1][1] == 0) {
                app.Inventory[id - 1][0] = 0;
                NiftyImage img = nifty.getRenderEngine().createImage("Textures/" + theme + "/items/0.png", false);
                Element niftyElement = nifty.getCurrentScreen().findElementByName("item" + (id));
                niftyElement.getRenderer(ImageRenderer.class).setImage(img);
            }
            if (app.Inventory[id - 1][1] < 2) {
                nifty.getScreen("play").findNiftyControl("item" + (id) + "_label", Label.class).setText("");
            } else {
                nifty.getScreen("play").findNiftyControl("item" + (id) + "_label", Label.class).setText("" + app.Inventory[id - 1][1]);
            }
        }
    }

    public void itemunClicked(int id) {
        if (app.Inventory[id - 1][1] > 0) {
            app.Inventory[id - 1][1]--;
            app.client.send(new DropItemMessage(app.Inventory[id - 1][0], app.player.sprite.getLocation()));

            if (app.Inventory[id - 1][1] == 0) {
                app.Inventory[id - 1][0] = 0;
                NiftyImage img = nifty.getRenderEngine().createImage("Textures/" + theme + "/items/0.png", false);
                Element niftyElement = nifty.getCurrentScreen().findElementByName("item" + (id));
                niftyElement.getRenderer(ImageRenderer.class).setImage(img);
            }
            if (app.Inventory[id - 1][1] < 2) {
                nifty.getCurrentScreen().findNiftyControl("item" + (id) + "_label", Label.class).setText("");
            } else {
                nifty.getCurrentScreen().findNiftyControl("item" + (id) + "_label", Label.class).setText("" + app.Inventory[id - 1][1]);
            }
        }
    }

    public void setscifi() {
        this.theme = 2;
        app.player.sprite.resetTheme(theme, assetManager, rootNode);
        map.ChangeTheme(theme, level, rootNode, assetManager);
        if (app.players != null) {
            for (int i = 0; i < app.players.size; i++) {
                if (app.players.buffer[i] != null) {
                    app.players.buffer[i].sprite.resetTheme(theme, assetManager, rootNode);
                }
            }
        }
        for (int i = 1; i <= app.Inventory.length; i++) {
            NiftyImage img = nifty.getRenderEngine().createImage("Textures/" + theme + "/items/" + app.Inventory[i - 1] + ".png", false);
            Element niftyElement = nifty.getCurrentScreen().findElementByName("item" + i);
            niftyElement.getRenderer(ImageRenderer.class).setImage(img);
        }
    }

    public void setfilmnoir() {
        this.theme = 1;
        app.player.sprite.resetTheme(theme, assetManager, rootNode);
        map.ChangeTheme(theme, level, rootNode, assetManager);
        if (app.players != null) {
            for (int i = 0; i < app.players.size; i++) {
                if (app.players.buffer[i] != null) {
                    app.players.buffer[i].sprite.resetTheme(theme, assetManager, rootNode);
                }
            }
        }
        for (int i = 1; i <= app.Inventory.length; i++) {
            NiftyImage img = nifty.getRenderEngine().createImage("Textures/" + theme + "/items/" + app.Inventory[i - 1] + ".png", false);
            Element niftyElement = nifty.getCurrentScreen().findElementByName("item" + i);
            niftyElement.getRenderer(ImageRenderer.class).setImage(img);
        }
    }

    public void setwestern() {
        this.theme = 0;
        app.player.sprite.resetTheme(theme, assetManager, rootNode);
        map.ChangeTheme(theme, level, rootNode, assetManager);
        if (app.players != null) {
            for (int i = 0; i < app.players.size; i++) {
                if (app.players.buffer[i] != null) {
                    app.players.buffer[i].sprite.resetTheme(theme, assetManager, rootNode);
                }
            }
        }
        for (int i = 1; i <= app.Inventory.length; i++) {
            NiftyImage img = nifty.getRenderEngine().createImage("Textures/" + theme + "/items/" + app.Inventory[i - 1] + ".png", false);
            Element niftyElement = nifty.getCurrentScreen().findElementByName("item" + i);
            niftyElement.getRenderer(ImageRenderer.class).setImage(img);
        }
    }

    @Override
    public void update(float tpf) {
        if(nifty.getCurrentScreen().getScreenId().equals("play"))
        {
            
            if (map!=null&&rootNode.hasChild(map.terrain)&&!loaded) {
            inputManager.addListener(actionListener, "Chat", "ClickScreen");
            if (app.players != null) {
            for (int i = 0; i < app.players.size; i++) {
                if (app.players.buffer[i] != null) {
                    app.players.buffer[i].sprite.setLocation(0,10,0);
                }
            if(app.player!=null){
                app.player.sprite.setLocation(0,10,0);
            }
            }
             }  
            nifty.getCurrentScreen().findElementByName("load").hide();
            nifty.setIgnoreMouseEvents(false);
            loaded=true;
        }
        
        
        app.player.sprite.InternalUpdate(app.getCamera(), tpf);
        if (app.player.sprite.Sstate == PlayerSprite.playerState.MINING) {
            app.player.sprite.useStamina(1);
            updateStats();
        }

        
        nifty.getCurrentScreen().findElementByName("health").setWidth(Math.round(nifty.getCurrentScreen().findElementByName("health").getWidth()*app.player.sprite.getHealthPercent()));




        if (app.player.sprite.updateScene) {
            app.client.send(new UpdateMessageNID(app.player.sprite.getLocation(), app.player.sprite.getWalkDirection()));
            app.player.sprite.updateScene = false;
        }
        //Vector3f magnitude;
        if (app.players != null) {
            for (int i = 0; i < app.players.size; i++) {
                if (app.players.buffer[i] != null) {
                    app.players.buffer[i].sprite.Update(tpf);
                }
            }
        }
        
        for(int i=0;i<map.warppoints.size;i++)
        {
            if(map.warppoints.buffer[i]!=null)
            {
            Vector3f magnitude= new Vector3f(0,0,0);
            double distance;
            
            magnitude.x = app.player.sprite.getLocation().x>map.warppoints.buffer[i].sprite.Model.getLocalTranslation().x ? app.player.sprite.getLocation().x- map.warppoints.buffer[i].sprite.Model.getLocalTranslation().x : map.warppoints.buffer[i].sprite.Model.getLocalTranslation().x-app.player.sprite.getLocation().x;
            magnitude.y = app.player.sprite.getLocation().y>map.warppoints.buffer[i].sprite.Model.getLocalTranslation().y ? app.player.sprite.getLocation().y- map.warppoints.buffer[i].sprite.Model.getLocalTranslation().y : map.warppoints.buffer[i].sprite.Model.getLocalTranslation().y-app.player.sprite.getLocation().y;
            magnitude.y = app.player.sprite.getLocation().y>map.warppoints.buffer[i].sprite.Model.getLocalTranslation().z ? app.player.sprite.getLocation().z- map.warppoints.buffer[i].sprite.Model.getLocalTranslation().z : map.warppoints.buffer[i].sprite.Model.getLocalTranslation().z-app.player.sprite.getLocation().z;
            distance = Math.sqrt((magnitude.z*magnitude.z)+(magnitude.y*magnitude.y)+(magnitude.z*magnitude.z));
            if(distance<=0.5)
                {
                    app.player.sprite.setLocation(map.warppoints.buffer[i].getGoto());
                    app.client.send(new UpdateMessageNID(app.player.sprite.getLocation(), app.player.sprite.getWalkDirection()));    
                 }
            }
        }
           
       }
    }
    
    public void updateStats()
    {
       nifty.getCurrentScreen().findElementByName("stamina").setWidth(Math.round(nifty.getCurrentScreen().findElementByName("staminaback").getWidth()*app.player.sprite.getStaminaPercent()));
       nifty.getCurrentScreen().findElementByName("health").setWidth(Math.round(nifty.getCurrentScreen().findElementByName("healthback").getWidth()*app.player.sprite.getHealthPercent()));
    }

    public void updatechat(String in) {
        {
            screen.findNiftyControl("Chat5", Label.class).setText(screen.findNiftyControl("Chat4", Label.class).getText());
            screen.findNiftyControl("Chat4", Label.class).setText(screen.findNiftyControl("Chat3", Label.class).getText());
            screen.findNiftyControl("Chat3", Label.class).setText(screen.findNiftyControl("Chat2", Label.class).getText());
            screen.findNiftyControl("Chat2", Label.class).setText(screen.findNiftyControl("Chat1", Label.class).getText());
            screen.findNiftyControl("Chat1", Label.class).setText(in);
        }
    }
    
    public void resetchat()
    {
        screen.findNiftyControl("Chat5", Label.class).setText("");
        screen.findNiftyControl("Chat4", Label.class).setText("");
        screen.findNiftyControl("Chat3", Label.class).setText("");
        screen.findNiftyControl("Chat2", Label.class).setText("");
        screen.findNiftyControl("Chat1", Label.class).setText("");
    }
}
