package me.dotu.MMO.ItemData;

import org.bukkit.inventory.ItemStack;

public class MobGear {

    private ItemStack item;
    private int weight;

    public MobGear(ItemStack item, int weight){
        this.item = item;
        this.weight = weight;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
