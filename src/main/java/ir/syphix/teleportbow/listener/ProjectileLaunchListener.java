package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.utils.Items;
import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.utils.ComponentUtils;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Objects;


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

            if (hand == null) return;

            PersistentDataContainer itemData;

            if (hand.equals("MAIN_HAND")) {
                itemData = mainHandItem.getItemMeta().getPersistentDataContainer();
            } else {
                itemData = offHandItem.getItemMeta().getPersistentDataContainer();
            }

            if (!itemData.has(Items.TYPE_KEY)) return;

            if (Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).noneMatch(item -> item.hasItemMeta()
                    && item.getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY)
                    && item.getItemMeta().getPersistentDataContainer().has(Items.CUSTOM_ITEM_KEY)
                    && item.getType().equals(Material.ARROW))) {
                player.sendMessage(ComponentUtils.component("<gradient:dark_red:red>You dont have an arrow to teleport!"));
                event.setCancelled(true);
                return;
            }

            if (!player.hasPermission("teleportbow.use")) {
                player.sendMessage(ComponentUtils.component("<gradient:dark_red:red>You dont have permission to use TeleportBow!"));
                event.setCancelled(true);
                return;
            }
            arrow.getPersistentDataContainer().set(Items.ARROW_ENTITY_KEY, PersistentDataType.STRING, "arrow");

            if (config.getBoolean("glowing_arrow")) {
                arrow.setGlowing(true);
            }

            ConfigurationSection arrowLaunchSoundSection = config.getConfigurationSection("arrow.launch_sound");
            if (arrowLaunchSoundSection.getBoolean("enabled")) {
                String soundName = arrowLaunchSoundSection.getString("name");
                if (soundName != null) {
                    player.playSound(player.getLocation(), Sound.valueOf(soundName), 10, 30);
                }

            }

            ConfigurationSection launchParticleSection = config.getConfigurationSection("arrow.launch_particle");
            if (launchParticleSection.getBoolean("enabled")) {
                String particleName = launchParticleSection.getString("name");
                if (particleName != null) {
                    Bukkit.getScheduler().runTaskTimer(Origin.getPlugin(), task -> {
                        if (!arrow.isValid()) {
                            task.cancel();
                        }
                        Location arrowLocation = arrow.getLocation().clone();
                        arrowLocation.getWorld().spawnParticle(Particle.valueOf(particleName), arrowLocation, 1);
                    },0, 1);
                }
            }

            if (config.getBoolean("infinity_arrow")) {
                player.getInventory().setItem(config.getInt("arrow.slot"), Items.getArrow());
            }

            ConfigurationSection customNameSection = config.getConfigurationSection("arrow.custom_name");
            if (customNameSection.getBoolean("enabled")) {
                String customName = customNameSection.getString("name");
                if (customName != null) {
                    arrow.customName(ComponentUtils.component(customName));
                    arrow.setCustomNameVisible(true);
                };

            }

        }

    }

}
