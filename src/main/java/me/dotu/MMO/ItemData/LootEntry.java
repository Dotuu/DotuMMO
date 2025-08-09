package me.dotu.MMO.ItemData;

import org.bukkit.inventory.ItemStack;

public class LootEntry {

    private ItemStack item;
    private int weight;

    public LootEntry(ItemStack item, int weight){
        this.item = item;
        this.weight = weight;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
