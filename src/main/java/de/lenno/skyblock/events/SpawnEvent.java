package de.lenno.skyblock.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnEvent implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (!(event.getLocation().getWorld().getEnvironment() == World.Environment.NETHER)) {
            // Wither-Skelett spawning on netherbricks
            Location loc = event.getLocation();
            if (loc.getBlock().getRelative(0, -1, 0).getType() == Material.NETHER_BRICKS) {
                // Zu 5% Chance (oder 100%, wenn du willst) in ein Wither-Skelett verwandeln
                if (Math.random() < 0.05) {
                    event.setCancelled(true);
                    loc.getWorld().spawnEntity(loc, EntityType.WITHER_SKELETON);
                }
            }
        }
        else if (event.getEntityType() == EntityType.SQUID || event.getEntityType() == EntityType.GLOW_SQUID) {
            // Spawning von Guardians auf allen Arten von Prismarine-Blöcken
            Location checkLoc = event.getLocation().clone();

            // Wir schauen maximal 10 Blöcke nach unten durch Wasser/Luft
            for (int i = 0; i < 10; i++) {
                Material blockType = checkLoc.getBlock().getType();

                if (blockType.name().contains("PRISMARINE")) {
                    // Treffer! Wir verwandeln den Squid zu 25% in einen Guardian
                    if (Math.random() < 0.25) {
                        event.setCancelled(true);
                        event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.GUARDIAN);
                    }
                    break; // Schleife beenden
                }

                // Wenn wir auf etwas anderes als Wasser stoßen (außer Prismarin), abbrechen
                if (blockType != Material.WATER && i > 0) {
                    break;
                }

                checkLoc.subtract(0, 1, 0); // Einen Block tiefer prüfen
            }
        }
    }
}
