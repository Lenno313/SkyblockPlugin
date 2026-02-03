package de.lenno.skyblock.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    @EventHandler
    public void onSpawnerChange(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;

        if (event.getClickedBlock().getType() == Material.SPAWNER) {
            ItemStack item = event.getItem();
            if (item != null && item.getType().name().endsWith("_SPAWN_EGG")) {
                event.setCancelled(true);
                Player player = event.getPlayer();

                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }
        }
    }
}
