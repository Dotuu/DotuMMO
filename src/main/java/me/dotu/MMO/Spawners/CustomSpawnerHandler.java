package me.dotu.MMO.Spawners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Configs.LootTableConfig;
import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Enums.SpawnerKey;
import me.dotu.MMO.LootTables.LootItem;
import me.dotu.MMO.LootTables.LootTable;
import net.md_5.bungee.api.ChatColor;

public class CustomSpawnerHandler implements Listener {

    public CustomSpawnerHandler() {
    }

    @EventHandler
    public void onMobSpawn(SpawnerSpawnEvent event) {
        CreatureSpawner spawner = event.getSpawner();
        LivingEntity living = (LivingEntity) event.getEntity();
        event.setCancelled(true);
        if (spawner.getPersistentDataContainer().has(SpawnerKey.ROOT.getKey())) {
            String name = spawner.getPersistentDataContainer().get(SpawnerKey.NAME.getKey(), PersistentDataType.STRING);
            CustomSpawner customSpawner = SpawnerConfig.spawners.get(name);
            ArrayList<Location> spawnLocations = customSpawner.getSpawnLocations();

            for (Location loc : spawnLocations) {
                if (event.getEntity() instanceof LivingEntity) {
                    this.spawnCustomEntity(customSpawner, living, loc);
                    break;
                }
            }
        }
    }

    private void spawnCustomEntity(CustomSpawner props, LivingEntity living, Location loc) {
        World world = loc.getWorld();
        // set entity health
        living.setHealth(calculateHealth(this.getRandomLevel(props.getMinLevel(), props.getMaxLevel())));

        // set entity armor
        if (this.hasEquipmentSlots(living)) {
            if (props.isArmored()) {
                for (int x = 0; x < 4; x++) {
                    if (this.canEquipGear(props)) {
                        this.equipToMob(props, living, x);
                    }
                }
            }
            // set entity weapon
            if (props.isWeaponed()) {
                if (this.canEquipGear(props)) {
                    this.equipToMob(props, living, 4);
                }
            }
        }
    }

    private int getRandomLevel(int min, int max) {
        return min + (int) (Math.random() * (max - min) + 1);
    }

