package me.dotu.MMO.Inventories.CustomSpawner;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Inventories.CustomInventory;
import me.dotu.MMO.Inventories.CustomInventoryHolder;
import me.dotu.MMO.Inventories.InventoryItem;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;

public class SpawnerInventory extends CustomInventory{

    private int startingIndex;

    public SpawnerInventory(Player player, int startingIndex) {
        super(new CustomInventoryHolder(), 9, "Custom Spawner's", true, false);
        this.startingIndex = startingIndex;
    }

    @Override
    public void createInventoryItems() {

        ArrayList<InventoryItem> inventoryItems = new ArrayList<>();

        for (Map.Entry<String, CustomSpawner> spawners : SpawnerConfig.spawners.entrySet()) {
            CustomSpawner spawner = spawners.getValue();

            ItemStack stack = CustomSpawnerHandler.decorateSpawnerStack(spawner);

            InventoryItem item = new InventoryItem(stack, new int[] { -1 }, false, true);
            inventoryItems.add(item);
        }

        this.setContents(inventoryItems);

        this.setupInventory();
    }

    public int getStartingIndex() {
        return this.startingIndex;
    }

    public void setStartingIndex(int startingIndex) {
        this.startingIndex = startingIndex;
    }
}
