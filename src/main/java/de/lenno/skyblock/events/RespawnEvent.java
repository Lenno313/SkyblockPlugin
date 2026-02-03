package de.lenno.skyblock.events;

import de.lenno.skyblock.Skyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (player.getBedSpawnLocation() != null) {
            return;
        }
        // 2. Er hat KEIN Bett (oder es wurde abgebaut):
        // Wir holen die Insel-Koordinaten aus der Config
        String uuid = player.getUniqueId().toString();
        if (Skyblock.getInstance().getConfig().contains("players." + uuid)) {
            double x = Skyblock.getInstance().getConfig().getDouble("players." + uuid + ".x");
            double y = Skyblock.getInstance().getConfig().getDouble("players." + uuid + ".y");
            double z = Skyblock.getInstance().getConfig().getDouble("players." + uuid + ".z");

            Location islandSpawn = new Location(Bukkit.getWorld("world"), x + 0.5, y, z + 0.5);

            // Wir setzen den Respawn-Punkt auf die Insel
            event.setRespawnLocation(islandSpawn);
        }
    }
}
