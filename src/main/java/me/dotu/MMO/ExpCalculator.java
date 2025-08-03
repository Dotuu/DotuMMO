package me.dotu.MMO;

import java.util.UUID;

import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.Managers.PvpManager;

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

    public static int calculatePvpExp(UUID uuid){
        PvpManager pvpManager = new PvpManager();
        double kdr = pvpManager.getKdr(uuid);

        double xp = baseXp * Math.log10(kdr + 1);
        return (int) Math.max(xp, baseXp);
    }
}
