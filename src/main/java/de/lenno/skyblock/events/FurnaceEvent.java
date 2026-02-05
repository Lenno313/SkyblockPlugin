package de.lenno.skyblock.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class FurnaceEvent implements Listener {

    @EventHandler
    public void onFurnaceInteract(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.FURNACE ||
                event.getInventory().getType() == InventoryType.BLAST_FURNACE) {

            ItemStack item = event.getCursor(); // Das Item, das man gerade reinlegen will
            if (item != null && isCoin(item)) {
                // Wenn es der Slot 0 (Eingabe) oder Slot 1 (Brennstoff) ist
                if (event.getRawSlot() == 0 || event.getRawSlot() == 1) {
                    event.setCancelled(true);
                }
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
