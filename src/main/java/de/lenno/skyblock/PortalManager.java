package de.lenno.skyblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class PortalManager {

    private Location findPortalCenter(Location center, int radius) {
        World world = center.getWorld();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block b = world.getBlockAt(center.getBlockX() + x, center.getBlockY() + y, center.getBlockZ() + z);
                    if (b.getType() == Material.NETHER_PORTAL) {
                        // Wir geben die Mitte des Blocks zurück + 0.5 für Zentrierung
                        return b.getLocation().add(0.5, 0, 0.5);
                    }
                }
            }
        }
        return null;
    }
}
