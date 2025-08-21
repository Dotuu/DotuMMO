package me.dotu.MMO.Inventories;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SpawnerSettingsInventory extends CustomInventory implements Listener{

    private final ArrayList<InventoryItem> invItems = new ArrayList<>();
    private final int[] nametags = new int[] {10, 11, 12, 14, 15, 16, 28, 29, 30, 32, 33, 34};
    private final int[] redGlass;
    private final int[] limeGlass = new int[] {19};
    private final int[] yellowGlass = new int[] {24, 25, 37, 38, 39, 41, 42, 43};
    private final int[] spawner = new int[] {49};
    private final int[] filler = new int[] {13, 22, 31, 40};

    public SpawnerSettingsInventory(){
        super(new SpawnerSettingsHolder(), 54, "Spawner Settings");
        this.redGlass = new int[] {20, 21, 23};
        this.createInventoryItems();
    }

    @Override
    protected void setupInventoryContents() {
        for (InventoryItem item : this.invItems){
            int[] slots = item.getSlots();

            for (int slot : slots){
                this.inventory.setItem(slot, item.getItem());
            }
        }
    }

    @Override
    protected void createInventoryItems(){
        invItems.add(new InventoryItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), this.borderLarge, false, false));
        invItems.add(new InventoryItem(new ItemStack(Material.NAME_TAG), this.nametags, false, true));
        invItems.add(new InventoryItem(new ItemStack(Material.RED_STAINED_GLASS_PANE), this.redGlass, false, true));
        invItems.add(new InventoryItem(new ItemStack(Material.LIME_STAINED_GLASS_PANE), this.limeGlass, false, true));
        invItems.add(new InventoryItem(new ItemStack(Material.YELLOW_STAINED_GLASS_PANE), this.yellowGlass, false, false));
        invItems.add(new InventoryItem(new ItemStack(Material.SPAWNER), this.spawner, false, true));
        invItems.add(new InventoryItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), this.filler, false, false));

        this.setupInventoryContents();
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event){
        if (event.getInventory().getHolder() instanceof SpawnerSettingsHolder){
            InventoryItem inventoryItem = this.getInventoryItemClicked(event.getSlot());
            
            if (inventoryItem != null && inventoryItem.isRemoveable() == false){
                event.setCancelled(true);
            }

            if (this.isActionClick(event.getClickedInventory(), event.getSlot())){
                // do clicked valid settings item here
            }
        }
    }

    private InventoryItem getInventoryItemClicked(int clickedSlot){
        for (InventoryItem item : this.invItems){
            int[] slots = item.getSlots();

            for (int slot : slots){
                if (slot == clickedSlot){
                    return item;
                }
            }
        }
        return null;
    }

    private boolean isActionClick(Inventory inv, int slot){
        if (inv == null){
            return false;
        }

        ItemStack item = inv.getItem(slot);

        if (item == null || item.getType() == Material.AIR){
            return false;
        }

        InventoryItem invItem = getInventoryItemClicked(slot);
        return invItem != null && invItem.isActionItem();
    }
}
