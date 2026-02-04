package de.lenno.skyblock.events;

import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onFireworkDamage(EntityDamageByEntityEvent event) {
        // Prüfen, ob der Verursacher eine Rakete ist
        if (event.getDamager() instanceof Firework) {
            event.setCancelled(true); // Schaden wird komplett verhindert
        }
    }
}
