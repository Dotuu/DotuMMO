package me.dotu.MMO.Managers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.dotu.MMO.Enums.SkillEnum;

public class PlayerManager {

    JsonObject config;

    public PlayerManager(JsonObject config){
        this.config = config;
    }

    public JsonObject getSkills(){
        if (this.config != null && config.has("Data")){
            JsonObject data = config.getAsJsonObject("Data");
            JsonObject skills = data.getAsJsonObject("skills");
            return skills.getAsJsonObject("xp");
        }
        else{
            return null;
        }
    }

    public int getSkillExp(SkillEnum.Skill skillEnum){
        if (this.config != null && this.config.has("Data")){
            JsonObject data = config.getAsJsonObject("Data");
            JsonObject skills = data.getAsJsonObject("skills");
            JsonObject xp = skills.getAsJsonObject("xp");

            String skillName = skillEnum.toString().replace("_", " ");
            if (xp.has(skillName)){
                JsonElement skillJson = xp.get(skillName);
                return skillJson.getAsInt();
            }
            else{
                return 0;
            }
        }
        else{
            return 0;
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
