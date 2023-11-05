package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.utils.Items;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class BowMoveListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (!player.hasPermission("teleportbow.changeslot") && player.getGameMode() != GameMode.CREATIVE) {
                if (event.getCurrentItem() != null) {
                    ItemStack clickedItem = event.getCurrentItem();
                    if (clickedItem.hasItemMeta()) {
                        if (clickedItem.getItemMeta().getPersistentDataContainer().has(Items.CUSTOME_ITEM_KEY) && clickedItem.getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHandChange(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("teleportbow.changeslot") && player.getGameMode() != GameMode.CREATIVE) {

            if (event.getOffHandItem().getItemMeta().getPersistentDataContainer().has(Items.CUSTOME_ITEM_KEY) &&
                    event.getOffHandItem().getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY) ||
                    event.getMainHandItem().getItemMeta().getPersistentDataContainer().has(Items.CUSTOME_ITEM_KEY) &&
                            event.getMainHandItem().getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY)) {
                event.setCancelled(true);
            }

        }
    }
}
