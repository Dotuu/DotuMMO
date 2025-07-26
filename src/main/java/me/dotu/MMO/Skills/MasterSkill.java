package me.dotu.MMO.Skills;

import me.dotu.MMO.Enums.SkillEnum;

public class MasterSkill {
    private SkillEnum.Skill skill;
    private int id;
    private SkillEnum.Difficulty difficulty;
    private int maxLevel;
    private int startingLevel;
    
    public MasterSkill(SkillEnum.Difficulty difficulty, SkillEnum.Skill skill, int id, int maxLevel, int startingLevel) {
        this.skill = skill;
        this.id = id;
        this.difficulty = difficulty;
        this.maxLevel = maxLevel;
        this.startingLevel = startingLevel;
    }
        
    public int getId() {
        return this.id;
    }
        
    public void setId(int id) {
        this.id = id;
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
}
