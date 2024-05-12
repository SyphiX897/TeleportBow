package ir.syphix.teleportbow.command;

import ir.syphix.teleportbow.file.FileManager;
import ir.syphix.teleportbow.item.Items;
import ir.syphix.teleportbow.message.Messages;
import ir.syphix.teleportbow.utils.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.sayandev.stickynote.bukkit.command.StickyCommand;
import org.sayandev.stickynote.bukkit.command.interfaces.SenderExtension;
import org.sayandev.stickynote.lib.incendo.cloud.Command;
import org.sayandev.stickynote.lib.incendo.cloud.bukkit.parser.PlayerParser;

import java.util.Arrays;
import java.util.Objects;

public class TeleportBowCommand extends StickyCommand {
    public TeleportBowCommand() {
        super("teleportbow", "tb", "tbow", "teleportb");

        Command.Builder<SenderExtension> reload = getBuilder()
                .literal("reload")
                .permission(constructBasePermission("reload"))
                .handler(context -> {
                    FileManager.SettingsFile.reload();
                    FileManager.MessagesFile.reload();
                    new Items();

                    Player player = context.sender().player();
                    if (player == null) return;
                    TextUtils.sendMessage(player, TextUtils.toFormattedString(Messages.RELOAD));
                });
        getManager().command(reload);

        Command.Builder<SenderExtension> give = getBuilder()
                .literal("give")
                .permission(constructBasePermission("give"))
                .optional("player", PlayerParser.playerParser())
                .handler(context -> {
                    Player player = context.sender().player();
                    if (player == null) return;
                    Player target = context.<Player>optional("player").orElse(player);

                    Items.give(player, target);
                });
        getManager().command(give);
    }

    private boolean hasCustomItem(Player player, Material material) {
        return Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).anyMatch(item ->
                item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW) && item.getType() == material);
    }
}
