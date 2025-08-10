package me.dotu.MMO.Entities;

public class CustomSpawner {

    private int level;
    double difficulty;
    private boolean armored;
    private boolean weaponed;
    private boolean nameVisible;
    private boolean spawnRandomly;
    private String name;
    private String table;

    public CustomSpawner(int level, double difficulty, boolean armored, boolean weaponed, boolean nameVisible, boolean spawnRandomly, String name, String table){
        this.level = level;
        this.difficulty = difficulty;
        this.armored = armored;
        this.weaponed = weaponed;
        this.nameVisible = nameVisible;
        this.spawnRandomly = spawnRandomly;
        this.name = name;
        this.table = table;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
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
}
