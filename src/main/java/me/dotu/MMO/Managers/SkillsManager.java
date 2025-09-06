package me.dotu.MMO.Managers;

import java.util.UUID;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Enums.SkillType;

public class SkillsManager {

    public SkillsManager() {
    }

    public JsonObject getSkills(UUID uuid) {
        try {
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            return data.getAsJsonObject("skills");
        } catch (Exception e) {
            return null;
        }
    }

    public void setSkills(UUID uuid, String skillName, int xpGained) {
        try {
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject skills = PlayerManager.getOrCreateObject(data, "skills");

            int currentExp = skills.has(skillName) ? skills.get(skillName).getAsInt() : 0;
            skills.addProperty(skillName, currentExp + xpGained);
        } catch (Exception e) {

        }
    }
    
    public int getSkillExp(UUID uuid, SkillType skillEnum) {
        try {
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject skills = PlayerManager.getOrCreateObject(data, "skills");

            String skillName = skillEnum.toString().replace("_", " ");
            return skills.has(skillName) ? skills.get(skillName).getAsInt() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

}
