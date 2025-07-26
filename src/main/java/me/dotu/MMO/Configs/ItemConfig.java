package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileWriter;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import me.dotu.MMO.Enums.ConfigEnum;

public class ItemConfig {

    private final File configFile;
    private final JavaPlugin plugin;
    private final String filename = "ItemConfig.json";

    public ItemConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(this.plugin.getDataFolder(), this.filename);

        if (!this.configFile.exists()) {
            this.setupDefaults();
        }
    }

    public File getConfig() {
        return this.configFile;
    }

    private void setupDefaults() {
        System.out.println("DotuMMO - Item config: Running first time setup");
        File parentDir = this.configFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        JsonObject defaultConfig = new JsonObject();

        ConfigEnum.Type.TOOLS.populate(defaultConfig);
        ConfigEnum.Type.ARMORS.populate(defaultConfig);
        ConfigEnum.Type.WEAPONS.populate(defaultConfig);

        try (FileWriter writer = new FileWriter(this.configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaultConfig, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
