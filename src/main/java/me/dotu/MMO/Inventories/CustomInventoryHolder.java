package me.dotu.MMO.Inventories;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CustomInventoryHolder implements InventoryHolder{
    private Inventory inventory;
    private CustomInventory customInventory;

    @Override
    public Inventory getInventory() {
        return null;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public CustomInventory getCustomInventory() {
        return this.customInventory;
    }

    public void setCustomInventory(CustomInventory customInventory) {
        this.customInventory = customInventory;
    }
}
