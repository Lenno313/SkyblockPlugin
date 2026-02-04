package de.lenno.skyblock.events;

import de.lenno.skyblock.IslandManager;
import de.lenno.skyblock.Skyblock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Orientable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalEvent implements Listener {

    @EventHandler
    public void onPortalTravel(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        Block blockFrom = event.getFrom().getBlock();
        Location locFrom = blockFrom.getLocation();

        if (event.getTo().getWorld().getEnvironment() == World.Environment.NORMAL) {
            event.setCancelled(true);

            Location respawnLoc = player.getBedSpawnLocation();
            if (respawnLoc == null) {
                String uuid = player.getUniqueId().toString();
                double x = Skyblock.getInstance().getConfig().getDouble("players." + uuid + ".x");
                double y = Skyblock.getInstance().getConfig().getDouble("players." + uuid + ".y");
                double z = Skyblock.getInstance().getConfig().getDouble("players." + uuid + ".z");

                respawnLoc = new Location(Bukkit.getWorld("world"), x + 0.5, y, z + 0.5);
            }

            player.teleport(respawnLoc);
            return;
        }

        Location targetLoc = new Location(
                Bukkit.getWorld("world_nether"),
                locFrom.getX(),
                locFrom.getY(),
                locFrom.getZ()
        );

        Location nearestPortal = searchNearestPortal(targetLoc, 40);

        if (nearestPortal != null) {
            event.setTo(nearestPortal.add(0.5, 0, 0.5));
            return;
        }

        String uuid = player.getUniqueId().toString();

        if (Skyblock.getInstance().getConfig().get("players." + uuid + ".has_nether_poral", "false") != "false") {
            event.setTo(targetLoc.add(0.5, 0, 0.5));
            return;
        }

        Axis portalAxis = Axis.X;

        if (blockFrom.getType() == Material.NETHER_PORTAL) {
            if (blockFrom.getBlockData() instanceof Orientable) {
                Orientable data = (Orientable) blockFrom.getBlockData();
                portalAxis = data.getAxis();
            }
        }

        IslandManager.createNetherIsland(targetLoc, portalAxis);
        Skyblock.getInstance().getConfig().set("players." + uuid + ".has_nether_poral", "true");
        event.setTo(targetLoc.add(0.5, 0, 0.5));
        player.sendMessage(Skyblock.PLUGIN_PREFIX + "§7Es wurde eine §4Netherinsel §7für dich erzeugt!");
        player.sendMessage(Skyblock.PLUGIN_PREFIX + "§7PS: Pass auf die §eBlazes §7auf! ;)");
    }

    public Location searchNearestPortal(Location loc, int radius) {
        World world = loc.getWorld();
        Location closestPortal = null;
        double minDistanceSquared = Double.MAX_VALUE;

        int minX = loc.getBlockX() - radius;
        int maxX = loc.getBlockX() + radius;
        int minZ = loc.getBlockZ() - radius;
        int maxZ = loc.getBlockZ() + radius;

        int minY = Math.max(world.getMinHeight(), loc.getBlockY() - 20);
        int maxY = Math.min(world.getMaxHeight(), loc.getBlockY() + 20);

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = minY; y <= maxY; y++) {
                    if (world.getBlockAt(x, y, z).getType() == Material.NETHER_PORTAL) {
                        // Nur wenn wir ein Portal finden, erstellen wir EINMAL ein Location-Objekt
                        Location found = new Location(world, x, y, z);
                        double distSq = found.distanceSquared(loc);

                        if (distSq < minDistanceSquared) {
                            minDistanceSquared = distSq;
                            closestPortal = found;
                        }
                    }
                }
            }
        }
        // Falls wir ein Portal gefunden haben, geben wir die MITTE zurück
        return closestPortal;
    }
}
