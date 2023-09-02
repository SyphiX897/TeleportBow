package ir.syphix.teleportbow.item;

import ir.syrent.origin.paper.Origin;
import ir.syrent.origin.paper.utils.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Items {

    static FileConfiguration config = Origin.getPlugin().getConfig();

    public static NamespacedKey CUSTOME_ITEM_KEY = new NamespacedKey(Origin.getPlugin(), "custome_item");
    public static NamespacedKey TYPE_KEY = new NamespacedKey(Origin.getPlugin(), "type");
    public static NamespacedKey ARROW_ENTITY_KEY = new NamespacedKey(Origin.getPlugin(), "custom_arrow");

    public static ItemStack getTeleportBow() {
        ItemStack bow = new ItemStack(Material.BOW);
        bow.editMeta(meta -> {
            meta.displayName(toComponent(config.getString("teleport-bow.display-name")).decoration(TextDecoration.ITALIC, false));
            meta.getPersistentDataContainer().set(CUSTOME_ITEM_KEY, PersistentDataType.STRING, "teleportBow");
            meta.getPersistentDataContainer().set(TYPE_KEY, PersistentDataType.STRING, "teleportBow");
            meta.setUnbreakable(config.getBoolean("teleport-bow.unbreakable"));
        });
        if (config.getBoolean("teleport-bow.show-enchant")) {
            bow.addEnchantment(Enchantment.MENDING, 1);
            bow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            bow.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            bow.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }


        return bow;
    }

    public static ItemStack getArrow() {
        ItemStack arrow = new ItemStack(Material.ARROW);
        arrow.editMeta(meta -> {
            meta.displayName(toComponent(config.getString("arrow.display-name")).decoration(TextDecoration.ITALIC, false));
            meta.getPersistentDataContainer().set(CUSTOME_ITEM_KEY, PersistentDataType.STRING, "arrow");
            meta.getPersistentDataContainer().set(TYPE_KEY, PersistentDataType.STRING, "teleportBowArrow");
        });



        return arrow;
    }


    public static Component toComponent(String content) {
        return ComponentUtils.component(content);
    }


}
