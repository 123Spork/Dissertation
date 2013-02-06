package mygame.Data;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import mygame.Main;
import mygame.messages.UpdateMessageNID;

public class CollisionListener implements PhysicsCollisionListener {

    public Main app;
    BulletAppState bullet;
    
    public CollisionListener(Main main, BulletAppState bulletAppState)
    {   
        this.app=main;
        this.bullet=bulletAppState;
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }
    
    public void collision(PhysicsCollisionEvent event) {
       if (app.player.sprite.updateScene && app.player.sprite.Sstate.equals(PlayerSprite.playerState.MINING) && (event.getNodeA().equals(app.player.sprite.Model) || event.getNodeB().equals(app.player.sprite.Model)))
       {
        for(int i=0;i<app.playscreen.map.scriptedobjects.size;i++)
        {
                if(app.playscreen.map.scriptedobjects.buffer[i]!=null)
                {
                   if (event.getNodeA().equals(app.playscreen.map.scriptedobjects.buffer[i].sprite.GhostNode) || event.getNodeB().equals(app.playscreen.map.scriptedobjects.buffer[i].sprite.GhostNode))
                    {
                        switch(app.playscreen.map.scriptedobjects.buffer[i].getType())
                        {
                           case 0: FireCannon(); break;
                           case 1: LoseHealth(); break;
                        } 
                    }
                }
        }
        
       
        for(int i=0;i<app.playscreen.map.warppoints.size;i++)
        {
            if(app.playscreen.map.warppoints.buffer[i]!=null)
            {
                if (event.getNodeA().equals(app.playscreen.map.warppoints.buffer[i].sprite.GhostNode) || event.getNodeB().equals(app.playscreen.map.warppoints.buffer[i].sprite.GhostNode))
                {
                    app.player.sprite.setLocation(app.playscreen.map.warppoints.buffer[i].getGoto());
                    app.client.send(new UpdateMessageNID(app.player.sprite.getLocation(), app.player.sprite.getWalkDirection()));    
                }
            }
        }
      }
    }
    
    public void FireCannon()
    {
        app.player.sprite.useHealth(30);
    }
    
    public void LoseHealth()
    {
         app.player.sprite.useHealth(30);
    }

}