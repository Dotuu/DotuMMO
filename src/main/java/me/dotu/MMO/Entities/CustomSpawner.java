package me.dotu.MMO.Entities;

import me.dotu.MMO.Enums.SpawnerEnum;

public class CustomSpawner {

    private int level;
    SpawnerEnum.Difficulty difficulty;
    private boolean armored;
    private boolean weaponed;
    private boolean nameVisible;
    private boolean spawnRandomly;
    private String name;

    public CustomSpawner(int level,SpawnerEnum.Difficulty difficulty, boolean armored, boolean weaponed, boolean nameVisible, boolean spawnRandomly, String name){
        this.level = level;
        this.difficulty = difficulty;
        this.armored = armored;
        this.weaponed = weaponed;
        this.nameVisible = nameVisible;
        this.spawnRandomly = spawnRandomly;
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public SpawnerEnum.Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(SpawnerEnum.Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isArmored() {
        return this.armored;
    }

    public void setArmored(boolean armored) {
        this.armored = armored;
    }

    public boolean isWeaponed() {
        return this.weaponed;
    }

    public void setWeaponed(boolean weaponed) {
        this.weaponed = weaponed;
    }

    public boolean isNameVisible() {
        return this.nameVisible;
    }

    public void setNameVisible(boolean nameVisible) {
        this.nameVisible = nameVisible;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSpawnRandomly() {
        return spawnRandomly;
    }

    public void setSpawnRandomly(boolean spawnRandomly) {
        this.spawnRandomly = spawnRandomly;
    }
}
