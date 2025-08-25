package me.dotu.MMO.Enums;

import org.bukkit.NamespacedKey;

import me.dotu.MMO.Main;

public enum SpawnerKey {
    ROOT("DotuMMO_Spawner"),
    ARMORED("armored"),
    WEAPONED("weaponed"),
    MIN_LEVEL("minLevel"),
    MAX_LEVEL("maxLevel"),
    DIFFICULTY("difficulty"),
    NAME_VISIBLE("nameVisible"),
    SPAWN_RANDOMLY("spawnRandomly"),
    NAME("name"),
    TABLE("table"),
    MIN_SPAWN_DELAY("minSpawnDelay"),
    MAX_SPAWN_DELAY("maxSpawnDelay"),
    SPAWN_RANGE("spawnRange");

    private final String key;

    SpawnerKey(String key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return new NamespacedKey(Main.plugin, this.key);
    }
}