package me.dotu.MMO.Entities;

public class CustomSpawner {

    private int minLevel;
    private int maxLevel;
    private double difficulty;
    private boolean armored;
    private boolean weaponed;
    private boolean nameVisible;
    private boolean spawnRandomly;
    private String name;
    private String table;
    private int minSpawnDelay;
    private int maxSpawnDelay;
    private int spawnRange;

    public CustomSpawner(int minLevel, int maxLevel, double difficulty, boolean armored, boolean weaponed, boolean nameVisible, boolean spawnRandomly, String name, String table, int minSpawnDelay, int maxSpawnDelay, int spawnRange){
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.difficulty = difficulty;
        this.armored = armored;
        this.weaponed = weaponed;
        this.nameVisible = nameVisible;
        this.spawnRandomly = spawnRandomly;
        this.name = name;
        this.table = table;
        this.minSpawnDelay = minSpawnDelay;
        this.maxSpawnDelay = maxSpawnDelay;
        this.spawnRange = spawnRange;
    }

    public int getMinLevel() {
        return this.minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public double getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(double difficulty) {
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

    public String getTable() {
        return this.table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getMinSpawnDelay() {
        return minSpawnDelay;
    }

    public void setMinSpawnDelay(int minSpawnDelay) {
        this.minSpawnDelay = minSpawnDelay;
    }

    public int getMaxSpawnDelay() {
        return maxSpawnDelay;
    }

    public void setMaxSpawnDelay(int maxSpawnDelay) {
        this.maxSpawnDelay = maxSpawnDelay;
    }

    public int getSpawnRange() {
        return spawnRange;
    }

    public void setSpawnRange(int spawnRange) {
        this.spawnRange = spawnRange;
    }
}
