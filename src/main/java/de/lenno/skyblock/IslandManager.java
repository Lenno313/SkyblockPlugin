package de.lenno.skyblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IslandManager {

    private static List<ItemStack> getBaseChestItems() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack ice = new ItemStack(Material.ICE);
        ItemStack lavaBucket = new ItemStack(Material.LAVA_BUCKET);
        items.add(ice);
        items.add(lavaBucket);
        return items;
    }

    private static List<ItemStack> getSandChestItems() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 10);
        ItemStack ice = new ItemStack(Material.ICE);
        ItemStack birchSapling = new ItemStack(Material.MELON_SEEDS);
        ItemStack pumpkinSeeds = new ItemStack(Material.PUMPKIN_SEEDS);
        ItemStack sweetBerryBush = new ItemStack(Material.SWEET_BERRY_BUSH);
        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE);

        double value = Math.random();
            Material saplingMaterial = null;
        if (value < 0.33) {
            saplingMaterial = Material.BIRCH_SAPLING;
        } else if (value < 0.66) {
            saplingMaterial = Material.SPRUCE_SAPLING;
        } else {
            saplingMaterial = Material.ACACIA_SAPLING;
        }

        ItemStack sapling = new ItemStack(saplingMaterial);
        items.add(obsidian);
        items.add(ice);
        items.add(birchSapling);
        items.add(pumpkinSeeds);
        items.add(sapling);
        items.add(sweetBerryBush);
        items.add(sugarCane);
        return items;
    }

    private static List<ItemStack> getNetherChestItems() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack brownMush = new ItemStack(Material.BROWN_MUSHROOM);
        ItemStack redMush = new ItemStack(Material.RED_MUSHROOM);
        ItemStack cocoaBean = new ItemStack(Material.COCOA_BEANS);
        ItemStack beetrootSeed = new ItemStack(Material.BEETROOT_SEEDS);
        ItemStack bamboo = new ItemStack(Material.BAMBOO);
        items.add(brownMush);
        items.add(redMush);
        items.add(cocoaBean);
        items.add(beetrootSeed);
        items.add(bamboo);
        return items;
    }

    public static void generateIsland(Location loc) {
        List<ItemStack> items = getBaseChestItems();

        Location locBedrock = loc.clone().add(0, -3, 0);
        Location treeLoc = loc.clone().add(3, 0, 0);
        Location chestLoc = loc.clone().add(0, 0, 4);

        for (int y = -3; y <= -1; y++) {
            for (int x = -1; x <= 4; x++) {
                for (int z = -1; z <= 4; z++) {
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
        treeLoc.getBlock().setType(Material.AIR);

        boolean treeSpawned = loc.getWorld().generateTree(treeLoc, TreeType.TREE);

        if (!treeSpawned) {
            loc.getWorld().generateTree(treeLoc, org.bukkit.TreeType.BIG_TREE);
        }

        chestLoc.getBlock().setType(Material.CHEST);

        if (chestLoc.getBlock().getState() instanceof org.bukkit.block.Chest chest) {
            if (items != null && !items.isEmpty()) {
                for (ItemStack item : items) {
                    if (item != null) {
                        chest.getInventory().addItem(item);
                    }
                }
            }
        }

        Location sandIslandLoc = loc.clone();
        sandIslandLoc.setX(loc.getBlockX() - Integer.signum(loc.getBlockX()) * 35);
        sandIslandLoc.setZ(loc.getBlockZ() - Integer.signum(loc.getBlockZ()) * 35);
        generateSandIsland(sandIslandLoc);
    }

    public static void generateSandIsland(Location loc) {
        Location chestLoc = loc.clone().add(-1, 0, 1);
        Location cactusLog = loc.clone().add(1, 0, -1);

        for (int y = -3; y <= -1; y++) {
            for (int x = -2; x < 2; x++) {
                for (int z = -1; z <= 1; z++) {

                    Location current = loc.clone().add(x, y, z);

                    current.getBlock().setBlockData(Material.NETHERRACK.createBlockData(), false);
                }
            }
        }

        cactusLog.getBlock().setType(Material.CACTUS);

        chestLoc.getBlock().setType(Material.CHEST);

        // Einen zufälligen Sapling aus: birch, Spruce, Acacia, cherry
        List<ItemStack> items = getSandChestItems();

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
    public static void generateNetherIsland(Location loc) {
        Location chestLoc = loc.clone().add(-1, 0, 1);
        Location cactusLog = loc.clone().add(1, 0, -1);

        for (int y = -3; y <= -1; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {

                    Location current = loc.clone().add(x, y, z);

                    current.getBlock().setBlockData(Material.SAND.createBlockData(), false);
                }
            }
        }

        cactusLog.getBlock().setType(Material.CACTUS);

        chestLoc.getBlock().setType(Material.CHEST);

        // Einen zufälligen Sapling aus: birch, Spruce, Acacia, cherry
        List<ItemStack> items = getSandChestItems();

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

        Location spawnerIslandLoc = loc.clone();
        spawnerIslandLoc.setX(loc.getBlockX() - Integer.signum(loc.getBlockX()) * 35);
        spawnerIslandLoc.setZ(loc.getBlockZ() - Integer.signum(loc.getBlockZ()) * 35);
        generateBlazeIsland(spawnerIslandLoc);
    }

    public static void generateBlazeIsland(Location loc) {
        Location chestLoc = loc.clone().add(-1, 0, 1);
        Location blazeSpawner = loc.clone().add(1, 0, -1);

        for (int y = -3; y <= -1; y++) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {

                    Location current = loc.clone().add(x, y, z);

                    current.getBlock().setBlockData(Material.NETHER_BRICKS.createBlockData(), false);
                }
            }
        }

        blazeSpawner.getBlock().setType(Material.SPAWNER);
        if (blazeSpawner.getBlock().getState() instanceof CreatureSpawner spawner) {
            spawner.setSpawnedType(EntityType.BLAZE);
            spawner.update();
        }

        chestLoc.getBlock().setType(Material.CHEST);

        // Einen zufälligen Sapling aus: birch, Spruce, Acacia, cherry
        List<ItemStack> items = getSandChestItems();

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

    public static List<Location> getIslandLocations(World world) {
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
}
