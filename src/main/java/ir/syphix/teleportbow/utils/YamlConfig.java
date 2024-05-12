package ir.syphix.teleportbow.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.sayandev.stickynote.bukkit.StickyNote;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class YamlConfig {

    private final String fileName;

    private FileConfiguration config = null;
    final File folder;
    private File configFile = null;

    public YamlConfig(File folder, String fileName) {
        this.folder = folder;

        this.fileName = fileName;

        saveDefaultConfig();
    }

    public YamlConfig(File folder, String fileName, boolean saveDefaultConfig) {
        this.folder = folder;

        this.fileName = fileName;

        if (saveDefaultConfig) {
            saveDefaultConfig();
        }
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(folder, fileName);

        this.config = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = StickyNote.plugin().getResource(fileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.config.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.config == null)
            reloadConfig();
        return this.config;
    }

    public void saveConfig() {
        if (this.config == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            StickyNote.plugin().getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null)
            this.configFile = new File(folder, fileName);

        if (!(this.configFile.exists())) {
            try {
                StickyNote.plugin().saveResource(fileName, false);
            } catch (IllegalArgumentException e) {
                try {
                    this.configFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public String getFileName() {
        return fileName;
    }
}
