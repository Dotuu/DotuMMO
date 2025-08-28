package me.dotu.MMO.Spawners;

import org.bukkit.Location;

public class SpawnerEntityData {

    private Long nextSpawn;
    private CustomSpawner customSpawner;
    private int activeEntitiesAmount;
    private Location spawnerLoc;

    public SpawnerEntityData(CustomSpawner customSpawner, Long nextSpawn, int activeEntitiesAmount, Location spawnerLoc){
        this.customSpawner = customSpawner;
        this.nextSpawn = nextSpawn;
        this.activeEntitiesAmount = activeEntitiesAmount;
        this.spawnerLoc = spawnerLoc;
    }

    public CustomSpawner getCustomSpawner() {
        return this.customSpawner;
    }

    public void setCustomSpawner(CustomSpawner customSpawner) {
        this.customSpawner = customSpawner;
    }

    public Long getNextSpawn() {
        return this.nextSpawn;
    }

    public void setNextSpawn(Long nextSpawn) {
        this.nextSpawn = nextSpawn;
    }

    public int getActiveEntitiesAmount() {
        return this.activeEntitiesAmount;
    }

    public void setActiveEntitiesAmount(int activeEntitiesAmount) {
        this.activeEntitiesAmount = activeEntitiesAmount;
    }

    public Location getSpawnerLoc() {
        return spawnerLoc;
    }

    public void setSpawnerLoc(Location spawnerLoc) {
        this.spawnerLoc = spawnerLoc;
    }

}
