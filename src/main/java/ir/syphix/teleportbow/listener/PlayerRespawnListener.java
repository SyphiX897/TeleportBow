package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.utils.Items;
import ir.syrent.origin.paper.Origin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    FileConfiguration config = Origin.getPlugin().getConfig();

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (config.getBoolean("give_on_respawn")) {
            Player player = event.getPlayer();

            player.getInventory().setItem(config.getInt("teleport-bow.slot"), Items.getTeleportBow());
            player.getInventory().setItem(config.getInt("arrow.slot"), Items.getArrow());
        }
    }
}
