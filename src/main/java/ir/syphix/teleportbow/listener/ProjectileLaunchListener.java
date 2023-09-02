package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.item.Items;
import ir.syrent.origin.paper.Origin;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onHit(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Arrow) {
            Player player = (Player) event.getEntity().getShooter();
            if (!player.getInventory().getItemInMainHand().hasItemMeta()) return;
            PersistentDataContainer itemData = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            if (!itemData.has(Items.TYPE_KEY)) return;
            Arrow arrow = (Arrow) event.getEntity();
            arrow.getPersistentDataContainer().set(Items.ARROW_ENTITY_KEY, PersistentDataType.BOOLEAN, true);
        }
    }

}
