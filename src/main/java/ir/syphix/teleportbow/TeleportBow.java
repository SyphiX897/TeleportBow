package ir.syphix.teleportbow;

import ir.syphix.teleportbow.command.GiveBowCommand;
import ir.syphix.teleportbow.listener.*;
import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.OriginPlugin;

public final class TeleportBow extends OriginPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Origin.registerListener(new PlayerJoinListener());
        Origin.registerListener(new ItemDropListener());
        Origin.registerListener(new BowMoveListener());
        Origin.registerListener(new ProjectileHitListener());
        Origin.registerListener(new ProjectileLaunchListener());
        Origin.registerListener(new EntityDamageByEntityListener());
        Origin.registerListener(new PlayerDeathListener());

        new GiveBowCommand();

    }

}
