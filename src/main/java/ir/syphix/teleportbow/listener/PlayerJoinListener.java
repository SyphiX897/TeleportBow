package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.item.Items;
import ir.syrent.origin.paper.Origin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public class PlayerJoinListener implements Listener {

    FileConfiguration config = Origin.getPlugin().getConfig();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean("give_on_join")) {

            for (int i = 0; i <= player.getInventory().getSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item == null) continue;
                if (!item.hasItemMeta()) continue;
                PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();
                if (itemData.has(Items.CUSTOME_ITEM_KEY))
                    player.getInventory().clear(i);
            }
            player.getInventory().setItem(config.getInt("teleport-bow.slot"), Items.getTeleportBow());
            player.getInventory().setItem(config.getInt("arrow.slot"), Items.getArrow());
        }
    }
}
