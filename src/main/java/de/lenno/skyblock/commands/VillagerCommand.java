package de.lenno.skyblock.commands;

import de.lenno.skyblock.Skyblock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class VillagerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, @NonNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = (Player) commandSender;
        p.sendMessage(Skyblock.PLUGIN_PREFIX + "§3ES SOLLTE EIN VILLAGER SPAWNEN!");
        spawnShopVillager(p.getLocation());
        return false;
    }

    public void spawnShopVillager(Location loc) {
        // 1. Villager spawnen
        Villager villager = loc.getWorld().spawn(loc, Villager.class);

        // 2. Villager "einfrieren" und anpassen
        villager.setCustomName("§e§lSkyblock Shop");
        villager.setCustomNameVisible(true);
        villager.setAI(false);               // Bewegt sich nicht
        villager.setSilent(true);            // MACHT KEINE GERÄUSCHE
        villager.setInvulnerable(true);      // KANN NICHT GEHITTET/GETÖTET WERDEN
        villager.setCollidable(false);       // Spieler können ihn nicht herumschubsen
        villager.setRemoveWhenFarAway(false); // Verhindert, dass er despawnt
        villager.setProfession(Villager.Profession.LIBRARIAN);
        villager.setVillagerType(Villager.Type.SNOW);

        // 3. Trades erstellen
        List<MerchantRecipe> trades = getTradingRecipes();

        // 4. Trades dem Villager geben
        villager.setRecipes(trades);
    }

    public List<MerchantRecipe> getTradingRecipes() {
        List<MerchantRecipe> trades = new ArrayList<>();
        MerchantRecipe trade1 = new MerchantRecipe(new ItemStack(Material.ELYTRA, 1), 9999); // 9999 = unendlich oft nutzbar
        trade1.addIngredient(createCoin(64));
        trades.add(trade1);

        // BEISPIEL TRADE 2: 64x Cobblestone gegen 1x Eisen (mit Glow-Effekt von vorhin)
        MerchantRecipe trade2 = new MerchantRecipe(createCoin(1), 9999);
        trade2.addIngredient(new ItemStack(Material.EMERALD_BLOCK, 16));
        trades.add(trade2);

        return trades;
    }

    public ItemStack createCoin(int amount) {
        ItemStack coin = new ItemStack(Material.RAW_GOLD, amount);

        ItemMeta meta = coin.getItemMeta();
        if (meta != null) {
            meta.setEnchantmentGlintOverride(true);
            meta.setDisplayName("§6COIN");
            coin.setItemMeta(meta);
        }

        return coin;
    }
}