    private void equipToMob(CustomSpawner props, LivingEntity living, int slot) {
        LootTable table = LootTableConfig.lootTables.get(props.getTable());
        List<LootItem> tableItems = table.getItems();
        ItemStack stack = rollTableForSlot(tableItems, this.getSuffixes(slot));

        switch (slot) {
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

    private ItemStack rollTable(List<LootItem> table) {
        int totalWeight = table.stream().mapToInt(LootItem::getWeight).sum();
        int rand = 1 + (int) (Math.random() * totalWeight);

        int cumulative = 0;
        for (LootItem item : table) {
            cumulative += item.getWeight();
            if (rand <= cumulative) {
                ItemStack stack = new ItemStack(item.getMaterial());
                return stack;
            }
        }
        return new ItemStack(Material.AIR);
    }

    private ItemStack rollTableForSlot(List<LootItem> table, String[] suffixes) {
        List<LootItem> filtered = table.stream()
                .filter(gear -> Arrays.stream(suffixes).anyMatch(suffix -> gear.getMaterial().name().endsWith(suffix)))
                .toList();
        if (filtered.isEmpty()) {
            return new ItemStack(Material.AIR);
        }
        return rollTable(filtered);
    }

    private String[] getSuffixes(int slot) {
        switch (slot) {
            case 0:
                return new String[] { "_HELMET" };
            case 1:
                return new String[] { "_CHESTPLATE" };
            case 2:
                return new String[] { "_LEGGINGS" };
            case 3:
                return new String[] { "_BOOTS" };
            case 4:
                return new String[] { "_SWORD", "BOW", "MACE", "CROSSBOW" };
            default:
                return new String[0];
        }
    }

    private boolean canEquipGear(CustomSpawner props) {
        if (this.rollChance(props.getDifficulty())) {
            if (this.lootTableExists(props.getTable())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEquipmentSlots(LivingEntity entity) {
        return (entity instanceof Mob) && entity.getEquipment() != null;
    }

    private boolean rollChance(double chance) {
        return Math.random() < (chance / 100.0);
    }

    private int calculateHealth(int level) {
        int baseHealth = 20;
        double growthRate = 1.15;
        return (int) (baseHealth * Math.pow(growthRate, level - 1));
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.SPAWNER) {
            ItemStack handItem = event.getItemInHand();
            ItemMeta handMeta = handItem.getItemMeta();
            if (handMeta.getPersistentDataContainer().has(SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN)) {
                String name = handMeta.getPersistentDataContainer().get(SpawnerKey.NAME.getKey(),
                        PersistentDataType.STRING);
                CreatureSpawner spawner = (CreatureSpawner) block.getState();
                CustomSpawner customSpawner = SpawnerConfig.spawners.get(name);
                this.setSpawnerProps(spawner, customSpawner);
            }
        }
    }

    public static ItemStack decorateSpawnerStack(CustomSpawner spawner) {
        String spawnerName = spawner.getName();

        List<String> lores = Arrays.asList(
                ChatColor.AQUA + "Loot Table: " + ChatColor.YELLOW + spawner.getTable(),
                ChatColor.AQUA + "Min Level: " + ChatColor.YELLOW + String.valueOf(spawner.getMinLevel()),
                ChatColor.AQUA + "Max Level: " + ChatColor.YELLOW + String.valueOf(spawner.getMaxLevel()),
                ChatColor.AQUA + "Min Spawn Delay: " + ChatColor.YELLOW + String.valueOf(spawner.getMinSpawnDelay()),
                ChatColor.AQUA + "Max Spawn Delay: " + ChatColor.YELLOW + String.valueOf(spawner.getMaxSpawnDelay()),
                ChatColor.AQUA + "Spawn Range: " + ChatColor.YELLOW + String.valueOf(spawner.getSpawnRange()),
                ChatColor.AQUA + "Difficulty: " + ChatColor.YELLOW + Double.toString(spawner.getDifficulty()),
                ChatColor.AQUA + "Armored: " + ChatColor.YELLOW + Boolean.toString(spawner.isArmored()),
                ChatColor.AQUA + "Weaponed: " + ChatColor.YELLOW + Boolean.toString(spawner.isWeaponed()),
                ChatColor.AQUA + "Name Visible: " + ChatColor.YELLOW + Boolean.toString(spawner.isNameVisible()),
                ChatColor.AQUA + "Spawn Randomly: " + ChatColor.YELLOW + Boolean.toString(spawner.isSpawnRandomly()));

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(SpawnerKey.NAME.getKey(), PersistentDataType.STRING, spawnerName);

        meta.setDisplayName(ChatColor.BLUE + spawnerName);
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public void setSpawnerProps(CreatureSpawner spawner, CustomSpawner customSpawner) {
        spawner.getPersistentDataContainer().set(SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN, true);
        spawner.getPersistentDataContainer().set(SpawnerKey.MIN_LEVEL.getKey(), PersistentDataType.INTEGER,
                customSpawner.getMinLevel());
        spawner.getPersistentDataContainer().set(SpawnerKey.MAX_LEVEL.getKey(), PersistentDataType.INTEGER,
                customSpawner.getMaxLevel());
        spawner.getPersistentDataContainer().set(SpawnerKey.DIFFICULTY.getKey(), PersistentDataType.DOUBLE,
                customSpawner.getDifficulty());
        spawner.getPersistentDataContainer().set(SpawnerKey.ARMORED.getKey(), PersistentDataType.BOOLEAN,
                customSpawner.isArmored());
        spawner.getPersistentDataContainer().set(SpawnerKey.WEAPONED.getKey(), PersistentDataType.BOOLEAN,
                customSpawner.isWeaponed());
        spawner.getPersistentDataContainer().set(SpawnerKey.NAME_VISIBLE.getKey(), PersistentDataType.BOOLEAN,
                customSpawner.isNameVisible());
        spawner.getPersistentDataContainer().set(SpawnerKey.SPAWN_RANDOMLY.getKey(), PersistentDataType.BOOLEAN,
                customSpawner.isSpawnRandomly());
        spawner.getPersistentDataContainer().set(SpawnerKey.NAME.getKey(), PersistentDataType.STRING,
                customSpawner.getName());
        spawner.getPersistentDataContainer().set(SpawnerKey.TABLE.getKey(), PersistentDataType.STRING,
                customSpawner.getTable());
        spawner.getPersistentDataContainer().set(SpawnerKey.MIN_SPAWN_DELAY.getKey(), PersistentDataType.INTEGER,
                customSpawner.getMinSpawnDelay());
        spawner.getPersistentDataContainer().set(SpawnerKey.MAX_SPAWN_DELAY.getKey(), PersistentDataType.INTEGER,
                customSpawner.getMaxSpawnDelay());
        spawner.getPersistentDataContainer().set(SpawnerKey.SPAWN_RANGE.getKey(), PersistentDataType.INTEGER,
                customSpawner.getMaxSpawnDelay());
        spawner.update();
    }

    public int getSpawnLevel(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private boolean lootTableExists(String lootTable) {
        return LootTableConfig.lootTables.containsKey(lootTable);
    }
}