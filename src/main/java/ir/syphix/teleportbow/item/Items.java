package ir.syphix.teleportbow.item;

import ir.syphix.teleportbow.data.DataManager;
import ir.syphix.teleportbow.message.Messages;
import ir.syphix.teleportbow.utils.TextUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.util.Arrays;
import java.util.Objects;

public class Items extends DataManager {

    public static NamespacedKey TELEPORTBOW = new NamespacedKey(StickyNote.plugin(), "items");

    private static ItemStack bow;
    private static ItemStack arrow;

    public Items() {
        createBow();
        createArrow();
    }

    private static void createBow() {
        ItemStack itemStack = new ItemStack(Material.BOW);
        ItemMeta bowMeta = itemStack.getItemMeta();
        bowMeta.getPersistentDataContainer().set(TELEPORTBOW, PersistentDataType.STRING, "bow");
        bowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Bow.displayName()));
        bowMeta.setLore(Bow.lore().stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).toList());
        if (Bow.enchantmentParticle()) {
            bowMeta.addEnchant(Enchantment.MENDING, 1, true);
            bowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if (Bow.isUnbreakable()) {
            bowMeta.setUnbreakable(true);
            bowMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }
        itemStack.setItemMeta(bowMeta);

        bow = itemStack;
    }

    private static void createArrow() {
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta arrowMeta = itemStack.getItemMeta();
        arrowMeta.getPersistentDataContainer().set(TELEPORTBOW, PersistentDataType.STRING, "arrow");
        arrowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Arrow.displayName()));
        arrowMeta.setLore(Arrow.lore().stream().map(line -> ChatColor.translateAlternateColorCodes('&', line)).toList());
        if (Arrow.enchantmentParticle()) {
            arrowMeta.addEnchant(Enchantment.MENDING, 1, true);
            arrowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(arrowMeta);

        arrow = itemStack;
    }

    public static ItemStack bow() {
        return bow;
    }

    public static ItemStack arrow() {
        return arrow;
    }

    public static void give(Player player) {
        boolean hasTeleportBow = hasCustomItem(player, Material.BOW);
        boolean hasTeleportArrow = hasCustomItem(player, Material.ARROW);

        if (hasTeleportBow && hasTeleportArrow) {
            return;
        }

        player.getInventory().setItem(DataManager.Bow.slot(), Items.bow());
        player.getInventory().setItem(DataManager.Arrow.slot(), Items.arrow());
    }

    public static void give(Player player, Player target) {
        boolean hasTeleportBow = hasCustomItem(target, Material.BOW);
        boolean hasTeleportArrow = hasCustomItem(target, Material.ARROW);

        if (hasTeleportBow && hasTeleportArrow) {
            TextUtils.sendMessage(player, TextUtils.toFormattedString(Messages.ALREADY_HAS));
            return;
        }

        target.getInventory().setItem(DataManager.Bow.slot(), Items.bow());
        target.getInventory().setItem(DataManager.Arrow.slot(), Items.arrow());
        TextUtils.sendMessage(player, TextUtils.toFormattedString(Messages.GIVE_ITEMS));
    }

    private static boolean hasCustomItem(Player player, Material material) {
        return Arrays.stream(player.getInventory().getContents()).filter(Objects::nonNull).anyMatch(item ->
                item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(Items.TELEPORTBOW) && item.getType() == material);
    }
}
