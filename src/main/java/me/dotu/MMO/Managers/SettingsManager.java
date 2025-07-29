package me.dotu.MMO.Managers;

import com.google.gson.JsonObject;

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

    public boolean getEnabled() {
        // add enabled logic here
        return false;
    }

    public int getMaxLevel() {
        return 0;
    }

    public int getStartingLevel() {
        return 0;
    }
}
