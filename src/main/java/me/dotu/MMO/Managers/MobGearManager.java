package me.dotu.MMO.Managers;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Enums.SpawnerEnum;
import me.dotu.MMO.ItemData.MobGear;

public class MobGearManager {
    private static Map<SpawnerEnum.Difficulty, List<MobGear>> lootTables;
    private Random random;
    
    public MobGearManager(){
        this.lootTables = new EnumMap<>(SpawnerEnum.Difficulty.class);
        this.random = new Random();
        initLootTables();
    }

    private void initLootTables(){
        List<MobGear> easyLoot = Arrays.asList(new MobGear(new ItemStack(Material.LEATHER_HELMET), 50),
            new MobGear(new ItemStack(Material.LEATHER_CHESTPLATE), 10),
            new MobGear(new ItemStack(Material.LEATHER_LEGGINGS), 10),
            new MobGear(new ItemStack(Material.LEATHER_BOOTS), 10),
            new MobGear(new ItemStack(Material.GOLDEN_HELMET), 10),
            new MobGear(new ItemStack(Material.GOLDEN_CHESTPLATE), 10)
        );

        lootTables.put(SpawnerEnum.Difficulty.EASY, easyLoot);
    }
}
