package ir.syphix.teleportbow.listener;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.item.Items;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!DataManager.IsEnable.giveOnJoin()) return;
        Items.give(event.getPlayer());
    }
}
