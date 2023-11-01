package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.item.Items;
import ir.syrent.origin.paper.Origin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerDeathListener implements Listener {

    FileConfiguration config = Origin.getPlugin().getConfig();
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!config.getBoolean("drop_on_death")) {
            Origin.broadcast("1");
            Player player = event.getPlayer();
            for (ItemStack item : player.getInventory().getContents()) {
                if (item == null) return;
                Origin.broadcast("2");
                if (item.hasItemMeta()) {
                    Origin.broadcast("3");
                    if (item.getItemMeta().getPersistentDataContainer().has(Items.CUSTOME_ITEM_KEY) &&
                            item.getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY) &&
                            (item.getType().equals(Material.BOW) ||
                            item.getType().equals(Material.ARROW))) {
                        event.setCancelled(true);
                        Origin.broadcast("4");
                        player.getInventory().remove(item);
                        player.getInventory().remove(Items.getArrow());
                    }
                }
                Origin.broadcast("5" + player.getName());
                Bukkit.getScheduler().runTaskLater(Origin.getPlugin(), task -> {
                    player.setHealth(0.0);
                }, 1);
            }
        }
    }
}
