package me.dotu.MMO.Managers;

import java.util.UUID;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Enums.SkillEnum;

public class SkillsManager {

    public SkillsManager(){
    }   

    public JsonObject getSkills(UUID uuid){
        try{
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject skills = PlayerManager.getOrCreateObject(data, "skills");
            return skills.getAsJsonObject("xp");
        }catch(Exception e){
            return null;
        }
    }

    public int getSkillExp(UUID uuid, SkillEnum.Skill skillEnum){
        try{
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject skills = PlayerManager.getOrCreateObject(data, "skills");
            JsonObject xp = PlayerManager.getOrCreateObject(skills, "xp");

            String skillName = skillEnum.toString().replace("_", " ");
            return xp.has(skillName) ? xp.get(skillName).getAsInt() : 0;
        }catch(Exception e){
            return 0;
        }
    }

    public void setSkills(UUID uuid, String skillName, int xpGained){
        try{
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject skills = PlayerManager.getOrCreateObject(data, "skills");
            JsonObject xp = PlayerManager.getOrCreateObject(skills, "xp");

            int currentExp = xp.has(skillName) ? xp.get(skillName).getAsInt() : 0;
            xp.addProperty(skillName, currentExp + xpGained);
        }catch(Exception e){

        }
    }
}
