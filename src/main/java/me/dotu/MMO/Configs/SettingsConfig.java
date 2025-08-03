package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

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
    private final String filename = "settings.json";
    public static HashMap<ConfigEnum.Settings, SettingsManager> settingsMap = new HashMap<>();

    public SettingsConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(this.plugin.getDataFolder(), this.filename);
        if (!this.configFile.exists()) {
            this.setupDefaults();
        }
        else{
            this.loadSettingsFromFile();
        }
    }

    public File getConfig() {
        return this.configFile;
    }

    public void reloadConfig(){
        settingsMap.clear();
        this.loadSettingsFromFile();
    }

    private void loadSettingsFromFile(){
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
                settingsName = name.toString().toLowerCase();
                if (settingsJson.has(settingsName)){
                    JsonObject settings = settingsJson.getAsJsonObject(settingsName);
                    settingsMap.put(name, new SettingsManager(settings));
                }
            }
        }
    }

    private void setupDefaults() {
        File parentDir = this.configFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        JsonObject defaultConfig = new JsonObject();

        ConfigEnum.Type.SETTINGS.populate(defaultConfig);

        try (FileWriter writer = new FileWriter(this.configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaultConfig, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.loadSettingsFromFile();
    }

    public void saveSettingsToFile(){
        JsonObject settings = new JsonObject();

        for (Map.Entry<ConfigEnum.Settings, SettingsManager> entry : settingsMap.entrySet()) {
            ConfigEnum.Settings key = entry.getKey();
            SettingsManager manager = entry.getValue();
            settings.add(key.toString().toLowerCase(), manager.getSettings());

        }

        JsonObject root = new JsonObject();
        root.add("Settings", settings);

        try(FileWriter writer = new FileWriter(this.configFile)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        }
        catch(Exception e){

        }
    }
}
