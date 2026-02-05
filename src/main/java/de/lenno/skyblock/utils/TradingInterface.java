package de.lenno.skyblock.utils;

import de.lenno.skyblock.commands.VillagerType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TradingInterface {

    public static void spawnTradingVillager(Location loc, VillagerType type) {

        String name = getVillagerName(type);
        Villager.Profession profession = getVillagerProfession(type);
        List<MerchantRecipe> trades = getTradingRecipes(type);

        Villager villager = loc.getWorld().spawn(loc, Villager.class);

        // 2. Villager "einfrieren" und anpassen
        villager.setCustomNameVisible(true);
        villager.setAI(false);               // Bewegt sich nicht
        villager.setSilent(true);            // MACHT KEINE GERÄUSCHE
        villager.setInvulnerable(true);      // KANN NICHT GEHITTET/GETÖTET WERDEN
        villager.setCollidable(false);       // Spieler können ihn nicht herumschubsen
        villager.setRemoveWhenFarAway(false); // Verhindert, dass er despawnt

        // Individuelle Eigenschaften pro Typ
        villager.setCustomName(name);
        villager.setProfession(profession);
        villager.setVillagerType(Villager.Type.TAIGA);

        // Trades dem Villager geben
        villager.setRecipes(trades);
    }

    private static String getVillagerName(VillagerType type) {
        if (type == VillagerType.MÜNZMEISTER) {
            return "§6Münzmeister";
        }
        if (type == VillagerType.BOTANIKER) {
            return "§fBotaniker";
        }
        if (type == VillagerType.ZOOLOGE) {
            return "§bZoologe";
        }
        if (type == VillagerType.ARCHÄOLOGE) {
            return "§cArchäologe";
        }
        return "";
    }

    private static List<MerchantRecipe> getTradingRecipes(VillagerType type) {
        List<MerchantRecipe> recipes = new ArrayList<>();
        if (type == VillagerType.MÜNZMEISTER) {
            // 16 Emeralds -> Silvercoin
            MerchantRecipe silverCoin = new MerchantRecipe(createSilverCoin(1), 9999);
            silverCoin.addIngredient(new ItemStack(Material.EMERALD, 16));
            recipes.add(silverCoin);

            // 16 Emeraldblocks -> Goldcoin
            MerchantRecipe goldCoinEmeralds = new MerchantRecipe(createGoldCoin(1), 9999);
            goldCoinEmeralds.addIngredient(new ItemStack(Material.EMERALD_BLOCK, 16));
            recipes.add(goldCoinEmeralds);

            // 16 Emeraldblocks -> Goldcoin
            MerchantRecipe goldCoinGold = new MerchantRecipe(createGoldCoin(1), 9999);
            goldCoinGold.addIngredient(new ItemStack(Material.GOLD_BLOCK, 16));
            recipes.add(goldCoinGold);
        }
        if (type == VillagerType.BOTANIKER) {
            // 1 Silvercoin -> Brown Mushroom
            MerchantRecipe brownMushroom = new MerchantRecipe(new ItemStack(Material.BROWN_MUSHROOM), 9999);
            brownMushroom.addIngredient(createSilverCoin(1));
            recipes.add(brownMushroom);

            // 1 Silvercoin -> Jungle Sapling
            MerchantRecipe jungleSapling = new MerchantRecipe(new ItemStack(Material.JUNGLE_SAPLING), 9999);
            jungleSapling.addIngredient(createSilverCoin(1));
            recipes.add(jungleSapling);

            // 1 Silvercoin -> Cherry Sapling
            MerchantRecipe cherrySapling = new MerchantRecipe(new ItemStack(Material.CHERRY_SAPLING), 9999);
            cherrySapling.addIngredient(createSilverCoin(1));
            recipes.add(cherrySapling);

            // 4 Silvercoins -> 4 Dark oak sapling
            MerchantRecipe darkOakSaplings = new MerchantRecipe(new ItemStack(Material.DARK_OAK_SAPLING, 4), 9999);
            darkOakSaplings.addIngredient(createSilverCoin(4));
            recipes.add(darkOakSaplings);

            // 4 Silvercoins -> 4 Pale oak sapling
            MerchantRecipe palmtreeSapling = new MerchantRecipe(new ItemStack(Material.PALE_OAK_SAPLING, 4), 9999);
            palmtreeSapling.addIngredient(createSilverCoin(4));
            recipes.add(palmtreeSapling);

            // 8 Silvercoins -> 1 flowerin azalea
            MerchantRecipe floweringAzalea = new MerchantRecipe(new ItemStack(Material.FLOWERING_AZALEA), 9999);
            floweringAzalea.addIngredient(createSilverCoin(8));
            recipes.add(floweringAzalea);
        }
        if (type == VillagerType.ZOOLOGE) {
            // Alle Standard-Eier für 1 Gold-Coin
            Material[] basicEggs = {
                    Material.CAMEL_SPAWN_EGG, Material.FROG_SPAWN_EGG,
                    Material.TURTLE_SPAWN_EGG, Material.POLAR_BEAR_SPAWN_EGG,
                    Material.AXOLOTL_SPAWN_EGG, Material.FOX_SPAWN_EGG,
                    Material.PANDA_SPAWN_EGG
            };

            for (Material eggMaterial : basicEggs) {
                MerchantRecipe recipe = new MerchantRecipe(new ItemStack(eggMaterial), 9999);
                recipe.addIngredient(createGoldCoin(1));
                recipes.add(recipe);
            }

            // Armadillo Spawn Egg für 8 Gold-Coins
            MerchantRecipe armadillo = new MerchantRecipe(new ItemStack(Material.ARMADILLO_SPAWN_EGG), 9999);
            armadillo.addIngredient(createGoldCoin(4));
            recipes.add(armadillo);
        }
        if (type == VillagerType.ARCHÄOLOGE) {
            // 1 Silver-Coin -> 2 Suspicious Sand / Gravel
            MerchantRecipe dirtSand = new MerchantRecipe(new ItemStack(Material.SAND), 9999);
            dirtSand.addIngredient(new ItemStack(Material.DIRT));
            recipes.add(dirtSand);

            // 2 CobbleStone -> 1 Cobbled deepslae
            MerchantRecipe deepslate = new MerchantRecipe(new ItemStack(Material.COBBLED_DEEPSLATE, 1), 9999);
            deepslate.addIngredient(new ItemStack(Material.COBBLESTONE, 2));
            recipes.add(deepslate);

            // 1 Silver-Coin -> 2 Suspicious Sand / Gravel
            MerchantRecipe susSand = new MerchantRecipe(new ItemStack(Material.SUSPICIOUS_SAND, 1), 9999);
            susSand.addIngredient(createSilverCoin(1));
            recipes.add(susSand);

            MerchantRecipe susGravel = new MerchantRecipe(new ItemStack(Material.SUSPICIOUS_GRAVEL, 1), 9999);
            susGravel.addIngredient(createSilverCoin(1));
            recipes.add(susGravel);

            // Wertvolle Ressourcen
            // 2 Gold-Coins -> 1 Diamant
            MerchantRecipe diamond = new MerchantRecipe(new ItemStack(Material.DIAMOND), 9999);
            diamond.addIngredient(createGoldCoin(2));
            recipes.add(diamond);

            // 4 Gold-Coins -> 1 Shulker Shell
            MerchantRecipe shulker = new MerchantRecipe(new ItemStack(Material.SHULKER_SHELL), 9999);
            shulker.addIngredient(createGoldCoin(4));
            recipes.add(shulker);

            // PointedDripstone
            MerchantRecipe pointedDrip = new MerchantRecipe(new ItemStack(Material.POINTED_DRIPSTONE), 9999);
            pointedDrip.addIngredient(createGoldCoin(1));
            recipes.add(pointedDrip);

            // Dripstone block
            MerchantRecipe dripBlock = new MerchantRecipe(new ItemStack(Material.DRIPSTONE_BLOCK), 9999);
            dripBlock.addIngredient(createGoldCoin(4));
            recipes.add(dripBlock);

            // High-End Loot
            // 2 Gold-Coins -> 1 Netherite Scrap
            MerchantRecipe netherite = new MerchantRecipe(new ItemStack(Material.NETHERITE_SCRAP), 9999);
            netherite.addIngredient(createGoldCoin(2));
            recipes.add(netherite);

            // 8 Gold-Coins - 1 Budding Amethyst (Der Block, an dem Kristalle wachsen)
            MerchantRecipe amethyst = new MerchantRecipe(new ItemStack(Material.BUDDING_AMETHYST), 9999);
            amethyst.addIngredient(createGoldCoin(8));
            recipes.add(amethyst);

            // 64 Gold-Coins -> 1 Elytra
            MerchantRecipe elytra = new MerchantRecipe(new ItemStack(Material.ELYTRA), 9999);
            elytra.addIngredient(createGoldCoin(64));
            recipes.add(elytra);
        }
        return recipes;
    }

    private static Villager.Profession getVillagerProfession(VillagerType type) {
        if (type == VillagerType.MÜNZMEISTER) {
            return Villager.Profession.LIBRARIAN;
        }
        if (type == VillagerType.BOTANIKER) {
            return Villager.Profession.FARMER;
        }
        if (type == VillagerType.ZOOLOGE) {
            return Villager.Profession.SHEPHERD;
        }
        if (type == VillagerType.ARCHÄOLOGE) {
            return Villager.Profession.CARTOGRAPHER;
        }
        return Villager.Profession.NONE;
    }

    public static ItemStack createGoldCoin(int amount) {
        ItemStack coin = new ItemStack(Material.RAW_GOLD, amount);

        ItemMeta meta = coin.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            meta.setDisplayName("§6Goldmünze");
            coin.setItemMeta(meta);
        }

        return coin;
    }

    public static ItemStack createSilverCoin(int amount) {
        ItemStack coin = new ItemStack(Material.RAW_IRON, amount);

        ItemMeta meta = coin.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            meta.setDisplayName("§7Silbermünze");
            coin.setItemMeta(meta);
        }

        return coin;
    }
}
