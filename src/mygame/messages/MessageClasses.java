
package mygame.messages;

import com.jme3.network.serializing.Serializer;

public class MessageClasses {
    
    public void Serialize()
    {
        Serializer.registerClass(ByteMessage.class);
        Serializer.registerClass(LoginMessage.class);  
        Serializer.registerClass(LoggedInMessage.class);
        Serializer.registerClass(LoggedOutMessage.class);
        Serializer.registerClass(GetMapMessage.class);  
        Serializer.registerClass(ChatMessage.class);
        Serializer.registerClass(UpdateMessage.class);   
        Serializer.registerClass(UpdateMessageNID.class);   
        Serializer.registerClass(CreateUserMessage.class); 
        Serializer.registerClass(MapSetupMessage.class); 
        Serializer.registerClass(AddStaticItemMessage.class);
        Serializer.registerClass(RemoveStaticItemMessage.class);
        Serializer.registerClass(AddDynamicItemMessage.class);
        Serializer.registerClass(RemoveDynamicItemMessage.class);
        Serializer.registerClass(DropItemMessage.class); 
        Serializer.registerClass(WarpToMeMessage.class);    
        Serializer.registerClass(EndServerMsg.class);   
        Serializer.registerClass(AddSpriteMessage.class); 
        Serializer.registerClass(AddWarpMessage.class);
        Serializer.registerClass(RemoveSpriteMessage.class);  
        Serializer.registerClass(LobbyTimerMessage.class);
        Serializer.registerClass(ToLobbyMessage.class);
        Serializer.registerClass(LevelSetupMessage.class);
    }
    
}
