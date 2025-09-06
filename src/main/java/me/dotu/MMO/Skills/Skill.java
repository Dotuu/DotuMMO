package me.dotu.MMO.Skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.dotu.MMO.Configs.ItemTableConfig;
import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.PlayerSettings;
import me.dotu.MMO.Enums.Settings;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Managers.PlayerManager;
import me.dotu.MMO.Managers.SettingsManager;
import me.dotu.MMO.Managers.SkillsManager;
import me.dotu.MMO.Tables.ItemSource;
import me.dotu.MMO.Tables.ItemTable;
import me.dotu.MMO.UI.ExpBar;
import me.dotu.MMO.Utils.ExpCalculator;
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

    public boolean sourceExists(ItemSource<?> source){
        return source.getTableSource() != null;
    }

    public boolean isRequiredLevel(ItemSource<?> source, Player player, String skillName){
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

    public ItemSource<?> getSourceBlock(Block block, Player player){
        ItemTable<?> itemTable = this.getItemTable(this.name.toUpperCase());

        if (itemTable ==  null){
            return null;
        }

        if (itemTable.isMaterialTable()){
            ArrayList<ItemSource<?>> expItems = itemTable.getExpItems();

            for (ItemSource<?> item : expItems){
                Material sourceMaterial = (Material) item.getTableSource();
                if (block.getType() == sourceMaterial){
                    return item;
                }
            }
        }
        return null;
    }

    public ItemSource<?> getExpSourceMaterial(Material material, Player player){
        ItemTable<?> itemTable = this.getItemTable(this.name.toUpperCase());

        if (itemTable ==  null){
            return null;
        }

        if (itemTable.isMaterialTable()){
            ArrayList<ItemSource<?>> expItems = itemTable.getExpItems();

            for (ItemSource<?> item : expItems){
                Material sourceMaterial = (Material) item.getTableSource();
                if (sourceMaterial == material){
                    return item;
                }
            }
        }
        return null;
    }

    public ItemSource<?> getExpSourceEntity(Entity entity, Player player){
        ItemTable<?> itemTable = this.getItemTable(this.name.toUpperCase());

        if (itemTable ==  null){
            return null;
        }
        
        if (itemTable.isMaterialTable()){
            ArrayList<ItemSource<?>> expItems = itemTable.getExpItems();

            for (ItemSource<?> e : expItems){
                EntityType entityType = (EntityType) e.getTableSource();
                if (entity.getType() == entityType){
                    return e;
                }
            }
        }
        return null;
    }

    public ItemTable<?> getItemTable(String key){
        return ItemTableConfig.itemTables.get(key);
    }

    public void processExpReward(Player player, Skill skill, int minExp, int maxExp, int multiplier) {
        UUID uuid = player.getUniqueId();

        int xpGained = ExpCalculator.calculateRewardedExp(skill.getDifficulty(), minExp, maxExp, multiplier);
        SkillsManager skillsManager = new SkillsManager();
        skillsManager.setSkills(uuid, skill.getSkill().toString().toUpperCase(), xpGained);

        String msg = MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + skill.getName());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
        ExpBar.setExpBarToSkill(player, skillsMap.get(skill.getSkill()));
    }
}
