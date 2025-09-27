package me.dotu.MMO.Spawners;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class SpawnerLocationData {
    
    private Long linkedCustomSpawner;
    private Location spawnerLocation;
    private ArrayList<Location> spawnLocations;
    private ArrayList<ItemStack> equipableArmor;
    private ArrayList<ItemStack> equipableWeapon;

    public SpawnerLocationData(Long linkedCustomSpawner, Location spawnerLocation, ArrayList<Location> spawnLocations, ArrayList<ItemStack> equipableArmor, ArrayList<ItemStack> equipableWeapon){
        this.linkedCustomSpawner = linkedCustomSpawner;
        this.spawnerLocation = spawnerLocation;
        this.spawnLocations = (spawnLocations == null) ? new ArrayList<>() : new ArrayList<>(spawnLocations);
        this.equipableArmor = equipableArmor;
        this.equipableWeapon = equipableWeapon;
    }

    public Long getLinkedCustomSpawner() {
        return this.linkedCustomSpawner;
    }

    public void setLinkedCustomSpawner(Long linkedCustomSpawner) {
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

    public ArrayList<ItemStack> getEquipableArmor() {
        return this.equipableArmor;
    }

    public void setEquipableArmor(ArrayList<ItemStack> equipableArmor) {
        this.equipableArmor = equipableArmor;
    }

    public ArrayList<ItemStack> getEquipableWeapon() {
        return this.equipableWeapon;
    }

    public void setEquipableWeapon(ArrayList<ItemStack> equipableWeapon) {
        this.equipableWeapon = equipableWeapon;
    }
}
