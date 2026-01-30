package de.lenno.skyblock.commands;

import de.lenno.skyblock.Skyblock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NonNull CommandSender commandSender, @NonNull Command command, @NonNull String s, @NonNull String[] args) {
        if (!(commandSender instanceof  Player)) {
            return false;
        }
        Player p = (Player) commandSender;
        p.sendMessage(Skyblock.PLUGIN_PREFIX + "§3Jo! Dat hat geklappt :)");

        return true;
    }
}
