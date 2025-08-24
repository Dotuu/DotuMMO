package me.dotu.MMO.Configs;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Managers.SettingsManager;

public class SettingsConfig extends JsonFileManager{
    public static HashMap<ConfigEnum.Settings, SettingsManager> settingsMap = new HashMap<>();

    public SettingsConfig() {
        super("configs","dotummo");

        this.setupDefaults(Arrays.asList(
            ConfigEnum.Type.SETTINGS
        ));

        this.populateSettingsMap();
    }
    
    public void reloadConfig(){
        settingsMap.clear();
        this.populateSettingsMap();
    }

    @Override
    protected void populateSettingsMap(){
        JsonObject read = null;
        try (FileReader reader = new FileReader(this.file)){
            read = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
        }

        if (read != null){
            JsonObject settingsJson = read;
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
    public void saveAllSettingsToFile(){
        JsonObject settings = new JsonObject();

        for (Map.Entry<ConfigEnum.Settings, SettingsManager> entry : settingsMap.entrySet()) {
            ConfigEnum.Settings key = entry.getKey();
            SettingsManager manager = entry.getValue();
            settings.add(key.toString().toLowerCase(), manager.getSettings());

        }

        try(FileWriter writer = new FileWriter(this.file)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settings, writer);
        }
        catch(Exception e){

        }
    }
}
