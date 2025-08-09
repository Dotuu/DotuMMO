package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Managers.SettingsManager;

public class SettingsConfig extends JsonFileManager{
    public static HashMap<ConfigEnum.Settings, SettingsManager> settingsMap = new HashMap<>();

    public SettingsConfig(JavaPlugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "settings.json"), "configs");

        this.createFileIfNotExists("settings.json");

        this.setupDefaults(Arrays.asList(
            ConfigEnum.Type.SETTINGS
        ));
    }
    
    public void reloadConfig(){
        settingsMap.clear();
        this.loadFromFile();
    }

    @Override
    protected void loadFromFile(){
        JsonObject read = null;
        try (FileReader reader = new FileReader(this.file)){
            read = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
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

    @Override
    public void saveToFile(){
        JsonObject settings = new JsonObject();

        for (Map.Entry<ConfigEnum.Settings, SettingsManager> entry : settingsMap.entrySet()) {
            ConfigEnum.Settings key = entry.getKey();
            SettingsManager manager = entry.getValue();
            settings.add(key.toString().toLowerCase(), manager.getSettings());

        }

        JsonObject root = new JsonObject();
        root.add("Settings", settings);

        try(FileWriter writer = new FileWriter(this.file)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        }
        catch(Exception e){

        }
    }
}
