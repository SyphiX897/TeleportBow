package ir.syphix.teleportbow.file;

import ir.syphix.teleportbow.utils.YamlConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

@SuppressWarnings("unused")
public class FileManager {

    public FileManager() {
        SettingsFile.add();
        MessagesFile.add();
    }

    public static class SettingsFile {
        private static YamlConfig settingsConfig;

        public static void add() {

            settingsConfig = new YamlConfig(StickyNote.pluginDirectory(), "settings.yml");
            settingsConfig.saveDefaultConfig();
        }

        public static FileConfiguration get() {
            return settingsConfig.getConfig();
        }

        public static void reload() {
            settingsConfig.reloadConfig();
        }

        public static void save() {
            settingsConfig.saveConfig();
        }
    }

    public static class MessagesFile {
        private static YamlConfig messageConfig;

        public static void add() {
            messageConfig = new YamlConfig(StickyNote.pluginDirectory(), "messages.yml");
            messageConfig.saveDefaultConfig();
        }

        public static FileConfiguration get() {
            return messageConfig.getConfig();
        }

        public static void reload() {
            messageConfig.reloadConfig();
        }

        public static void save() {
            messageConfig.saveConfig();
        }
    }
}
