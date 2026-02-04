package de.lenno.skyblock.commands;

import de.lenno.skyblock.IslandManager;
import de.lenno.skyblock.Skyblock;
import kotlin.collections.ArrayDeque;
import org.bukkit.*;
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

        createTestIsland(p.getLocation());

        IslandManager.createNetherIsland(p.getLocation().add(0, 0, 10), Axis.Z);
        IslandManager.createNetherIsland(p.getLocation().add(10, 0, 0), Axis.X);

        return true;
    }

    public void createTestIsland(Location center) {
        int size = 100;
        int halfSize = size / 2;

        // Wir loopen durch ein 100x100 Quadrat
        for (int x = -halfSize; x < halfSize; x++) {
            for (int z = -halfSize; z < halfSize; z++) {
                // Wir setzen die Insel auf die Höhe des Centers
                Location loc = center.clone().add(x, 0, z);
                loc.getBlock().setType(Material.GRASS_BLOCK);

                // Optional: Setze darunter Erde, damit es wie eine echte Insel aussieht
                loc.clone().subtract(0, 1, 0).getBlock().setType(Material.DIRT);
                loc.clone().subtract(0, 2, 0).getBlock().setType(Material.DIRT);
            }
        }
        Bukkit.broadcastMessage("§a100x100 Insel wurde generiert!");
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
