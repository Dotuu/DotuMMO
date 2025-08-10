package me.dotu.MMO.Entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Enums.SpawnerEnum;
import me.dotu.MMO.ItemData.MobGear;
import me.dotu.MMO.Managers.MobGearManager;

public abstract class CustomSpawnerHandler implements Listener{

    public CustomSpawnerHandler(){
    }

    @EventHandler
    public void onMobSpawn(SpawnerSpawnEvent event){
        CreatureSpawner spawner = event.getSpawner();
        LivingEntity living = (LivingEntity) event.getEntity();
        event.setCancelled(true);
        if (spawner.getPersistentDataContainer().has(SpawnerEnum.SpawnerKey.ROOT.getKey())){
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
                    if (this.canEquipGear(props)){
                        this.equipToMob(props, living, x);
                    }
                }
            }
            // set entity weapon
            if (props.isWeaponed()){
                if (this.canEquipGear(props)){
                    this.equipToMob(props, living, 4);
                }
            }
        }
    }

    private void equipToMob(CustomSpawner props, LivingEntity living, int slot){
        List<MobGear> table = MobGearManager.lootTables.get(props.getTable());
        ItemStack stack = rollTableForSlot(table, this.getSuffixes(slot));

        switch(slot){
            case 0:
                living.getEquipment().setHelmet(stack);
                break;
            case 1:
                living.getEquipment().setChestplate(stack);
                break;
            case 2:
                living.getEquipment().setLeggings(stack);
                break;
            case 3:
                living.getEquipment().setBoots(stack);
                break;
            case 4:
                living.getEquipment().setItemInMainHand(stack);
                break;
        }
    }

    private ItemStack rollTable(List<MobGear> table){
        int totalWeight = table.stream().mapToInt(MobGear::getWeight).sum();
        int rand = 1 + (int)(Math.random() * totalWeight);

        int cumulative = 0;
        for (MobGear gear : table){
            cumulative += gear.getWeight();
            if (rand <= cumulative){
                return gear.getItem();
            }
        }
        return new ItemStack(Material.AIR);
    }

    private ItemStack rollTableForSlot(List<MobGear> table, String[] suffixes){
        List<MobGear> filtered = table.stream().filter(gear -> Arrays.stream(suffixes).anyMatch(suffix -> gear.getItem().getType().name().endsWith(suffix))).toList();
        if (filtered.isEmpty()){
            return new ItemStack(Material.AIR);
        }
        return rollTable(filtered);
    }

    private String[] getSuffixes(int slot){
        switch(slot){
            case 0:
                return new String[] {"_HELMET"};
            case 1:
                return new String[] {"_CHESTPLATE"};
            case 2:
                return new String[] {"_LEGGINGS"};
            case 3:
                return new String[] {"_BOOTS"};
            case 4:
                return new String[] {"_SWORD", "BOW", "MACE", "CROSSBOW"};
            default: 
                return new String[0];
        }
    }


    private boolean canEquipGear(CustomSpawner props){
        if (this.rollChance(props.getDifficulty())){
            if (this.lootTableExists(props.getTable())){
                return true;
            }
        }
        return false;
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
            if (spawner.getPersistentDataContainer().has(SpawnerEnum.SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN)){
                CustomSpawner props = this.loadSpawnerData(spawner);
                this.setSpawnerProps(spawner, props);
            }
        }
    }
    
    public int getSpawnLevel(int min, int max){
        return min + (int)(Math.random() * ((max - min) +1));
    }

    private boolean lootTableExists(String lootTable){
        return MobGearManager.lootTables.containsKey(lootTable);
    }

    private void setSpawnerProps(CreatureSpawner spawner, CustomSpawner props){
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.LEVEL.getKey(), PersistentDataType.INTEGER, props.getLevel());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.DIFFICULTY.getKey(), PersistentDataType.DOUBLE, props.getDifficulty());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.ARMORED.getKey(), PersistentDataType.BOOLEAN, props.isArmored());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.WEAPONED.getKey(), PersistentDataType.BOOLEAN, props.isWeaponed());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.NAME_VISIBLE.getKey(), PersistentDataType.BOOLEAN, props.isNameVisible());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.SPAWN_RANDOMLY.getKey(), PersistentDataType.BOOLEAN, props.isSpawnRandomly());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.NAME.getKey(), PersistentDataType.STRING, props.getName());
        spawner.getPersistentDataContainer().set(SpawnerEnum.SpawnerKey.TABLE.getKey(), PersistentDataType.STRING, props.getTable());
        spawner.update();
    }

    private CustomSpawner loadSpawnerData(CreatureSpawner spawner){
        return new CustomSpawner(
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.LEVEL.getKey(), PersistentDataType.INTEGER, 1),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.DIFFICULTY.getKey(), PersistentDataType.DOUBLE, 50D),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.ARMORED.getKey(), PersistentDataType.BOOLEAN, false),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.WEAPONED.getKey(), PersistentDataType.BOOLEAN, true),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.NAME_VISIBLE.getKey(), PersistentDataType.BOOLEAN, true),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.SPAWN_RANDOMLY.getKey(), PersistentDataType.BOOLEAN, true),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.NAME.getKey(), PersistentDataType.STRING, spawner.getSpawnedType().name()),
            spawner.getPersistentDataContainer().getOrDefault(SpawnerEnum.SpawnerKey.TABLE.getKey(), PersistentDataType.STRING, "")
        );
    }
}