package me.dotu.MMO.Enums;

import org.bukkit.NamespacedKey;

import me.dotu.MMO.Main;

public enum SpawnerKey {
    ROOT("DotuMMO_Spawner"),
    SPAWNER_ID("spawner_id");

    private final String key;

    SpawnerKey(String key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return new NamespacedKey(Main.plugin, this.key);
    }
}