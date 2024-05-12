package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.item.Items;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.ItemMeta;


public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerDeathEvent event) {
        if (!DataManager.IsEnable.dropOnDeath()) return;
        event.getDrops().removeIf(item -> {
            if (!item.hasItemMeta()) return false;
            ItemMeta itemMeta = item.getItemMeta();
            return itemMeta.getPersistentDataContainer().has(Items.TELEPORTBOW);
        });
    }
}
