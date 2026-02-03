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
}
