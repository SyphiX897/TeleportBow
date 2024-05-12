package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.item.Items;
import ir.syphix.teleportbow.message.Messages;
import ir.syphix.teleportbow.utils.TextUtils;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.Arrays;
import java.util.Objects;


public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Arrow arrow)) return;

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();
        if (!mainHandItem.hasItemMeta() && !offHandItem.hasItemMeta()) return;

        String hand = null;

        if (mainHandItem.getType().equals(Material.BOW) && mainHandItem.hasItemMeta()) {
            if (mainHandItem.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW)) {
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
        if (!itemData.has(Items.TELEPORTBOW)) return;

        if (Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).noneMatch(item -> item.hasItemMeta()
                && item.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW)
                && item.getType().equals(Material.ARROW))) {
            TextUtils.sendMessage(player, TextUtils.toFormattedString(Messages.MISSING_ARROW));
            event.setCancelled(true);
            return;
        }

        if (!player.hasPermission("teleportbow.use")) {
            TextUtils.sendMessage(player, TextUtils.toFormattedString(Messages.NEED_PERMISSION));
            event.setCancelled(true);
            return;
        }


        arrow.getPersistentDataContainer().set(Items.TELEPORTBOW, PersistentDataType.STRING, "arrow");

        if (DataManager.Arrow.isGlowing()) {
            arrow.setGlowing(true);
        }

        if (DataManager.Arrow.isHologramEnable()) {
            String hologramText = DataManager.Arrow.hologramText();
            if (hologramText == null) {
                StickyNote.error("Hologram name is null in settings.yml! (arrow -> hologram -> text)");
                return;
            }
            arrow.setCustomName(hologramText);
            arrow.setCustomNameVisible(true);
        }

        if (DataManager.Arrow.isLaunchSoundEnabled()) {
            String soundName = DataManager.Arrow.launchSoundName();
            if (soundName == null) {
                StickyNote.error("Sound name is null in settings.yml! (arrow -> launch_sound -> sound)");
                return;
            }
            player.playSound(player.getLocation(), Sound.valueOf(soundName), 10, 30);
        }

        if (DataManager.Arrow.isLaunchParticleEnable()) {
            String particleName = DataManager.Arrow.launchParticleName();
            if (particleName == null) {
                StickyNote.error("Particle name is null in settings.yml! (arrow -> launch_particle -> particle)");
                return;
            }
            Bukkit.getScheduler().runTaskTimer(StickyNote.plugin(), task -> {
                if (!arrow.isValid()) {
                    task.cancel();
                }
                Location arrowLocation = arrow.getLocation().clone();
                arrowLocation.getWorld().spawnParticle(Particle.valueOf(particleName), arrowLocation, 1);
            },0, 1);
        }

        if (DataManager.Bow.isInfinityArrow()) {
            player.getInventory().setItem(DataManager.Arrow.slot(), Items.arrow());
        }
    }

}
