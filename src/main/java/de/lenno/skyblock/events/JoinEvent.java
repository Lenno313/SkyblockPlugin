package de.lenno.skyblock.events;

import de.lenno.skyblock.Skyblock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        e.setJoinMessage(Skyblock.PLUGIN_PREFIX + player.getDisplayName() + " §7hat den Server §abetreten§7!");

        player.sendMessage(Skyblock.PLUGIN_PREFIX + "§fViel Spaß beim Bauen!");
    }
}
