package me.dotu.MMO.Configs;

import com.google.gson.JsonObject;

public class Settings {
    private JsonObject settings;

    public Settings(JsonObject settings){
        this.settings = settings;
    }

    public JsonObject getSettings() {
        return this.settings;
    }

    public void setSettings(JsonObject settings) {
        this.settings = settings;
    }

    public boolean getEnabled(){
        // add enabled logic here
        return false;
    }

    public int getMaxLevel(){
        return 0;
    }

    public int getStartingLevel(){
        return 0;
    }
}
