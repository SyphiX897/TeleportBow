package ir.syphix.teleportbow.data;

import ir.syphix.teleportbow.file.FileManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class DataManager extends FileManager {

    private static final FileConfiguration settings = SettingsFile.get();

    public static class IsEnable {
        public static boolean giveOnJoin() {
            return settings.getBoolean("give_on_join");
        }

        public static boolean giveOnRespawn() {
            return settings.getBoolean("give_on_respawn");
        }

        public static boolean dropOnDeath() {
            return settings.getBoolean("drop_on_death");
        }

        public static boolean teleportToEntity() {
            return settings.getBoolean("teleport_to_entity");
        }

        public static boolean teleportToBlock() {
            return settings.getBoolean("teleport_to_block");
        }
    }

    public static class Bow {
        public static boolean isInfinityArrow() {
            return settings.getBoolean("bow.infinity_arrow");
        }

        //Bow itemStack
        public static String displayName() {
            return settings.getString("bow.item.display_name");
        }

        public static List<String> lore() {
            return settings.getStringList("bow.item.lore");
        }

        public static boolean enchantmentParticle() {
            return settings.getBoolean("bow.item.enchantment_particle");
        }

        public static boolean isUnbreakable() {
            return settings.getBoolean("bow.item.unbreakable");
        }

        public static int slot() {
            return settings.getInt("bow.item.slot");
        }
    }

    public static class Arrow {
        public static boolean isGlowing() {
            return settings.getBoolean("arrow.glowing");
        }

        public static boolean noDamage() {
            return settings.getBoolean("arrow.no_damage");
        }
        //Arrow itemStack
        public static String displayName() {
            return settings.getString("arrow.item.display_name");
        }

        public static List<String> lore() {
            return settings.getStringList("arrow.item.lore");
        }

        public static boolean enchantmentParticle() {
            return settings.getBoolean("arrow.item.enchantment_particle");
        }

        public static int slot() {
            return settings.getInt("arrow.item.slot");
        }

        //Arrow hologram
        public static boolean isHologramEnable() {
            return settings.getBoolean("arrow.hologram.enabled");
        }

        public static String hologramText() {
            return settings.getString("arrow.hologram.text");
        }

        //Arrow particle on hitting entity
        public static boolean isHitParticleOnHittingEntityEnable() {
            return settings.getBoolean("arrow.hit_particle.on_hitting_entity.enabled");
        }

        public static String hitParticleOnHittingEntityName() {
            return settings.getString("arrow.hit_particle.on_hitting_entity.particle");
        }

        public static double hitParticleOnHittingEntityRadius() {
            return settings.getDouble("arrow.hit_particle.on_hitting_entity.radius");
        }

        //Arrow particle on hitting block
        public static boolean isHitParticleOnHittingBlockEnable() {
            return settings.getBoolean("arrow.hit_particle.on_hitting_block.enabled");
        }

        public static String hitParticleOnHittingBlockName() {
            return settings.getString("arrow.hit_particle.on_hitting_block.particle");
        }

        public static double hitParticleOnHittingBlockRadius() {
            return settings.getDouble("arrow.hit_particle.on_hitting_block.radius");
        }

        //Arrow sound on hitting entity
        public static boolean isHitSoundOnHittingEntityEnable() {
            return settings.getBoolean("arrow.hit_sound.on_hitting_entity.enabled");
        }

        public static String hitSoundOnHittingEntityName() {
            return settings.getString("arrow.hit_sound.on_hitting_entity.sound");
        }

        //Arrow sound on hitting block
        public static boolean isHitSoundOnHittingBlockEnable() {
            return settings.getBoolean("arrow.hit_sound.on_hitting_block.enabled");
        }

        public static String hitSoundOnHittingBlockName() {
            return settings.getString("arrow.hit_sound.on_hitting_block.sound");
        }

        //Arrow launch particle
        public static boolean isLaunchParticleEnable() {
            return settings.getBoolean("arrow.launch_particle.enabled");
        }

        public static String launchParticleName() {
            return settings.getString("arrow.launch_particle.particle");
        }

        //Arrow launch sound
        public static boolean isLaunchSoundEnabled() {
            return settings.getBoolean("arrow.launch_sound.enabled");
        }

        public static String launchSoundName() {
            return settings.getString("arrow.launch_sound.sound");
        }
    }
}
