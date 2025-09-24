package me.dotu.MMO.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Configs.SkillTableConfig;
import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Enums.PlayerSettings;
import me.dotu.MMO.Enums.Settings;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Managers.PlayerManager;
import me.dotu.MMO.Managers.SettingsManager;
import me.dotu.MMO.Managers.SkillsManager;
import me.dotu.MMO.Tables.SkillSource;
import me.dotu.MMO.Tables.SkillTable;
import me.dotu.MMO.UI.ExpBar;
import me.dotu.MMO.Utils.ExpCalculator;

public abstract class Skill {
    private String name;
    private SkillType skill;
    private SkillDifficulty difficulty;
    private int maxLevel;
    private int startingLevel;

    public static HashMap<SkillType, Skill> skillsMap = new HashMap<>();

    protected Skill(String name, SkillDifficulty difficulty, SkillType skill, int maxLevel, int startingLevel) {
        this.skill = skill;
        this.difficulty = difficulty;
        this.maxLevel = maxLevel;
        this.startingLevel = startingLevel;
        this.name = name;
    }

    public void addToSkillsMap(Skill skill) {
        skillsMap.put(skill.getSkill(), skill);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SkillDifficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(SkillDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getStartingLevel() {
        return this.startingLevel;
    }

    public void setStartingLevel(int startingLevel) {
        this.startingLevel = startingLevel;
    }

    public SkillType getSkill() {
        return this.skill;
    }

    public void setSkill(SkillType skill) {
        this.skill = skill;
    }

    protected boolean isSkillEnabled(String skillName){
        SettingsManager enabledSkills = SettingsConfig.settingsMap.get(Settings.ENABLED_SKILLS);
        return enabledSkills.getSettingsBoolean(Settings.ENABLED_SKILLS, skillName, true);
    }

    protected boolean meetsRequirements(){
        return true;
    }

    public boolean sourceExists(SkillSource<?> source){
        return source.getTableSource() != null;
    }

    public boolean isRequiredLevel(SkillSource<?> source, Player player, String skillName){
        PlayerManager playerSettings = PlayerConfig.playerSettings.get(player.getUniqueId());
        
        if (playerSettings == null){
            player.sendMessage("No player settings file! Please report this to an ADMIN");
            return false;
        }

        int skillLevelExp = playerSettings.getSettingsInt(player.getUniqueId(), PlayerSettings.SKILLS, skillName, 1);
        int skillLevel = ExpCalculator.getLevelFromExp(skillLevelExp, this.difficulty);
        int requiredLevel = source.getRequiredLevel();

        return skillLevel >= requiredLevel;
    }

    public SkillSource<?> getSourceBlock(Block block, Player player){
        SkillTable<?> itemTable = this.getItemTable(this.name.toUpperCase());

        if (itemTable ==  null){
            return null;
        }

        if (itemTable.isMaterialTable()){
            ArrayList<SkillSource<?>> expItems = itemTable.getExpItems();

            for (SkillSource<?> item : expItems){
                Material sourceMaterial = (Material) item.getTableSource();
                if (block.getType() == sourceMaterial){
                    return item;
                }
            }
        }
        return null;
    }

    public SkillSource<?> getExpSourceMaterial(Material material, Player player){
        SkillTable<?> itemTable = this.getItemTable(this.name.toUpperCase());

        if (itemTable ==  null){
            return null;
        }

        if (itemTable.isMaterialTable()){
            ArrayList<SkillSource<?>> expItems = itemTable.getExpItems();

            for (SkillSource<?> item : expItems){
                Material sourceMaterial = (Material) item.getTableSource();
                if (sourceMaterial == material){
                    return item;
                }
            }
        }
        return null;
    }

    public SkillSource<?> getExpSourceEntity(Entity entity, Player player){
        SkillTable<?> itemTable = this.getItemTable(this.name.toUpperCase());

        if (itemTable ==  null){
            return null;
        }
        
        if (itemTable.isMaterialTable()){
            ArrayList<SkillSource<?>> expItems = itemTable.getExpItems();

            for (SkillSource<?> e : expItems){
                EntityType entityType = (EntityType) e.getTableSource();
                if (entity.getType() == entityType){
                    return e;
                }
            }
        }
        return null;
    }

    public SkillTable<?> getItemTable(String key){
        return SkillTableConfig.itemTables.get(key);
    }

    public void processExpReward(Player player, Skill skill, int minExp, int maxExp, int multiplier) {
        UUID uuid = player.getUniqueId();

        int xpGained = ExpCalculator.calculateRewardedExp(skill.getDifficulty(), minExp, maxExp, multiplier);
        SkillsManager skillsManager = new SkillsManager();
        skillsManager.setSkills(uuid, skill.getSkill().toString().toUpperCase(), xpGained);

        MessageManager.sendActionBar(player, Messages.SKILL_SUCCESS, true, xpGained, skill.getName());
        ExpBar.setExpBarToSkill(player, skillsMap.get(skill.getSkill()));
    }
}
