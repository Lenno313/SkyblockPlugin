package de.lenno.skyblock;

import de.lenno.skyblock.commands.TestCommand;
import de.lenno.skyblock.commands.VillagerCommand;
import de.lenno.skyblock.events.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;


public final class Skyblock extends JavaPlugin {

    private static Skyblock instance;

    public static String CONSOLE_PREFIX = "[System] ";
    public static String PLUGIN_PREFIX = "§7[§eSkyblock§7] §3";

    @Override
    public void onEnable() {
        sendConsoleMessage("Das Plugin wird geladen .. ");
        instance = this;

        saveDefaultConfig();

        sendConsoleMessage("Die Commands werden registriert .. ");
        registerCommands();

        sendConsoleMessage("Die Events werden registriert .. ");
        registerEvents();

        sendConsoleMessage("Das Plugin wurde erfolgreich geladen!");
    }

    @Override
    public void onDisable() {
        sendConsoleMessage("Das Plugin wird gestoppt .. ");
        sendConsoleMessage("Das Plugin wurde erfolgreich gestoppt!");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        getLogger().info("!!! DER VOID-GENERATOR WIRD AKTIVIERT FUER: " + worldName + " !!!");
        return new VoidGenerator();
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
    }

    public void registerCommands() {
        getCommand("test").setExecutor(new TestCommand());
        getCommand("spawnvillager").setExecutor(new VillagerCommand());
    }

    public static Skyblock getInstance() {
        return instance;
    }

    public void sendConsoleMessage(String message) {
        getLogger().info(CONSOLE_PREFIX + message);
    }
}
