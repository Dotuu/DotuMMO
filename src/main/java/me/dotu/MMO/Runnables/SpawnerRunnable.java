package me.dotu.MMO.Runnables;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Configs.SpawnerLocationDataConfig;
import me.dotu.MMO.Main;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;
import me.dotu.MMO.Spawners.SpawnerEntityData;
import me.dotu.MMO.Spawners.SpawnerLocationData;
import me.dotu.MMO.Utils.LocationUtils;

public class SpawnerRunnable implements Runnable {

    private int taskId = -1;
    private long tick;
    private final Long interval = 20L;
    private final CustomSpawnerHandler customSpawnerHandler;

    public SpawnerRunnable() {
        this.customSpawnerHandler = new CustomSpawnerHandler();
        this.setupSpawnerData();
    }

    private void setupSpawnerData() {
        for (SpawnerLocationData sld : SpawnerLocationDataConfig.spawnerLocationData.values()) {
            CustomSpawner customSpawner = SpawnerConfig.spawners.get(sld.getLinkedCustomSpawner());
            if (customSpawner == null) {
                continue;
            }
            int spawnDelay = customSpawner.getSpawnDelay();
            SpawnerEntityData spawnerData = new SpawnerEntityData(customSpawner, (long) spawnDelay, 0, sld.getSpawnerLocation());
            SpawnerConfig.spawnerDataList.put(LocationUtils.serializeLocation(sld.getSpawnerLocation()), spawnerData);
        }
    }

    public void start() {
        this.taskId = Bukkit.getScheduler().runTaskTimer(Main.plugin, this, 20L, this.interval).getTaskId();
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

    @Override
    public void run() {
        this.tick++;
        this.refreshSpawnerData();
        SpawnerConfig.spawnerDataList.values().forEach(this::tickSpawner);
    }

    private void tickSpawner(SpawnerEntityData spawnerData) {
        CustomSpawner customSpawner = spawnerData.getCustomSpawner();
        if (customSpawner == null) {
            Main.plugin.getLogger().info("Spawner at: " + spawnerData.getSpawnerLoc().toString() + ", is linked to a Custom Spawner that no longer exists!");
            return;
        }

        Location spawnerLoc = spawnerData.getSpawnerLoc();
        Block spawnerBlock = spawnerLoc.getBlock();
        SpawnerLocationData sld = SpawnerLocationDataConfig.spawnerLocationData.get(LocationUtils.serializeLocation(spawnerLoc));

        if (sld == null) {
            return;
        }

        if (this.isNotSpawner(spawnerBlock)) {
            sld.removeSpawnLocations(spawnerLoc);
            return;
        }

        if (this.spawnerHasNoEntity(spawnerBlock)) {
            return;
        }

        if (this.isNotReadyToSpawn(spawnerData)) {
            return;
        }

        if (this.entityHasNoRoom(spawnerData)) {
            spawnerData.setNextSpawn(this.tick + customSpawner.getSpawnDelay());
            return;
        }

        this.customSpawnerHandler.spawnCustomEntity(spawnerLoc, customSpawner.getName() + "|" + LocationUtils.serializeLocation(spawnerLoc));
        spawnerData.setActiveEntitiesAmount(spawnerData.getActiveEntitiesAmount() + 1);
        spawnerData.setNextSpawn(this.tick + customSpawner.getSpawnDelay());
    }

    private void refreshSpawnerData() {
        for (SpawnerLocationData sld : SpawnerLocationDataConfig.spawnerLocationData.values()) {
            String locationKey = LocationUtils.serializeLocation(sld.getSpawnerLocation());

            if (!SpawnerConfig.spawnerDataList.containsKey(locationKey)) {
                CustomSpawner customSpawner = SpawnerConfig.spawners.get(sld.getLinkedCustomSpawner());
                if (customSpawner != null) {
                    int spawnDelay = customSpawner.getSpawnDelay();
                    SpawnerEntityData spawnerData = new SpawnerEntityData(customSpawner, (long) spawnDelay, 0, sld.getSpawnerLocation());
                    SpawnerConfig.spawnerDataList.put(locationKey, spawnerData);
                }
            }
        }

        SpawnerConfig.spawnerDataList.entrySet().removeIf(entry -> !SpawnerLocationDataConfig.spawnerLocationData.containsKey(entry.getKey()));
    }

    private boolean isNotReadyToSpawn(SpawnerEntityData spawnerData) {
        return this.tick < spawnerData.getNextSpawn();
    }

    private boolean spawnerHasNoEntity(Block block) {
        CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
        return creatureSpawner.getSpawnedType() == null;
    }

    private boolean isNotSpawner(Block block) {
        return block.getType() != Material.SPAWNER;
    }

    private boolean entityHasNoRoom(SpawnerEntityData spawnerData) {
        CustomSpawner customSpawner = spawnerData.getCustomSpawner();
        int maxSpawnCount = customSpawner.getMaxSpawnCount();

        return maxSpawnCount <= spawnerData.getActiveEntitiesAmount();
    }
}
