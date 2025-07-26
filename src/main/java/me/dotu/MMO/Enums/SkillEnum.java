package me.dotu.MMO.Enums;

public class SkillEnum {
    public static enum Difficulty {
        FAST(0.5),
        NORMAL(1),
        SLOW(1.5),
        VERY_SLOW(2);

        private final double difficulty;

        Difficulty(double difficulty) {
            this.difficulty = difficulty;
        }

        public double getDifficultyValue(){
            return this.difficulty;
        }
    }

    public static enum Skill{
        FISHING,
        MINING,
        WOODCUTTING,
        AXE,
        SWORD,
        ARCHERY,
        MACE,
        FARMING,
        COOKING,
        POTION_CRAFTING,
        GEM_CRAFTING,
        ARMOR_CRAFTING,
        WEAPON_CRAFTING,
        BLOCK,
        UNARMED,
        TAMING
    }
}
