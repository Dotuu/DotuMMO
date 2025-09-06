package me.dotu.MMO.Utils;

import java.util.UUID;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Managers.PvpManager;

public class ExpCalculator {

    private static final int baseXp = 100;

    public static int getExpNeededForNextLevel(int level, SkillDifficulty difficultyEnum) {
        double difficulty = difficultyEnum.getDifficultyValue();
        return (int) (baseXp * Math.pow(level, difficulty));
    }

    public static int calculateRewardedExp(SkillDifficulty difficultyEnum, int minExp, int maxExp, int multiplier) {
        int obtainedExp = RandomNum.getRandom(minExp, minExp);
        double difficulty = difficultyEnum.getDifficultyValue();
        double xpGained = obtainedExp / difficulty;
        return (int) xpGained * multiplier;
    }

    public static int getLevelFromExp(int totalXp, SkillDifficulty difficultyEnum) {
        int level = 0;
        while (true) {
            int xpForNextLevel = getExpNeededForNextLevel(level + 1, difficultyEnum);
            if (totalXp < xpForNextLevel) {
                break;
            }
            level++;
        }
        return level;
    }

    public static int calculatePvpExp(UUID uuid) {
        PvpManager pvpManager = new PvpManager();
        double kdr = pvpManager.getKdr(uuid);

        double xp = baseXp * Math.log10(kdr + 1);
        return (int) Math.max(xp, baseXp);
    }
}
