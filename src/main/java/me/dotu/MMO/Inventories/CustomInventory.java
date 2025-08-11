package me.dotu.MMO.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class CustomInventory {
    private InventoryHolder holder;
    private int size;
    private String name;
    protected final Inventory inventory;
    protected int[] borderLarge = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

    public CustomInventory(InventoryHolder holder, int size, String name){
        this.holder = holder;
        this.size = size;
        this.name = name;
        this.inventory = Bukkit.createInventory(holder, size, name);
    }

    protected abstract void setupContents();

    public void openInventory(Player player){
        player.openInventory(this.inventory);
    }

    public Inventory getInventory(){
        return this.inventory;
    }

    public InventoryHolder getHolder() {
        return this.holder;
    }

    public void setHolder(InventoryHolder holder) {
        this.holder = holder;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
