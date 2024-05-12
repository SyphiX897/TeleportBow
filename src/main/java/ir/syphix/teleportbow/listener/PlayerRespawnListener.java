package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.item.Items;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (!DataManager.IsEnable.giveOnRespawn()) return;
        Items.give(event.getPlayer());
    }
}
