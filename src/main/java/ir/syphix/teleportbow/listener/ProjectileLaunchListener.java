package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.utils.Items;
import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.utils.ComponentUtils;
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
        if (event.getEntity().getShooter() instanceof Player player && event.getEntity() instanceof Arrow arrow) {
            if (!player.getInventory().getItemInMainHand().hasItemMeta()) return;
            PersistentDataContainer itemData = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();
            if (!itemData.has(Items.TYPE_KEY)) return;
            Sound soundOnShoot = Sound.valueOf(config.getString("sound_on_shoot"));
            player.playSound(player.getLocation(), soundOnShoot, 10, 30);
            arrow.getPersistentDataContainer().set(Items.ARROW_ENTITY_KEY, PersistentDataType.STRING, "arrow");
            if (config.getBoolean("glowing_arrow")) {
                arrow.setGlowing(true);
            }
            String customName = config.getString("arrow.custom_name");

            if (customName == null) {
                throw new NullPointerException("Arrow custom name is null on configuration file, please set a valid value for it.");
            }

            if (!customName.isEmpty()) {
                arrow.customName(ComponentUtils.component(customName));
                arrow.setCustomNameVisible(true);
            }
        }
    }

}
