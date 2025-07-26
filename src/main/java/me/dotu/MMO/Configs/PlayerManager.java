package me.dotu.MMO.Configs;

import com.google.gson.JsonObject;

public class PlayerManager {

    JsonObject config;

    public PlayerManager(JsonObject config){
        this.config = config;
    }

    public JsonObject getSkills(){
        if (this.config != null && config.has("stats") && config.get("stats").isJsonObject()){
            return config.getAsJsonObject("stats");
        }
        else{
            return null;
        }
    }

    public void setSkills(String skillName, int xpGained){
        if (this.config != null && this.config.has("Data")){
            JsonObject data = this.config.getAsJsonObject("Data");
            if (data.has("skills")){
                JsonObject skills = data.getAsJsonObject("skills");

                if (skills.has("xp")){
                    JsonObject xp = skills.getAsJsonObject("xp");

                    int currentXp = xp.has(skillName) ? xp.get(skillName).getAsInt() : 0;
                    xp.addProperty(skillName, currentXp + xpGained);
                }
            }
        }
    }

    public JsonObject getConfig(){
        return this.config;
    }
}
