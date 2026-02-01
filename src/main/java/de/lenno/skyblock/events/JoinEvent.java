package de.lenno.skyblock.events;

import de.lenno.skyblock.IslandManager;
import de.lenno.skyblock.Skyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        e.setJoinMessage(Skyblock.PLUGIN_PREFIX + player.getDisplayName() + " §7hat den Server §abetreten§7!");

        String uuid = player.getUniqueId().toString();

        // 1. Prüfen, ob der Spieler schon in der Config steht
        if (!Skyblock.getInstance().getConfig().contains("players." + uuid)) {

            // 2. Nächste freie Position berechnen
            // Wir holen uns die aktuelle Insel-Anzahl aus der Config (Standard 0)
            int islandCount = Skyblock.getInstance().getConfig().getInt("island-count", 0);

            // Wir versetzen jede Insel um 100 Blöcke, damit sie sich nicht sehen
            List<Location> islandLocs = getIslandLocations(player.getWorld());
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

    public List<Location> getIslandLocations(World world) {
        List<Location> locs = new ArrayList<>();

        locs.add(new Location(world, 0, 67, 100));
        locs.add(new Location(world, 100, 67, -100));
        locs.add(new Location(world, 100, 67, 100));
        locs.add(new Location(world, -100, 67, -100));
        locs.add(new Location(world, 0, 67, -100));
        locs.add(new Location(world, -100, 67, 100));
        locs.add(new Location(world, -100, 67, 0));
        locs.add(new Location(world, 100, 67, 0));
        locs.add(new Location(world, 0, 67, 200));
        locs.add(new Location(world, 200, 67, 0));
        locs.add(new Location(world, -200, 67, 0));
        locs.add(new Location(world, 0, 67, 200));
        locs.add(new Location(world, 100, 67, 200));
        locs.add(new Location(world, -100, 67, -200));
        locs.add(new Location(world, 100, 67, -200));
        locs.add(new Location(world, -100, 67, 200));

        return locs;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        e.setQuitMessage(Skyblock.PLUGIN_PREFIX + player.getDisplayName() + " §7hat den Server §cverlassen§7!");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Wir prüfen die letzte Schadensursache
        if (player.getLastDamageCause() != null &&
                player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {

            // "Ohne Fremdeinwirkung" bedeutet: es gibt keinen Mörder (Killer)
            if (player.getKiller() == null) {

                // Deine eigene Nachricht setzen
                String playerName = player.getName();
                event.setDeathMessage("§e" + playerName + " §7wollte mal sehen, wie tief das Loch ist... §8(Void)");

                // Alternativ: Eine Liste von zufälligen Sprüchen
                // String[] messages = {" hat die Schwerkraft getestet.", " dachte, er könne fliegen.", " ist im Nichts verschwunden."};
                // event.setDeathMessage("§e" + playerName + messages[new Random().nextInt(messages.length)]);
            }
        }
    }

    @EventHandler
    public void onVillagerHit(EntityDamageByEntityEvent event) {
        // Prüfen, ob das Opfer ein Villager ist
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();

            // Nur wenn es unser Shop-Villager ist (über den Namen oder Tag)
            if (villager.getCustomName() != null && villager.getCustomName().contains("Shop")) {
                event.setCancelled(true); // Schlag wird komplett ignoriert
            }
        }
    }

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
