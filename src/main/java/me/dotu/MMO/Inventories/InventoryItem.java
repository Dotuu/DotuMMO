package me.dotu.MMO.Inventories;

import org.bukkit.inventory.ItemStack;

public class InventoryItem {
    private ItemStack item;
    private int[] slots;
    private boolean removeable;
    private boolean actionItem;

    public InventoryItem(ItemStack item, int[] slots, boolean removeable, boolean actionItem){
        this.item = item;
        this.slots = slots;
        this.removeable = removeable;
        this.actionItem = actionItem;
    }
    
    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int[] getSlots() {
        return this.slots;
    }

    public void setSlots(int[] slots) {
        this.slots = slots;
    }

    public boolean isRemoveable() {
        return this.removeable;
    }

    public void setRemoveable(boolean removeable) {
        this.removeable = removeable;
    }

    public boolean isActionItem() {
        return this.actionItem;
    }

    public void setActionItem(boolean actionItem) {
        this.actionItem = actionItem;
    }
}
