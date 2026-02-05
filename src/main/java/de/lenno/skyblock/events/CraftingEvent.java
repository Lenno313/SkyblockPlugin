package de.lenno.skyblock.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class CraftingEvent implements Listener {

    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item != null && isCoin(item)) {
                // Wenn eine Münze im Gitter liegt, wird das Ergebnis auf "leer" gesetzt
                event.getInventory().setResult(null);
                break;
            }
        }
    }

    // Hilfsmethode, um zu prüfen ob es eine Münze ist
    private boolean isCoin(ItemStack item) {
        if (!item.hasItemMeta()) return false;
        String displayName = item.getItemMeta().getDisplayName();
        return displayName.contains("Goldmünze") || displayName.contains("Silbermünze");
    }
}
