package ir.syphix.teleportbow;

import ir.syphix.teleportbow.command.TeleportBowCommand;
import ir.syphix.teleportbow.file.FileManager;
import ir.syphix.teleportbow.item.Items;
import ir.syphix.teleportbow.listener.*;
import ir.syphix.teleportbow.message.Messages;
import org.sayandev.stickynote.bukkit.StickyNote;
import org.sayandev.stickynote.bukkit.StickyNotePlugin;

public final class TeleportBow extends StickyNotePlugin {

    @Override
    public void onEnable() {
//        new BukkitLibraryLoader(this);
//        new WrappedStickyNotePlugin(this);
        new FileManager();
        new Messages();
        registerListeners();
        new TeleportBowCommand();
        new Items();

    }

    private void registerListeners() {
        StickyNote.registerListener(new EntityDamageByEntityListener());
        StickyNote.registerListener(new InventoryListener());
        StickyNote.registerListener(new PlayerDeathListener());
        StickyNote.registerListener(new PlayerJoinListener());
        StickyNote.registerListener(new PlayerRespawnListener());
        StickyNote.registerListener(new ProjectileHitListener());
        StickyNote.registerListener(new ProjectileLaunchListener());
    }

}
