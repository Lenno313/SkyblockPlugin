package de.lenno.skyblock.commands;

import de.lenno.skyblock.Skyblock;
import de.lenno.skyblock.utils.TradingInterface;
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
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, @NonNull String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player p = (Player) commandSender;
        if (!p.isOp()) {
            p.sendMessage(Skyblock.PLUGIN_PREFIX + "§cDu hast keine Berechtigung für diesen Befehl.");
            return false;
        }
        if (args.length != 1) {
            p.sendMessage(Skyblock.PLUGIN_PREFIX + "§3Gib an welchen Villager du spawnen willst!");
            p.sendMessage(Skyblock.PLUGIN_PREFIX + "§3Wähle aus §8[§emünze§8, §eschatz§8, §etiere§8, §epflanze§8]");
            return false;
        }
        if (args[0].equalsIgnoreCase("münze")) {
            TradingInterface.spawnTradingVillager(p.getLocation(), VillagerType.MÜNZMEISTER);
        }
        else if (args[0].equalsIgnoreCase("schatz")) {
            TradingInterface.spawnTradingVillager(p.getLocation(), VillagerType.ARCHÄOLOGE);
        }
        else if (args[0].equalsIgnoreCase("pflanze")) {
            TradingInterface.spawnTradingVillager(p.getLocation(), VillagerType.BOTANIKER);
        }
        else if (args[0].equalsIgnoreCase("tiere")) {
            TradingInterface.spawnTradingVillager(p.getLocation(), VillagerType.ZOOLOGE);
        }
        return true;
    }
}
