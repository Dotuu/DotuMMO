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

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Enums.Settings;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Managers.SettingsManager;

public final class SettingsConfig extends JsonFileManager {
    public static HashMap<Settings, SettingsManager> settingsMap = new HashMap<>();

    public SettingsConfig() {
        super("configs", "dotummo");

        this.setupDefaults(Arrays.asList(DefaultConfig.SETTINGS));

        this.populateMap();
    }

    public void reloadConfig() {
        settingsMap.clear();
        this.populateMap();
    }

    @Override
    protected void populateMap() {
        JsonObject read = null;
        try (FileReader reader = new FileReader(this.file)) {
            read = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
        }

        if (read != null) {
            JsonObject settingsJson = read;
            String settingsName;
            for (Settings name : Settings.values()) {
                settingsName = name.toString().toLowerCase();
                if (settingsJson.has(settingsName)) {
                    JsonObject settings = settingsJson.getAsJsonObject(settingsName);
                    settingsMap.put(name, new SettingsManager(settings));
                }
            }
        }
    }

    @Override
    public void saveAllToFile() {
        JsonObject settings = new JsonObject();

        for (Map.Entry<Settings, SettingsManager> entry : settingsMap.entrySet()) {
            Settings key = entry.getKey();
            SettingsManager manager = entry.getValue();
            settings.add(key.toString().toLowerCase(), manager.getSettings());
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(settings, writer);
        } catch (Exception e) {

        }
    }
}
