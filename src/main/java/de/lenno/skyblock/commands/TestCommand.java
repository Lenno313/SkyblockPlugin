package de.lenno.skyblock.commands;

import de.lenno.skyblock.Skyblock;
import kotlin.collections.ArrayDeque;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, @NonNull String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = (Player) commandSender;
        p.sendMessage(Skyblock.PLUGIN_PREFIX + "§3Jo! Dat hat geklappt :)");

        Location loc = p.getLocation();

        ItemStack ice = new ItemStack(Material.ICE);
        ItemStack lavaBucket = new ItemStack(Material.LAVA_BUCKET);
        List<ItemStack> items = new ArrayList<>();
        items.add(ice);
        items.add(lavaBucket);
        generateIsland(loc, items);

        giveItem(p);

        return true;
    }

    public static void giveItem(Player p) {
        ItemStack coin = new ItemStack(Material.RAW_GOLD);

        ItemMeta meta = coin.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            meta.setDisplayName("§6COIN");
            coin.setItemMeta(meta);
        }
        p.getInventory().addItem(coin);
    }

    public static void generateIsland(Location loc, List<ItemStack> items) {
        // 1. Die Schichten festlegen (Relativ zum Spawnpunkt)
        // y = -3: Erde
        // y = -2: Erde
        // y = -1: Gras
        // Spawnpunkt des Spielers ist dann bei y = 0

        Location locBedrock = loc.clone().add(0, -3, 0);
        Location treeLoc = loc.clone().add(4, 0, 0);
        Location chestLoc = loc.clone().add(0, 0, 4);

        for (int y = -3; y <= -1; y++) {
            // Wir ziehen beide Schenkel 5 Blöcke weit vom Rand der 3x3 Ecke weg.
            // Die Ecke geht von -1 bis 1 (3 breit), die Schenkel bis 6.
            for (int x = -1; x <= 4; x++) {
                for (int z = -1; z <= 4; z++) {

                    // L-Form Logik: Nur bauen, wenn wir uns im "3-breiten" Korridor befinden
                    boolean inXSchenkel = z <= 1;
                    boolean inZSchenkel = x <= 1;

                    if (inXSchenkel || inZSchenkel) {
                        Location current = loc.clone().add(x, y, z);

                        if (y == -1) {
                            current.getBlock().setType(Material.GRASS_BLOCK);
                        } else {
                            current.getBlock().setType(Material.DIRT);
                        }
                    }
                }
            }
        }
        locBedrock.getBlock().setType(Material.BEDROCK);

        // Sicherstellen, dass dort Luft ist, bevor der Baum kommt
        treeLoc.getBlock().setType(Material.AIR);

        // world.generateTree gibt true zurück, wenn der Baum Platz hatte
        boolean treeSpawned = loc.getWorld().generateTree(treeLoc, TreeType.TREE);

        // Falls der Baum wegen Platzmangel nicht spawnt, erzwingen wir eine kleine Eiche
        if (!treeSpawned) {
            loc.getWorld().generateTree(treeLoc, org.bukkit.TreeType.BIG_TREE);
        }

        chestLoc.getBlock().setType(Material.CHEST);

        // Kiste mit Inhalt füllen
        if (chestLoc.getBlock().getState() instanceof org.bukkit.block.Chest chest) {

            // Sicherstellen, dass die Liste nicht leer ist
            if (items != null && !items.isEmpty()) {
                for (ItemStack item : items) {
                    if (item != null) {
                        chest.getInventory().addItem(item);
                    }
                }
            }
        }

        generateSandIsland(loc.clone().add(-35, 0, 0));
    }

    public static void generateSandIsland(Location loc) {
        Location chestLoc = loc.clone().add(-1, 0, 1);
        Location cactusLog = loc.clone().add(1, 0, -1);

        for (int y = -3; y <= -1; y++) {
            // Wir ziehen beide Schenkel 5 Blöcke weit vom Rand der 3x3 Ecke weg.
            // Die Ecke geht von -1 bis 1 (3 breit), die Schenkel bis 6.
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {

                    Location current = loc.clone().add(x, y, z);

                    current.getBlock().setBlockData(Material.SAND.createBlockData(), false);
                }
            }
        }

        cactusLog.getBlock().setType(Material.CACTUS);

        chestLoc.getBlock().setType(Material.CHEST);
        ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 10);
        ItemStack ice = new ItemStack(Material.ICE);
        ItemStack birchSapling = new ItemStack(Material.MELON_SEEDS);
        ItemStack pumpkinSeeds = new ItemStack(Material.PUMPKIN_SEEDS);

        // Einen zufälligen Sapling aus: birch, Spruce, Acacia, cherry
        ItemStack melonSeeds = new ItemStack(Material.BIRCH_SAPLING);
        List<ItemStack> items = new ArrayList<>();
        items.add(obsidian);
        items.add(ice);
        items.add(birchSapling);
        items.add(pumpkinSeeds);
        items.add(melonSeeds);

        Directional directional = (Directional) chestLoc.getBlock().getBlockData();

        // 3. Die Blickrichtung setzen (NORTH, EAST, SOUTH, WEST)
        directional.setFacing(BlockFace.EAST);

        // 4. Die geänderte BlockData zurück an den Block geben
        chestLoc.getBlock().setBlockData(directional);

        // Kiste mit Inhalt füllen
        if (chestLoc.getBlock().getState() instanceof org.bukkit.block.Chest chest) {

            for (ItemStack item : items) {
                if (item != null) {
                    chest.getInventory().addItem(item);
                }
            }
        }
    }
}
