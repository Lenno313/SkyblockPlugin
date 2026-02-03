package de.lenno.skyblock.events;

import de.lenno.skyblock.IslandManager;
import de.lenno.skyblock.Skyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        player.setDisplayName("§b" + player.getName());
        e.setJoinMessage(Skyblock.PLUGIN_PREFIX + player.getDisplayName() + " §7hat den Server §abetreten§7!");

        String uuid = player.getUniqueId().toString();

        // 1. Prüfen, ob der Spieler schon in der Config steht
        if (!Skyblock.getInstance().getConfig().contains("players." + uuid)) {

            // 2. Nächste freie Position berechnen
            // Wir holen uns die aktuelle Insel-Anzahl aus der Config (Standard 0)
            int islandCount = Skyblock.getInstance().getConfig().getInt("island-count", 0);

            // Wir versetzen jede Insel um 100 Blöcke, damit sie sich nicht sehen
            List<Location> islandLocs = Skyblock.islandLocations;
            Location islandLoc = islandLocs.get(islandCount);
            double x = islandLoc.getX();
            double y = islandLoc.getY();
            double z = islandLoc.getZ();

            Location islandLocation = new Location(Bukkit.getWorld("world"), x, y, z);

            // 3. Insel generieren (deine Methode von vorhin)
            IslandManager.generateIsland(islandLocation);

            // 4. Den Villager spawnen (falls die Methode im IslandManager ist)
            // IslandManager.spawnShopVillager(islandLocation.clone().add(2, 0, 2));

            // 5. Spieler teleportieren (leicht erhöht, damit er nicht im Block spawnt)
            Location spawnLoc = islandLocation.add(0.5, 1, 0.5);
            player.teleport(spawnLoc);

            // 6. In Config speichern
            Skyblock.getInstance().getConfig().set("players." + uuid + ".hasIsland", true);
            Skyblock.getInstance().getConfig().set("players." + uuid + ".x", x);
            Skyblock.getInstance().getConfig().set("players." + uuid + ".y", y);
            Skyblock.getInstance().getConfig().set("players." + uuid + ".z", z);

            // Insel-Zähler erhöhen für den nächsten Spieler
            Skyblock.getInstance().getConfig().set("island-count", islandCount + 1);

            // WICHTIG: Config speichern!
            Skyblock.getInstance().saveConfig();

            player.sendMessage(Skyblock.PLUGIN_PREFIX + "§aDeine Insel wurde erstellt!");
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        e.setQuitMessage(Skyblock.PLUGIN_PREFIX + player.getDisplayName() + " §7hat den Server §cverlassen§7!");
    }
}
