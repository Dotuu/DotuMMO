package me.dotu.MMO.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.dotu.MMO.Configs.ExpTableConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.Settings;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Managers.SettingsManager;
import me.dotu.MMO.Managers.SkillsManager;
import me.dotu.MMO.Tables.ExpSource;
import me.dotu.MMO.Tables.ExpTable;
import me.dotu.MMO.UI.ExpBar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Skill {
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

    public ExpSource<?> getExpSourceBlock(Block block, Player player){
        ExpTable<?> expTable = this.getExpTable(this.name.toLowerCase());
        if (expTable.isMaterialTable()){
            ArrayList<ExpSource<?>> expItems = expTable.getExpItems();

            for (ExpSource<?> item : expItems){
                Material material = (Material) item.getTableSource();
                if (block.getType() == material){
                    this.processExpReward(player, this, item.getMinExp(), item.getMaxExp());
                    return item;
                }
            }
        }
        return null;
    }

    public ExpSource<?> getExpSourceEntity(Entity entity, Player player){
        ExpTable<?> expTable = this.getExpTable(this.name.toLowerCase());
        if (expTable.isMaterialTable()){
            ArrayList<ExpSource<?>> expItems = expTable.getExpItems();

            for (ExpSource<?> e : expItems){
                EntityType entityType = (EntityType) e.getTableSource();
                if (entity.getType() == entityType){
                    this.processExpReward(player, this, e.getMinExp(), e.getMaxExp());
                    return e;
                }
            }
        }
        return null;
    }

    public ExpTable<?> getExpTable(String key){
        return ExpTableConfig.expTables.get(key);
    }

    public void processExpReward(Player player, Skill skill, int minExp, int maxExp) {
        UUID uuid = player.getUniqueId();

        int xpGained = ExpCalculator.calculateRewardedExp(skill.getDifficulty(), minExp, maxExp);
        SkillsManager skillsManager = new SkillsManager();
        skillsManager.setSkills(uuid, skill.getSkill().toString(), xpGained);

        String msg = MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + skill.getName());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
        ExpBar.setExpBarToSkill(player, skillsMap.get(skill.getSkill()));
    }
}
