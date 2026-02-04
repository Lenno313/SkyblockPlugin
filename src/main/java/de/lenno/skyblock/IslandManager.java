package de.lenno.skyblock;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Orientable;
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

    private static List<ItemStack> getBlazeChestItems() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack cocoaBean = new ItemStack(Material.COCOA_BEANS);
        ItemStack beetrootSeed = new ItemStack(Material.BEETROOT_SEEDS);
        ItemStack bamboo = new ItemStack(Material.BAMBOO);
        items.add(cocoaBean);
        items.add(beetrootSeed);
        items.add(bamboo);
        return items;
    }

    private static List<ItemStack> getNetherChestItems() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack brownMush = new ItemStack(Material.BROWN_MUSHROOM);
        ItemStack redMush = new ItemStack(Material.RED_MUSHROOM);
        ItemStack sweetBerry = new ItemStack(Material.SWEET_BERRIES);
        items.add(brownMush);
        items.add(redMush);
        items.add(sweetBerry);
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
    }

    public static void createNetherIsland(Location loc, Axis axis) {
        int width = (axis == Axis.X) ? 4 : 5; // Das Portal ist immer 4 breit
        int depth = (axis == Axis.X) ? 5 : 4; // Das Portal ist immer 5 tief

        for (int x = -2; x < -2 + width; x++) {
            for (int z = -2; z < -2 + depth; z++) {
                for (int y = -3; y <= -1; y++) {
                    Location current = loc.clone().add(x, y, z);

                    if (y < -1) {
                        current.getBlock().setBlockData(Material.NETHERRACK.createBlockData());
                        continue;
                    }

                    if (axis == Axis.X) {
                        if (z == 1 && x == 1) {
                            current.getBlock().setBlockData(Material.SOUL_SAND.createBlockData());
                            current.clone().add(0, 1, 0).getBlock().setBlockData(Material.NETHER_WART.createBlockData());
                            continue;
                        }
                        if (z == -1 && x == -2) {
                            current.getBlock().setBlockData(Material.SOUL_SOIL.createBlockData());
                            continue;
                        }
                        Material blockMat = (z < 0) ? Material.WARPED_NYLIUM : Material.CRIMSON_NYLIUM;

                        current.getBlock().setBlockData(blockMat.createBlockData());
                    } else {
                        if (z == 1 && x == 1) {
                            current.getBlock().setBlockData(Material.SOUL_SAND.createBlockData());
                            current.clone().add(0, 1, 0).getBlock().setBlockData(Material.NETHER_WART.createBlockData());
                            continue;
                        }
                        if (z == -2 && x == -1) {
                            current.getBlock().setBlockData(Material.SOUL_SOIL.createBlockData());
                            continue;
                        }
                        Material blockMat = (x < 0) ? Material.WARPED_NYLIUM : Material.CRIMSON_NYLIUM;

                        current.getBlock().setBlockData(blockMat.createBlockData());
                    }
                }
            }
        }

        //Location portalLoc = (axis == Axis.X) ? loc.clone().add(0, 0, 1) : loc.clone().add(1, 0, 0);
        buildPortalFrame(loc, axis);

        List<ItemStack> items = getNetherChestItems();
        Location chestLoc;
        if (axis == Axis.X) {
            chestLoc = loc.clone().add(1, 0, 2);
        } else {
            chestLoc = loc.clone().add(2, 0, 1);
        }
        chestLoc.getBlock().setType(Material.CHEST);
        Directional directional = (Directional) chestLoc.getBlock().getBlockData();

        if (axis == Axis.X) {
            directional.setFacing(BlockFace.WEST);
        } else {
            directional.setFacing(BlockFace.NORTH);
        }

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
        spawnerIslandLoc.setX(loc.getBlockX() - Integer.signum(loc.getBlockX()) * 10);
        spawnerIslandLoc.setZ(loc.getBlockZ() - Integer.signum(loc.getBlockZ()) * 10);
        generateBlazeIsland(spawnerIslandLoc);
    }

    public static void buildPortalFrame(Location loc, Axis axis) {
        World world = loc.getWorld();

        List<Location> obsidianLocs = new ArrayList<>();
        List<Location> portalLocs = new ArrayList<>();
        if (axis == Axis.X) {
            // linke seite
            obsidianLocs.add(loc.clone().add(1, 0, 0));
            obsidianLocs.add(loc.clone().add(1, 1, 0));
            obsidianLocs.add(loc.clone().add(1, 2, 0));
            // Boden
            obsidianLocs.add(loc.clone().add(1, -1, 0));
            obsidianLocs.add(loc.clone().add(0, -1, 0));
            obsidianLocs.add(loc.clone().add(-1, -1, 0));
            obsidianLocs.add(loc.clone().add(-2, -1, 0));
            // rechte
            obsidianLocs.add(loc.clone().add(-2, 0, 0));
            obsidianLocs.add(loc.clone().add(-2, 1, 0));
            obsidianLocs.add(loc.clone().add(-2, 2, 0));
            // Decke
            obsidianLocs.add(loc.clone().add(1, 3, 0));
            obsidianLocs.add(loc.clone().add(0, 3, 0));
            obsidianLocs.add(loc.clone().add(-1, 3, 0));
            obsidianLocs.add(loc.clone().add(-2, 3, 0));
            // Portal
            portalLocs.add(loc.clone().add(0, 0, 0));
            portalLocs.add(loc.clone().add(-1, 0, 0));
            portalLocs.add(loc.clone().add(0, 1, 0));
            portalLocs.add(loc.clone().add(-1, 1, 0));
            portalLocs.add(loc.clone().add(0, 2, 0));
            portalLocs.add(loc.clone().add(-1, 2, 0));
        } else{
            // linke seite
            obsidianLocs.add(loc.clone().add(0, 0, 1));
            obsidianLocs.add(loc.clone().add(0, 1, 1));
            obsidianLocs.add(loc.clone().add(0, 2, 1));
            // Boden
            obsidianLocs.add(loc.clone().add(0, -1, 1));
            obsidianLocs.add(loc.clone().add(0, -1, 0));
            obsidianLocs.add(loc.clone().add(0, -1, -1));
            obsidianLocs.add(loc.clone().add(0, -1, -2));
            // rechte
            obsidianLocs.add(loc.clone().add(0, 0, -2));
            obsidianLocs.add(loc.clone().add(0, 1, -2));
            obsidianLocs.add(loc.clone().add(0, 2, -2));
            // Decke
            obsidianLocs.add(loc.clone().add(0, 3, 1));
            obsidianLocs.add(loc.clone().add(0, 3, 0));
            obsidianLocs.add(loc.clone().add(0, 3, -1));
            obsidianLocs.add(loc.clone().add(0, 3, -2));
            // Portal
            portalLocs.add(loc.clone().add(0, 0, 0));
            portalLocs.add(loc.clone().add(0, 0, -1));
            portalLocs.add(loc.clone().add(0, 1, 0));
            portalLocs.add(loc.clone().add(0, 1, -1));
            portalLocs.add(loc.clone().add(0, 2, 0));
            portalLocs.add(loc.clone().add(0, 2, -1));
        }

        for (Location obsidianLoc :  obsidianLocs) {
            obsidianLoc.getBlock().setType(Material.OBSIDIAN);
        }
        for (Location portalLoc :  portalLocs) {
            portalLoc.getBlock().setType(Material.NETHER_PORTAL);
            Orientable data = (Orientable) portalLoc.getBlock().getBlockData();
            data.setAxis(axis);
            portalLoc.getBlock().setBlockData(data);
        }
    }

    public static void generateBlazeIsland(Location loc) {
        Location chestLoc = loc.clone().add(-2, 0, 2);
        Location blazeSpawner = loc.clone();

        for (int y = -1; y <= 0; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {

                    Location current = loc.clone().add(x, y, z);

                    if (y == 0) {
                        if (x == 2 || x == -2 || z == 2 || z == -2) {
                            current.getBlock().setBlockData(Material.NETHER_BRICK_FENCE.createBlockData());
                        }
                    } else {
                        current.getBlock().setBlockData(Material.NETHER_BRICKS.createBlockData());
                    }
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
        List<ItemStack> items = getBlazeChestItems();

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
        locs.add(new Location(world, 0, 67, -100));
        locs.add(new Location(world, 100, 67, -100));
        locs.add(new Location(world, 100, 67, 100));
        locs.add(new Location(world, -100, 67, -100));
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
