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
                    if (event.getHitEntity() != null) {
                        if (config.getBoolean("teleport-to-entity")) {
                            Location hitEntityLocation = event.getEntity().getLocation();
                            player.teleport(entityLocation(player, hitEntityLocation));
                            arrowGiver(player, event.getEntity());
                            return;
                        }
                    }
                    if (event.getHitBlock() != null) {
                        if (config.getBoolean("teleport-to-block")) {
                            Location arrowLocation = event.getEntity().getLocation();
                            player.setInvulnerable(true);

                            player.teleport(arrowLocation(player, arrowLocation));

                            player.setInvulnerable(false);
                            arrowGiver(player, event.getEntity());
                        }
                    }
                }
            }
        }
    }

    public void arrowGiver(Player player, Entity arrow) {
        arrow.remove();
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


}
