package de.lenno.skyblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.Directional;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class IslandManager {

    private static List<ItemStack> getBaseChestItems() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack infoBook = createBookItem();
        ItemStack ice = new ItemStack(Material.ICE);
        ItemStack lavaBucket = new ItemStack(Material.LAVA_BUCKET);
        items.add(infoBook);
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

    private static ItemStack createBookItem() {
        // 1. Ein beschriebenes Buch erstellen
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, true);

        // 2. Die Verzauberung im Interface verstecken
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        // 3. Den Namen "Wissen" in die Lore schreiben
        meta.setLore(java.util.Arrays.asList(
                "§9Wissen III"
        ));

        if (meta != null) {
            // 2. Titel und Autor setzen
            meta.setTitle("§6Skyblock");
            meta.setAuthor("CBT & Lenno");

            // 3. Seiten hinzufügen
            meta.addPage(
                    "§l§7Willkommen bei§r\n" +
                            "§6§lSkyblock +§r\n\n" +
                            "§7Wir haben uns einige Gedanken gemacht, wie wir das Ganze hier etwas" +
                            "§e§laufregender §7und vor allem §a§labwechslungsreicher §7gestalten können.\n\n" +
                            "§7Uns war es dabei wichtig alles möglichst §eVanilla §7zu halten!\n" +
                            "§7Hier fassen wir dir einmal zusammen, was wir dennoch angepasst haben.\n"
            );

            meta.addPage(
                    "§l§c'Neue' Items§r\n\n" +
                            "§7Wir haben Wege eingeführt, um an Blöcke & Items zu kommen, die sonst in Skyblock §cnicht erreichbar §7wären.\n" +
                            "§7Diese befinden sich teilweise in den §6Kisten §7auf euren Inseln, manche müssen aber" +
                            "auch §aerspielt §7bzw. §eerworben §7werden.\n" +
                            "§7Manche benötigen auch einiges an Glück beim §bAbbauen §7oder §1Angeln§7."
            );

            meta.addPage(
                    "§l§2Biome§r\n\n" +
                            "§7Wir haben in der gesamten Oberwelt das §aPlains-Biom§7.\n" +
                            "§7Im Nether ist überall das §cNether-Wastes-Biom§7.\n\n" +
                            "§l§8Tipp: §r§7Ab Höhe 175 könnte es langsam etwas §akälter §7werden...!"
            );

            meta.addPage(
                    "§l§4Nether§r\n\n" +
                            "§7Wenn du das §lERSTE §7mal auf deiner Insel in den §cNether §7gehst, wird dir eine §eStart-Insel §7erzeugt.\n" +
                            "§7Dafür musst du jedoch §cmindestens 40 Blöcke §7von anderen Portal entfernt sein.\n\n" +
                            "§7Wenn du Nether §cverlässt§7, gelangst du dabei immer zu deiner §aStart-Insel §7bzw. deinem §e(Re)Spawn-Punkt§7."
            );

            meta.addPage(
                    "§l§aBesondere Mobs§r\n\n" +
                            "§7Wir haben eine Möglichkeit eingebaut §3Guardians §7und §8Wither-Skelette §7spawnen zu lassen.\n" +
                            "§7Guardians können im Wasser über jeglicher Art von §3Prismarine-Blöcken §7spawnen.\n" +
                            "§7Wither-Skelette können im Nether auf §4Nether-Bricks §7spawnen.\n\n" +
                            "§l§8Tipp: §r§7Es soll dafür wohl neue Schätze beim Angeln geben. Und schau dazu dir mal den" +
                            "§c'Basalt-Generator' §7im Nether an."
            );

            // 4. Meta zurückgeben und Buch dem Spieler geben
            book.setItemMeta(meta);
        }
        return book;
    }
}
