package fr.atope.acore.events;

import fr.atope.acore.ACore;
import fr.atope.acore.commands.ChatCommands;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        boolean chatStatut = ChatCommands.isChatDisabled();
        if(chatStatut && !event.getPlayer().hasPermission(ACore.getInstance().getConfig().getString("permissions.chat-bypass"))) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ACore.getInstance().getConfigManager().getConfigFile("messages.yml").getString("chat-disabled"));
        }
    }

}
