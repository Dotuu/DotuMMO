package me.dotu.MMO.Enums;

public enum SkillDifficulty {
    FAST(0.5),
    NORMAL(1),
    SLOW(1.5),
    VERY_SLOW(2);

    private final double difficulty;

    SkillDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public double getDifficultyValue() {
        return this.difficulty;
    }
}
