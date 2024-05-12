package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.item.Items;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!DataManager.Arrow.noDamage()) return;
        if (event.getDamager() instanceof Arrow arrow) {
            if (arrow.getShooter() instanceof Player player) {
                if (!arrow.getPersistentDataContainer().has(Items.TELEPORTBOW)) return;
                event.setCancelled(true);
            }
        }
    }
}
