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

import java.util.ArrayList;
import java.util.List;


public class Items {

    static FileConfiguration config = Origin.getPlugin().getConfig();

    public static NamespacedKey CUSTOME_ITEM_KEY = new NamespacedKey(Origin.getPlugin(), "custome_item");
    public static NamespacedKey TYPE_KEY = new NamespacedKey(Origin.getPlugin(), "type");
    public static NamespacedKey ARROW_ENTITY_KEY = new NamespacedKey(Origin.getPlugin(), "custom_arrow");

    public static ItemStack getTeleportBow() {
        ItemStack bow = new ItemStack(Material.BOW);
        List<Component> lore = new ArrayList<>();
        for (String configLore: config.getStringList("teleport-bow.lore")) {
            lore.add(toComponent(configLore).decoration(TextDecoration.ITALIC, false));
        }
        bow.editMeta(meta -> {
            meta.displayName(toComponent(config.getString("teleport-bow.display-name")).decoration(TextDecoration.ITALIC, false));
            meta.getPersistentDataContainer().set(CUSTOME_ITEM_KEY, PersistentDataType.STRING, "teleportBow");
            meta.getPersistentDataContainer().set(TYPE_KEY, PersistentDataType.STRING, "teleportBow");
            meta.setUnbreakable(config.getBoolean("teleport-bow.unbreakable"));
            meta.lore(lore);
        });
        if (config.getBoolean("teleport-bow.enchantment-particle")) {
            bow.addEnchantment(Enchantment.MENDING, 1);
            bow.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            bow.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            bow.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        }


        return bow;
    }

    public static ItemStack getArrow() {
        ItemStack arrow = new ItemStack(Material.ARROW);
        List<Component> lore = new ArrayList<>();
        for (String configLore: config.getStringList("arrow.lore")) {
            lore.add(toComponent(configLore).decoration(TextDecoration.ITALIC, false));
        }
        arrow.editMeta(meta -> {
            meta.displayName(toComponent(config.getString("arrow.display-name")).decoration(TextDecoration.ITALIC, false));
            meta.getPersistentDataContainer().set(CUSTOME_ITEM_KEY, PersistentDataType.STRING, "arrow");
            meta.getPersistentDataContainer().set(TYPE_KEY, PersistentDataType.STRING, "teleportBowArrow");
            meta.lore(lore);
        });



        return arrow;
    }


    public static Component toComponent(String content) {
        return ComponentUtils.component(content);
    }


}
