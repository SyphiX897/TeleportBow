package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.item.Items;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.sayandev.stickynote.bukkit.StickyNote;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (player.hasPermission("teleportbow.changeslot")) return;
            ItemStack itemStack = event.getCurrentItem();
            if (itemStack == null) return;
            if (!itemStack.hasItemMeta()) return;
            if (!itemStack.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW)) return;
            if (event.getSlot() != DataManager.Arrow.slot() && event.getSlot() != DataManager.Bow.slot()) return;
            if (player.getGameMode() == GameMode.CREATIVE) {
                ItemStack offhand = player.getInventory().getItemInOffHand().clone();
                if (offhand.getType() == Material.AIR) {
                    player.closeInventory();
                    event.setCancelled(true);
                    StickyNote.runAsync(() -> {
                        player.getEquipment().setItemInOffHand(null);
                    }, 1);
                } else {
                    player.closeInventory();
                    event.setCancelled(true);
                    StickyNote.runAsync(() -> {
                        player.getEquipment().setItemInOffHand(offhand);
                    }, 1);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerSwapHandItem(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = event.getMainHandItem();
        ItemStack offHandItem = event.getOffHandItem();

        if (player.hasPermission("teleportbow.changeslot")) return;
        if ((mainHandItem.hasItemMeta() && mainHandItem.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW)) ||
                (offHandItem.hasItemMeta() && offHandItem.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("teleportbow.drop")) return;
        if (!event.getItemDrop().getItemStack().hasItemMeta()) return;
        if (!event.getItemDrop().getItemStack().getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW)) return;
        event.setCancelled(true);
        StickyNote.runAsync(() -> {
            for (int i = 0; i <= 35; i++) {
                if (player.getInventory().getItem(i) == null) continue;
                ItemStack itemStack = player.getInventory().getItem(i);
                if (!itemStack.hasItemMeta()) continue;
                if (!itemStack.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW)) continue;
                if (i == DataManager.Bow.slot() || i == DataManager.Arrow.slot()) continue;
                player.getInventory().setItem(i, null);
                if (i == DataManager.Arrow.slot()) {
                    if (itemStack.getAmount() == 1) continue;
                    itemStack.setAmount(1);
                }
            }
        }, 1);
    }
}
