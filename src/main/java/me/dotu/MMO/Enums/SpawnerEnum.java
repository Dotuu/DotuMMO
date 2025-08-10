package me.dotu.MMO.Enums;

import org.bukkit.NamespacedKey;

import me.dotu.MMO.Main;

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
        NAME("name"),
        TABLE("table");

        private final String key;

        SpawnerKey(String key){
            this.key = key;
        }

        public NamespacedKey getKey(){
            return new NamespacedKey(Main.plugin, this.key);
        }
    }
}