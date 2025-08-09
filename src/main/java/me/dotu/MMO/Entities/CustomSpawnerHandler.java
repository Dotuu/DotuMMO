package me.dotu.MMO.Entities;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Enums.SpawnerEnum;

public abstract class CustomSpawnerHandler implements Listener{
    
    private final JavaPlugin plugin;

    public CustomSpawnerHandler(JavaPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobSpawn(SpawnerSpawnEvent event){
        CreatureSpawner spawner = event.getSpawner();
        LivingEntity living = (LivingEntity) event.getEntity();
        event.setCancelled(true);
        if (spawner.getPersistentDataContainer().has(SpawnerEnum.SpawnerKey.ROOT.getKey(this.plugin))){
            if (SpawnerConfig.spawners.containsKey(spawner.getLocation())){
                HashMap<Location, Boolean> spawnLocations = SpawnerConfig.spawners.get(spawner.getLocation());

                for (Map.Entry<Location, Boolean> entry : spawnLocations.entrySet()){
                    if (entry.getValue()){
                        if (event.getEntity() instanceof LivingEntity){
                            this.spawnCustomEntity(this.loadSpawnerData(spawner), living, entry.getKey());
                            break;
                        }
                    }
                }
            }
        }
    }

    private void spawnCustomEntity(CustomSpawner props, LivingEntity living, Location loc){
        World world = loc.getWorld();
        // set entity health
        living.setHealth(calculateHealth(props.getLevel()));

        // set entity armor
        if (this.hasEquipmentSlots(living)){
            if(props.isArmored()){
                for (int x = 0; x < 4; x++){
                    if (rollChance(props.getDifficulty().getDifficultyValue())){
                        // equip armor piece
                    }
                }
            }
            // set entity weapon
            if (props.isWeaponed()){
                if (rollChance(props.getDifficulty().getDifficultyValue())){
                    // equip weapon
                }
            }
        }
    }

    private boolean hasEquipmentSlots(LivingEntity entity) {
        return (entity instanceof Mob) && entity.getEquipment() != null;
    }

    private boolean rollChance(double chance){
        return Math.random() < (chance / 100.0);
    }

    private int calculateHealth(int level){
        int baseHealth = 20;
        double growthRate = 1.15;
        return (int) (baseHealth * Math.pow(growthRate, level -1));
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event){
        Block block = event.getBlock();

        if (block.getType() == Material.SPAWNER){
            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            if (spawner.getPersistentDataContainer().has(SpawnerEnum.SpawnerKey.ROOT.getKey(this.plugin), PersistentDataType.BOOLEAN)){
                CustomSpawner props = this.loadSpawnerData(spawner);
                this.setSpawnerProps(spawner, props);
            }
        }
    }
    
    public int getSpawnLevel(int min, int max){
        return min + (int)(Math.random() * ((max - min) +1));
    }

    private void setSpawnerProps(CreatureSpawner spawner, CustomSpawner props){
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.LEVEL.getKey(this.plugin), PersistentDataType.INTEGER, props.getLevel());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.DIFFICULTY.getKey(this.plugin), PersistentDataType.DOUBLE, props.getDifficulty().getDifficultyValue());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.ARMORED.getKey(this.plugin), PersistentDataType.BOOLEAN, props.isArmored());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.WEAPONED.getKey(this.plugin), PersistentDataType.BOOLEAN, props.isWeaponed());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.NAME_VISIBLE.getKey(this.plugin), PersistentDataType.BOOLEAN, props.isNameVisible());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.SPAWN_RANDOMLY.getKey(this.plugin), PersistentDataType.BOOLEAN, props.isSpawnRandomly());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.NAME.getKey(this.plugin), PersistentDataType.STRING, props.getName());
        spawner.update();
    }

    private CustomSpawner loadSpawnerData(CreatureSpawner spawner){
        return new CustomSpawner(
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.LEVEL.getKey(this.plugin), PersistentDataType.INTEGER, 1),
            SpawnerEnum.Difficulty.fromValue(spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.DIFFICULTY.getKey(this.plugin), PersistentDataType.DOUBLE, 1D)),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.ARMORED.getKey(this.plugin), PersistentDataType.BOOLEAN, false),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.WEAPONED.getKey(this.plugin), PersistentDataType.BOOLEAN, true),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.NAME_VISIBLE.getKey(this.plugin), PersistentDataType.BOOLEAN, true),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.SPAWN_RANDOMLY.getKey(this.plugin), PersistentDataType.BOOLEAN, true),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.NAME.getKey(this.plugin), PersistentDataType.STRING, spawner.getSpawnedType().name())
        );
    }
}
