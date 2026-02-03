package de.lenno.skyblock.events;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HitEvent implements Listener {

    @EventHandler
    public void onVillagerHit(EntityDamageByEntityEvent event) {
        // Prüfen, ob das Opfer ein Villager ist
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();

            // Nur wenn es unser Shop-Villager ist (über den Namen oder Tag)
            if (villager.getCustomName() != null && villager.getCustomName().contains("§")) {
                event.setCancelled(true); // Schlag wird komplett ignoriert
            }
        }
    }
}
