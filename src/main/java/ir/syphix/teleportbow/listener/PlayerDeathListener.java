package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.utils.Items;
import ir.syrent.origin.paper.Origin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.meta.ItemMeta;


public class PlayerDeathListener implements Listener {

    FileConfiguration config = Origin.getPlugin().getConfig();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (config.getBoolean("drop_on_death")) return;

        event.getDrops().removeIf((item) -> {
            if (!item.hasItemMeta()) return false;
            ItemMeta meta = item.getItemMeta();
            return meta.getPersistentDataContainer().has(Items.CUSTOME_ITEM_KEY);
        });
    }
}
