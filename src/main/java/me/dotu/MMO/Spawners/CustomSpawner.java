package me.dotu.MMO.Spawners;

public class CustomSpawner {

    private int minLevel;
    private int maxLevel;
    private double difficulty;
    private boolean armored;
    private boolean weaponed;
    private boolean nameVisible;
    private boolean spawnRandomly;
    private String name;
    private String dropTable;
    private int spawnDelay;
    private int spawnRange;
    private int maxSpawnCount;
    private Long id;

    public CustomSpawner(int minLevel, int maxLevel, double difficulty, boolean armored, boolean weaponed, boolean nameVisible, boolean spawnRandomly, String name, String dropTable, int spawnDelay, int spawnRange, int maxSpawnCount, Long id){
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.difficulty = difficulty;
        this.armored = armored;
        this.weaponed = weaponed;
        this.nameVisible = nameVisible;
        this.spawnRandomly = spawnRandomly;
        this.name = name;
        this.dropTable = dropTable;
        this.spawnDelay = spawnDelay;
        this.spawnRange = spawnRange;
        this.maxSpawnCount = maxSpawnCount;
        this.id = id;
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
        return this.spawnRandomly;
    }

    public void setSpawnRandomly(boolean spawnRandomly) {
        this.spawnRandomly = spawnRandomly;
    }

    public String getDropTable() {
        return this.dropTable;
    }

    public void setDropTable(String table) {
        this.dropTable = table;
    }

    public int getSpawnDelay() {
        return this.spawnDelay;
    }

    public void setSpawnDelay(int SpawnDelay) {
        this.spawnDelay = SpawnDelay;
    }

    public int getSpawnRange() {
        return this.spawnRange;
    }

    public void setSpawnRange(int spawnRange) {
        this.spawnRange = spawnRange;
    }

    public int getMaxSpawnCount() {
        return this.maxSpawnCount;
    }

    public void setMaxSpawnCount(int maxSpawnCount) {
        this.maxSpawnCount = maxSpawnCount;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
