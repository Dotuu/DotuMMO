package me.dotu.MMO.Skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Managers.SkillsManager;
import me.dotu.MMO.UI.ExpBar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Skill {
    private String name;
    private SkillEnum.Skill skill;
    private SkillEnum.Difficulty difficulty;
    private int maxLevel;
    private int startingLevel;
    
    public static HashMap<SkillEnum.Skill, Skill> skillsMap = new HashMap<>();

    protected Skill(String name, SkillEnum.Difficulty difficulty, SkillEnum.Skill skill, int maxLevel, int startingLevel) {
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
        
    public SkillEnum.Difficulty getDifficulty() {
        return this.difficulty;
    }
        
    public void setDifficulty(SkillEnum.Difficulty difficulty) {
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

    public SkillEnum.Skill getSkill() {
        return this.skill;
    }

    public void setSkill(SkillEnum.Skill skill) {
        this.skill = skill;
    }

    public void processExpReward(Player player, Skill skill, int xpReward){
        UUID uuid = player.getUniqueId();

        int xpGained = ExpCalculator.calculateRewardedExp(skill.getDifficulty(), xpReward);
        SkillsManager skillsManager = new SkillsManager();
        skillsManager.setSkills(uuid, skill.getSkill().toString(), xpGained);

        String msg = MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + skill.getName());
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
        ExpBar.setExpBarToSkill(player, skillsMap.get(skill.getSkill()));
    }
}
