package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.item.Items;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.sayandev.stickynote.bukkit.StickyNote;


public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        PersistentDataContainer arrowData = arrow.getPersistentDataContainer();
        if (!arrowData.has(Items.TELEPORTBOW)) return;
        if (!player.hasPermission("teleportbow.use")) return;

        if (!DataManager.Bow.isInfinityArrow()) {
            player.getInventory().setItem(DataManager.Arrow.slot(), Items.arrow());
        }

        Location arrowLocation = arrow.getLocation();
        if (event.getHitEntity() != null) {
            if (!DataManager.IsEnable.teleportToEntity()) return;

            if (DataManager.Arrow.isHitParticleOnHittingEntityEnable()) {
                String particleName = DataManager.Arrow.hitParticleOnHittingEntityName();
                double particleRadius = DataManager.Arrow.hitParticleOnHittingEntityRadius();
                if (particleName == null) {
                    StickyNote.error("Particle name is null in settings.yml! (arrow -> hit_particle -> on_hitting_entity -> particle)");
                    return;
                }
                spawnParticle(particleName, arrowLocation, particleRadius);
            }

            if (DataManager.Arrow.isHitSoundOnHittingEntityEnable()) {
                String soundName = DataManager.Arrow.hitSoundOnHittingEntityName();
                if (soundName == null) {
                    StickyNote.error("Sound name is null in settings.yml! (arrow -> hit_sound -> on_hitting_entity -> sound)");
                }
                player.playSound(player.getLocation(), Sound.valueOf(soundName), 10, 30);
            }

            teleport(player, arrow);
            return;
        }

        if (event.getHitBlock() != null) {
            if (!DataManager.IsEnable.teleportToBlock()) return;

            if (DataManager.Arrow.isHitParticleOnHittingBlockEnable()) {
                String particleName = DataManager.Arrow.hitParticleOnHittingBlockName();
                double particleRadius = DataManager.Arrow.hitParticleOnHittingBlockRadius();
                if (particleName == null) {
                    StickyNote.error("Particle name is null in settings.yml! (arrow -> hit_particle -> on_hitting_block -> particle)");
                    return;
                }
                spawnParticle(particleName, arrowLocation, particleRadius);
            }

            if (DataManager.Arrow.isHitSoundOnHittingBlockEnable()) {

                String soundName = DataManager.Arrow.hitSoundOnHittingBlockName();
                if (soundName == null) {
                    StickyNote.error("Sound name is null in settings.yml! (arrow -> hit_sound -> on_hitting_block -> sound)");
                }
                player.playSound(player.getLocation(), Sound.valueOf(soundName), 10, 30);
            }

            teleport(player, arrow);
        }
    }

    private void teleport(Player player, Arrow arrow) {
        Location arrowLocation = arrow.getLocation();
        arrowLocation.setYaw(player.getEyeLocation().getYaw());
        arrowLocation.setPitch(player.getEyeLocation().getPitch());

        player.teleport(arrowLocation);
        arrow.remove();
    }

    private void spawnParticle(String particleName, Location location, double radius) {
        for (int i = 0; i <= 360; i += 4) {
            double x = Math.sin(Math.toRadians(i)) * radius;
            double z = Math.cos(Math.toRadians(i)) * radius;

            Location particleLocation = location.clone().add(x, 0, z);
            location.getWorld().spawnParticle(Particle.valueOf(particleName), particleLocation, 1);
        }
    }
}