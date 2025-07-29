package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.SettingsManager;

public class SettingsConfig {
    private final File configFile;
    private final JavaPlugin plugin;
    private final String filename = "Settings.json";
    public static HashMap<ConfigEnum.Settings, SettingsManager> settingsMap = new HashMap<>();

    public SettingsConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(this.plugin.getDataFolder(), this.filename);
        if (!this.configFile.exists()) {
            this.setupDefaults();
        }
        else{
            this.populateSettingsMap();
        }
    }

    public File getConfig() {
        return this.configFile;
    }

    public void reloadConfig(){
        this.settingsMap.clear();
        this.populateSettingsMap();
    }

    private void populateSettingsMap(){
        JsonObject read = null;
        try (FileReader reader = new FileReader(this.configFile)){
            read = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (read != null){
            JsonObject settingsJson = read.getAsJsonObject("Settings");
            String settingsName;
            for (ConfigEnum.Settings name : ConfigEnum.Settings.values()){
                settingsName = name.toString().toLowerCase().replace("_", " ");
                if (settingsJson.has(settingsName)){
                    JsonObject settings = settingsJson.getAsJsonObject(settingsName);
                    settingsMap.put(name, new SettingsManager(settings));
                }
            }
        }
    }

    private void setupDefaults() {
        System.out.println("DotuMMO - Settings: Running first time setup");
        File parentDir = this.configFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        JsonObject defaultConfig = new JsonObject();

        ConfigEnum.Type.SETTINGS.populate(defaultConfig);

        try (FileWriter writer = new FileWriter(this.configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaultConfig, writer);
            this.populateSettingsMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
