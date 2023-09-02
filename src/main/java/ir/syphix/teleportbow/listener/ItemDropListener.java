package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.item.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDropListener implements Listener {


    @EventHandler
    public void onPickUp(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("teleportbow.drop")) {
            ItemStack dropedItem = event.getItemDrop().getItemStack();
            if (!dropedItem.hasItemMeta()) return;
            if (dropedItem.getItemMeta().getPersistentDataContainer().has(Items.CUSTOME_ITEM_KEY) && dropedItem.getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY)) {
                event.setCancelled(true);
            }
        }
    }
}
