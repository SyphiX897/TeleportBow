package ir.syphix.teleportbow.command;

import cloud.commandframework.Command.Builder;
import cloud.commandframework.Completion;
import cloud.commandframework.arguments.standard.StringArgument;
import ir.syphix.teleportbow.utils.Items;
import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.command.Command;
import ir.syrent.origin.paper.command.interfaces.ISender;
import ir.syrent.origin.paper.utils.ComponentUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class GiveBowCommand extends Command {

    FileConfiguration config = Origin.getPlugin().getConfig();

    public GiveBowCommand() {
        super("givebow");

        Builder<ISender> command = getBuilder()
                .permission(getPermission("givebow"))
                .argument(
                        StringArgument.<ISender>builder("player")
                                .asOptional()
                                .withCompletionsProvider((context, input) ->
                                        Origin.getOnlinePlayers().stream()
                                                .map(player -> Completion.of(player.getName()))
                                                .collect(Collectors.toList())
                                )
                )
                .handler((context) -> {
                    Player player = context.getSender().player();
                    Optional<String> targetOptional = context.getOptional("player");
                    if (targetOptional.isPresent()) {
                        Player target = Bukkit.getPlayerExact(targetOptional.get());
                        if (player == null) return;
                        if (target == null) {
                            player.sendMessage(ComponentUtils.component("<gradient:dark_red:red>This player does not exist"));
                            return;
                        }

                        boolean hasCustomBow = hasCustomItem(target, Material.BOW);
                        boolean hasCustomArrow = hasCustomItem(target, Material.ARROW);

                        if (hasCustomBow && hasCustomArrow) {
                            player.sendMessage(ComponentUtils.component("<gradient:dark_red:red>This player already has these item"));
                            return;
                        }

                        target.getInventory().setItem(config.getInt("bow.slot"), Items.getTeleportBow());
                        target.getInventory().setItem(config.getInt("arrow.slot"), Items.getArrow());
                        return;
                    }

                    if (player == null) return;
                    boolean hasCustomBow = hasCustomItem(player, Material.BOW);
                    boolean hasCustomArrow = hasCustomItem(player, Material.ARROW);

                    if (hasCustomBow && hasCustomArrow) {
                        player.sendMessage(ComponentUtils.component("<gradient:dark_red:red>This player already has these item"));
                        return;
                    }

                    player.getInventory().setItem(config.getInt("bow.slot"), Items.getTeleportBow());
                    player.getInventory().setItem(config.getInt("arrow.slot"), Items.getArrow());
                });
        saveCommand(command);
    }

    private boolean hasCustomItem(Player player, Material material) {
        return Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).anyMatch((item) -> item.hasItemMeta()
                && item.getItemMeta().getPersistentDataContainer().has(Items.TYPE_KEY)
                && item.getItemMeta().getPersistentDataContainer().has(Items.CUSTOM_ITEM_KEY)
                && item.getType() == material);
    }

}
