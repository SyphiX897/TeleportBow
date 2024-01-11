package ir.syphix.teleportbow.listener;

import com.destroystokyo.paper.ParticleBuilder;
import ir.syphix.teleportbow.utils.Items;
import ir.syrent.origin.paper.Origin;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataContainer;


public class ProjectileHitListener implements Listener {

    FileConfiguration config = Origin.getPlugin().getConfig();

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) {
            if (!(event.getEntity() instanceof Arrow arrow)) return;
            PersistentDataContainer arrowData = arrow.getPersistentDataContainer();
            if (arrowData.has(Items.ARROW_ENTITY_KEY)) {
                if (player.hasPermission("teleportbow.use")) {

                    ConfigurationSection particleSection = config.getConfigurationSection("arrow.hit_particle");


                    if (event.getHitEntity() != null) {
                        if (config.getBoolean("teleport_to_entity")) {

                            Location hitEntityLocation = event.getEntity().getLocation();

                            ConfigurationSection particleHittingEntitySection = particleSection.getConfigurationSection("on_hitting_entity");

                            if (particleHittingEntitySection.getBoolean("enabled")) {

                                String particleName = particleHittingEntitySection.getString("name");
                                double particleRadius = particleHittingEntitySection.getDouble("radius");

                                summonParticles(particleName, hitEntityLocation, particleRadius);
                            }

                            player.teleport(entityLocation(player, hitEntityLocation));
                        }
                        arrowGiver(player, event.getEntity());
                        return;
                    }
                    if (event.getHitBlock() != null) {
                        if (config.getBoolean("teleport_to_block")) {
                            Location arrowLocation = event.getEntity().getLocation();
                            player.setInvulnerable(true);

                            ConfigurationSection particleBlockHittingSection = particleSection.getConfigurationSection("on_hitting_block");

                            if (particleBlockHittingSection.getBoolean("enabled")) {

                                String particleName = particleBlockHittingSection.getString("name");
                                double particleRadius = particleBlockHittingSection.getDouble("radius");

                                summonParticles(particleName, arrowLocation, particleRadius);
                            }


                            player.teleport(arrowLocation(player, arrowLocation));

                            player.setInvulnerable(false);
                        }
                        arrowGiver(player, event.getEntity());
                    }
                }
            }
        }
    }

    public void arrowGiver(Player player, Entity arrow) {
        arrow.remove();

        if (player.isDead()) return;

        player.getInventory().setItem(config.getInt("arrow.slot"), Items.getArrow());
    }
    public Location arrowLocation(Player player, Location arrowLocation) {
        float playerYaw = player.getEyeLocation().getYaw();
        float playerPitch = player.getEyeLocation().getPitch();

        arrowLocation.setYaw(playerYaw);
        arrowLocation.setPitch(playerPitch);
        return arrowLocation;
    }
    public Location entityLocation(Player player, Location hitEntityLocation) {
        float playerYaw = player.getEyeLocation().getYaw();
        float playerPitch = player.getEyeLocation().getPitch();

        hitEntityLocation.setYaw(playerYaw);
        hitEntityLocation.setPitch(playerPitch);
        return hitEntityLocation;
    }

    private void summonParticles(String particleName, Location location, double radius) {
        for (int i = 0; i <= 360; i += 4) {
            double x = Math.sin(Math.toRadians(i)) * radius;
            double z = Math.cos(Math.toRadians(i)) * radius;

            Location particleLocation = location.clone().add(x, 0, z);

            location.getWorld().spawnParticle(Particle.valueOf(particleName), particleLocation, 1);

        }
    }


}
