package ir.syphix.teleportbow.utils;

import ir.syphix.teleportbow.message.Messages;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.sayandev.stickynote.bukkit.StickyNote;

public class TextUtils extends Messages {

    static MiniMessage miniMessage = MiniMessage.builder()
            .tags(
                    TagResolver.resolver(
                            TagResolver.standard()
                    )
            )
            .build();

    static BukkitAudiences bukkitAudiences = BukkitAudiences.create(StickyNote.plugin());

    public static Component toComponent(String content, TagResolver... placeholders) {
        return Component.empty().decoration(TextDecoration.ITALIC, false).append(miniMessage.deserialize(content, placeholders));
    }

    public static void sendMessage(CommandSender sender, Component component) {
        bukkitAudiences.sender(sender).sendMessage(component);
    }

    public static Component toFormattedString(String message, TagResolver... placeholders) {
        return toComponent(String.format("%s%s", PREFIX, message), placeholders);
    }
}
