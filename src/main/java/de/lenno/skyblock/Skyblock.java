package de.lenno.skyblock;

import de.lenno.skyblock.commands.TestCommand;
import de.lenno.skyblock.commands.VillagerCommand;
import de.lenno.skyblock.events.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public final class Skyblock extends JavaPlugin {

    private static Skyblock instance;

    public static String CONSOLE_PREFIX = "[System] ";
    public static String PLUGIN_PREFIX = "§7[§eSkyblock§7] §3";

    public static IslandManager islandManager;
    public static List<Location> islandLocations;

    @Override
    public void onEnable() {
        sendConsoleMessage("Das Plugin wird geladen .. ");
        instance = this;
        islandLocations = IslandManager.getIslandLocations(Bukkit.getWorld("world"));
        islandManager = new IslandManager();

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
        return new VoidGenerator();
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new SpawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new FishingEvent(), this);
        Bukkit.getPluginManager().registerEvents(new CraftingEvent(), this);
        Bukkit.getPluginManager().registerEvents(new FurnaceEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new HitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PortalEvent(), this);
        Bukkit.getPluginManager().registerEvents(new GeneratorEvent(), this);
        Bukkit.getPluginManager().registerEvents(new DamageEvent(), this);
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
