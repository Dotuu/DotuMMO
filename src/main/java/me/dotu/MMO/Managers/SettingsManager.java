package me.dotu.MMO.Managers;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.Settings;

public class SettingsManager {
    private JsonObject settings;

    public SettingsManager(JsonObject settings) {
        this.settings = settings;
    }

    public JsonObject getSettings() {
        return this.settings;
    }

    public void setSettings(JsonObject settings) {
        this.settings = settings;
    }
    

    public boolean getSettingsBoolean (Settings key, String prop, boolean defaultValue){
        SettingsManager manager = SettingsConfig.settingsMap.get(key);
        if (manager != null){
            JsonObject obj = manager.getSettings();
            return obj.has(prop) ? obj.get(prop).getAsBoolean() : defaultValue;
        }
        return defaultValue;
    }

    public int getSettingsInt(Settings key, String prop, int defaultValue){
        SettingsManager manager = SettingsConfig.settingsMap.get(key);
        if (manager != null){
            JsonObject obj = manager.getSettings();
            return obj.has(prop) ? obj.get(prop).getAsInt() : defaultValue;
        }
        return defaultValue;
    }

    public String getSettingsString(Settings key, String prop, String defaultValue){
        SettingsManager manager = SettingsConfig.settingsMap.get(key);
        if (manager != null){
            JsonObject obj = manager.getSettings();
            return obj.has(prop) ? obj.get(prop).getAsString() : defaultValue;
        }
        return defaultValue;
    }

    public long getSettingsLong(Settings key, String prop, long defaultValue){
        SettingsManager manager = SettingsConfig.settingsMap.get(key);
        if (manager != null){
            JsonObject obj = manager.getSettings();
            return obj.has(prop) ? obj.get(prop).getAsLong() : defaultValue;
        }
        return defaultValue;
    }

    public void setSettingsLong(Settings key, String prop, long value){
        SettingsManager manager = SettingsConfig.settingsMap.get(key);
        if (manager != null){
            JsonObject obj = manager.getSettings();
            obj.addProperty(prop, value);
        }
    }
}
