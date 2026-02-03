package de.lenno.skyblock.events;

import de.lenno.skyblock.Skyblock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        event.setFormat(Skyblock.PLUGIN_PREFIX + player.getDisplayName() + " §8| §7" + message);
    }
}
