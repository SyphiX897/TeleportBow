package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.utils.Items;
import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.utils.ComponentUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class ProjectileLaunchListener implements Listener {

    FileConfiguration config = Origin.getPlugin().getConfig();

    @EventHandler
    public void onHit(ProjectileLaunchEvent event) {

        if (event.getEntity().getShooter() instanceof Player player && event.getEntity() instanceof Arrow arrow) {

            ItemStack mainHandItem = player.getInventory().getItemInMainHand();
            ItemStack offHandItem = player.getInventory().getItemInOffHand();

            if (!mainHandItem.hasItemMeta() && !offHandItem.hasItemMeta()) return;

            String hand = null;

            if (mainHandItem.getType().equals(Material.BOW) &&
                    mainHandItem.hasItemMeta()) {
                if (mainHandItem.getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY)) {
                    hand = "MAIN_HAND";
                }
            } else {
                if (mainHandItem.getType().equals(Material.BOW)) return;
                hand = "OFF_HAND";
            }

            if (hand.equals("null")) return;

            PersistentDataContainer itemData;

            if (hand.equals("MAIN_HAND")) {
                itemData = mainHandItem.getItemMeta().getPersistentDataContainer();
            } else {
                itemData = offHandItem.getItemMeta().getPersistentDataContainer();
            }

            if (!itemData.has(Items.TYPE_KEY)) return;

            if (!player.hasPermission("teleportbow.use")) {
                player.sendMessage(ComponentUtils.component("<gradient:dark_red:red>You dont have permission to use TeleportBow!"));
                event.setCancelled(true);
                return;
            }

            Sound soundOnShoot = Sound.valueOf(config.getString("sound_on_shoot"));
            player.playSound(player.getLocation(), soundOnShoot, 10, 30);
            arrow.getPersistentDataContainer().set(Items.ARROW_ENTITY_KEY, PersistentDataType.STRING, "arrow");

            if (config.getBoolean("infinity_arrow")) {
                player.getInventory().setItem(config.getInt("arrow.slot"), Items.getArrow());
            }

            if (config.getBoolean("glowing_arrow")) {
                arrow.setGlowing(true);
            }

            String customName = config.getString("arrow.custom_name");

            if (customName == null) {
                throw new NullPointerException("Arrow custom name is null on configuration file, please set a valid value for it.");
            }

            if (customName.isEmpty()) return;

            arrow.customName(ComponentUtils.component(customName));
            arrow.setCustomNameVisible(true);

        }
    }

}
