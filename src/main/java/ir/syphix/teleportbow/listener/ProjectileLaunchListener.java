package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.item.Items;
import ir.syrent.origin.paper.Origin;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class ProjectileLaunchListener implements Listener {

    FileConfiguration config = Origin.getPlugin().getConfig();

    @EventHandler
    public void onHit(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof Arrow) {
            Player player = (Player) event.getEntity().getShooter();
            if (!player.getInventory().getItemInMainHand().hasItemMeta()) return;
            PersistentDataContainer itemData = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            if (!itemData.has(Items.TYPE_KEY)) return;
            Sound soundOnShoot = Sound.valueOf(config.getString("sound-on-shoot"));
            player.playSound(player.getLocation(), soundOnShoot, 10, 30);
            Arrow arrow = (Arrow) event.getEntity();
            arrow.getPersistentDataContainer().set(Items.ARROW_ENTITY_KEY, PersistentDataType.BOOLEAN, true);
        }
    }

}
