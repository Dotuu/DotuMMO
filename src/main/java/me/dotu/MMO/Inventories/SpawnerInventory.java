package me.dotu.MMO.Inventories;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Spawners.CustomSpawner;
import net.md_5.bungee.api.ChatColor;

public class SpawnerInventory extends CustomInventory{

    private Player player;
    private int startingIndex;

    public SpawnerInventory(Player player, int startingIndex) {
        super(new SpawnerHolder(), 54, "Custom Spawner's");
        this.player = player;
        this.startingIndex = startingIndex;
    }

    @Override // change function to allow for more than 54 spawners ie add next and previous
              // pages if needed use startingIndex as point of start in map for second page
    public void createInventoryItems() {

        int slot = 0;

        for (Map.Entry<String, CustomSpawner> spawners : SpawnerConfig.spawners.entrySet()) {
            String spawnerName = spawners.getKey();
            CustomSpawner spawner = spawners.getValue();

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
                ChatColor.AQUA + "Spawn Randomly: " + ChatColor.YELLOW + Boolean.toString(spawner.isSpawnRandomly())
            );

            ItemStack stack = this.getDecoratedItemStack(Material.SPAWNER, ChatColor.RED + spawnerName, lores);

            int[] slots = new int[] { slot };

            InventoryItem item = new InventoryItem(stack, slots, false, true);
            this.addContents(item);

            slot++;

            if (slot >= this.getInventory().getSize()){
                break;
            }
        }

        this.setupInventoryContents();
    }

    public int getStartingIndex() {
        return this.startingIndex;
    }

    public void setStartingIndex(int startingIndex) {
        this.startingIndex = startingIndex;
    }
}
