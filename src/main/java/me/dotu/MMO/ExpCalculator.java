package me.dotu.MMO;

import me.dotu.MMO.Enums.SkillEnum;

public class ExpCalculator {

    private static final int baseXp = 100;

    public static int getExpNeededForNextLevel(int level, SkillEnum.Difficulty difficultyEnum){
        double difficulty = difficultyEnum.getDifficultyValue();
        return (int) (baseXp * Math.pow(level, difficulty));
    }

    public static int calculateRewardedExp(SkillEnum.Difficulty difficultyEnum, int obtainedXp){
        double difficulty = difficultyEnum.getDifficultyValue();
        double xpGained = obtainedXp / difficulty;
        return (int) xpGained;
    }

    public static int getLevelFromExp(int totalXp, SkillEnum.Difficulty difficultyEnum){
        int level = 1;
        while(true){
            int xpForNextLevel = getExpNeededForNextLevel(level + 1, difficultyEnum);
            if(totalXp < xpForNextLevel){ // If player/level xp is less than xp needed for next level break out and return 
                break;
            }
            level++;
        }
        return level;
    }
}
