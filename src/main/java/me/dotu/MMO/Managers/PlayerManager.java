package me.dotu.MMO.Managers;

import java.util.UUID;

import org.bukkit.event.Listener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Enums.PlayerSettings;

public class PlayerManager implements Listener{

    JsonObject config;

    public PlayerManager(JsonObject config){
        this.config = config;
    }

    public int getSettingsInt(UUID uuid, PlayerSettings key, String prop, int defaultValue){
        PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
        if (manager != null){
            JsonObject data = getOrCreateObject(manager.getSettings(), "Data");
            JsonObject obj = getOrCreateObject(data, key.toString().toLowerCase());
            return obj.has(prop) ? obj.get(prop).getAsInt() : defaultValue;
        }
        return defaultValue;
    }

    public void setSettingsInt(UUID uuid, PlayerSettings key, String prop, int value){
        PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
        if (manager != null){
            JsonObject data = getOrCreateObject(manager.getSettings(), "Data");
            JsonObject obj = getOrCreateObject(data, key.toString().toLowerCase());
            obj.addProperty(prop, value);
        }
    }

    public String getSettingsString(UUID uuid, PlayerSettings key, String prop, String defaultValue){
        PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
        if (manager != null){
            JsonObject data = getOrCreateObject(manager.getSettings(), "Data");
            JsonObject obj;
            obj = getOrCreateObject(data, key.toString().toLowerCase());
            return obj.has(prop) ? obj.get(prop).getAsString() : defaultValue;
        }
        return defaultValue;
    }

    public long getSettingsLong(UUID uuid, PlayerSettings key, String prop, long defaultValue){
        PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
        if (manager != null){
            JsonObject data = getOrCreateObject(manager.getSettings(), "Data");
            JsonObject obj = getOrCreateObject(data, key.toString().toLowerCase());
            return obj.has(prop) ? obj.get(prop).getAsLong() : defaultValue;
        }
        return defaultValue;
    }

    public void setSettingsLong(UUID uuid, PlayerSettings key, String prop, long value){
        PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
        if (manager != null){
            JsonObject data = getOrCreateObject(manager.getSettings(), "Data");
            JsonObject obj = getOrCreateObject(data, key.toString().toLowerCase());
            obj.addProperty(prop, value);
        }
    }

    public JsonObject getSettings(){
        return this.config;
    }

    public static JsonObject getOrCreateObject(JsonObject parent, String key){
        if (!parent.has(key) || !parent.get(key).isJsonObject()){
            JsonObject object = new JsonObject();
            parent.add(key, object);
            return object;
        }
        return parent.getAsJsonObject(key);
    }

    public static JsonElement getOrCreateElement(JsonObject parent, String key, JsonElement defaultValue) {
        if (!parent.has(key)) {
            parent.add(key, defaultValue);
            return defaultValue;
        }
        return parent.get(key);
    }
}
