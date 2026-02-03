package de.lenno.skyblock.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        // Wir prüfen die letzte Schadensursache
        if (player.getLastDamageCause() != null &&
                player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {

            // "Ohne Fremdeinwirkung" bedeutet: es gibt keinen Mörder (Killer)
            if (player.getKiller() == null) {

                // Deine eigene Nachricht setzen
                String playerName = player.getName();
                event.setDeathMessage("§7Das würd' mir stinken! §e" + playerName + " §7wollte mal sehen, wie tief das Void ist ...");

                // Alternativ: Eine Liste von zufälligen Sprüchen
                // String[] messages = {" hat die Schwerkraft getestet.", " dachte, er könne fliegen.", " ist im Nichts verschwunden."};
                // event.setDeathMessage("§e" + playerName + messages[new Random().nextInt(messages.length)]);
            }
        }
    }
}
