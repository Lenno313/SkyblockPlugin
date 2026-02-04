package de.lenno.skyblock.events;

import de.lenno.skyblock.Skyblock;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class GeneratorEvent implements Listener {

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        Material type = event.getNewState().getType();

        double random = Math.random() * 100;

        // Wir reagieren auf alles, was ein "Generator" erzeugt
        if (type == Material.COBBLESTONE) {
            if (random < 0.1) {
                spawnRocket(event.getBlock().getLocation().add(0,0.5,0), Color.AQUA);
                event.getNewState().setType(Material.DIAMOND_ORE);
            }
            else if (random < 2.1) {
                event.getNewState().setType(Material.DIORITE);
            }
            else if (random < 4.1) {
                event.getNewState().setType(Material.ANDESITE);
            }
            else if (random < 6.1) {
                event.getNewState().setType(Material.GRANITE);
            }
            else if (random < 7.1) {
                event.getNewState().setType(Material.CALCITE);
            }
            else if (random < 8.6) {
                event.getNewState().setType(Material.TUFF);
            }
            else if (random < 35.0) {
                event.getNewState().setType(Material.STONE);
            }
            else {
                event.getNewState().setType(Material.COBBLESTONE);
            }
        }
        else if (type == Material.BASALT) {
            if (random < 0.2) {
                spawnRocket(event.getBlock().getLocation().add(0,0.5,0), Color.BLACK);
                event.getNewState().setType(Material.ANCIENT_DEBRIS);
            }
            else if (random < 1.0) {
                event.getNewState().setType(Material.BONE_BLOCK);
            }
            else if (random < 2.0) {
                event.getNewState().setType(Material.SOUL_SOIL);
            }
            else if (random < 4.0) {
                event.getNewState().setType(Material.SOUL_SAND);
            }
            else if (random < 5.0) {
                event.getNewState().setType(Material.GILDED_BLACKSTONE);
            }
            else if (random < 7.0) {
                event.getNewState().setType(Material.BLACKSTONE);
            }
            else if (random < 8.0) {
                event.getNewState().setType(Material.RED_NETHER_BRICKS);
            }
            else if (random < 10.0) {
                event.getNewState().setType(Material.NETHER_BRICKS);
            }
            else if (random < 12.0) {
                event.getNewState().setType(Material.NETHER_QUARTZ_ORE);
            }
            else if (random < 13.0) {
                event.getNewState().setType(Material.GRAVEL);
            }
            else if (random < 13.25) {
                event.getBlock().getLocation().getWorld().playSound(event.getBlock().getLocation().add(0, 1, 0), Sound.ITEM_GOAT_HORN_SOUND_2, 1.0f, 1.0f);
                event.getNewState().setType(Material.CRYING_OBSIDIAN);
            }
            else if (random < 13.5) {
                event.getBlock().getLocation().getWorld().playSound(event.getBlock().getLocation().add(0, 1, 0), Sound.ITEM_GOAT_HORN_SOUND_2, 1.0f, 1.0f);
                event.getNewState().setType(Material.OBSIDIAN);
            }
            else if (random < 30.0) {
                event.getNewState().setType(Material.BASALT);
            }
            else {
                event.getNewState().setType(Material.NETHERRACK);
            }
        }
    }

    private void spawnRocket(Location loc, Color color) {
        Firework fw = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta fm = fw.getFireworkMeta();

        // Effekt erstellen: Explosion, Farbe und Funkeln
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(true)
                .withColor(color)
                .withFade(Color.WHITE)
                .with(FireworkEffect.Type.BALL)
                .trail(true)
                .build();

        fm.addEffect(effect);
        fm.setPower(3); // Sofort explodieren (0 = sehr kurze Flugzeit)
        fw.setFireworkMeta(fm);

        // Die Rakete nach einem Tick explodieren lassen, falls Power 0 nicht reicht
        Bukkit.getScheduler().runTaskLater(Skyblock.getInstance(), fw::detonate, 1L);
    }
}
