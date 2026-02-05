package de.lenno.skyblock.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishingEvent implements Listener {

    @EventHandler
    public void onFishing(PlayerFishEvent event) {
        // 1. Haben wir überhaupt etwas gefangen?
        if (event.getCaught() == null) return;

        // 2. Nur reagieren, wenn der Status "CAUGHT_FISH" ist (echter Erfolg am Haken)
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {

            // 3. Sicherstellen, dass es ein Item-Drop ist (kein Mob)
            if (event.getCaught() instanceof Item) {
                Item caughtItemEntity = (Item) event.getCaught();
                Material caughtType = caughtItemEntity.getItemStack().getType();

                if (caughtType == Material.BOWL ||
                    caughtType == Material.STICK ||
                    caughtType == Material.BONE ||
                    caughtType == Material.TRIPWIRE_HOOK ||
                    caughtType == Material.ROTTEN_FLESH) {
                    double random = Math.random() * 100;
                    if (random < 50.0) {
                        caughtItemEntity.setItemStack(new ItemStack(Material.PRISMARINE_SHARD));
                    }
                    else if (random < 75.0) {
                        caughtItemEntity.setItemStack(new ItemStack(Material.PRISMARINE_CRYSTALS));
                    }
                    else {
                        caughtItemEntity.setItemStack(new ItemStack(Material.KELP));
                    }
                }
            }
        }
    }
}
