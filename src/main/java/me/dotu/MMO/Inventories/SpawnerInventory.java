package me.dotu.MMO.Inventories;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;

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
            CustomSpawner spawner = spawners.getValue();

            ItemStack stack = CustomSpawnerHandler.decorateSpawnerStack(spawner);

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
