package me.dotu.MMO.Enums;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnerEnum{
    public static enum SpawnerKey{
        ROOT("DotuMMO_Spawner"),
        MAX_HEALTH("maxHealth"),
        MIN_HEALTH("minHealth"),
        ARMORED("armored"),
        WEAPONED("weaponed"),
        LEVEL("level"),
        DIFFICULTY("difficulty"),
        NAME_VISIBLE("nameVisible"),
        SPAWN_RANDOMLY("spawnRandomly"),
        NAME("name");

        private final String key;

        SpawnerKey(String key){
            this.key = key;
        }

        public NamespacedKey getKey(JavaPlugin plugin){
            return new NamespacedKey(plugin, this.key);
        }
    }

    public static enum Difficulty{
        EASY(10),
        NORMAL(25),
        HARD(40),
        VERY_HARD(60),
        BRUTAL(75),
        IMPOSSIBLE(100);

        private final double difficulty;

        Difficulty(double difficulty){
            this.difficulty = difficulty;
        }

        public double getDifficultyValue(){
            return this.difficulty;
        }

        public static Difficulty fromValue(double value){
            for (Difficulty d : values()){
                if (d.difficulty == value){
                    return d;
                }
            }
            return NORMAL;
        }
    }
}