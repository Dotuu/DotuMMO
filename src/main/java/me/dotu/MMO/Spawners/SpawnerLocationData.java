package me.dotu.MMO.Spawners;

import java.util.ArrayList;

import org.bukkit.Location;

public class SpawnerLocationData {
    
    private String linkedCustomSpawner;
    private Location spawnerLocation;
    private ArrayList<Location> spawnLocations;

    public SpawnerLocationData(String linkedCustomSpawner, Location spawnerLocation, ArrayList<Location> spawnLocations){
        this.linkedCustomSpawner = linkedCustomSpawner;
        this.spawnerLocation = spawnerLocation;
        this.spawnLocations = (spawnLocations == null) ? new ArrayList<>() : new ArrayList<>(spawnLocations);
    }

    public String getLinkedCustomSpawner() {
        return this.linkedCustomSpawner;
    }

    public void setLinkedCustomSpawner(String linkedCustomSpawner) {
        this.linkedCustomSpawner = linkedCustomSpawner;
    }

    public Location getSpawnerLocation() {
        return this.spawnerLocation;
    }

    public void setSpawnerLocation(Location spawnerLocation) {
        this.spawnerLocation = spawnerLocation;
    }

    public ArrayList<Location> getSpawnLocations() {
        return this.spawnLocations;
    }

    public void setSpawnLocations(ArrayList<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }

    public void addSpawnLocations(Location loc){
        this.spawnLocations.add(loc);
    }

    public void removeSpawnLocations(Location loc){
        this.spawnLocations.remove(loc);
    }
}
