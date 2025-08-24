package me.dotu.MMO.Inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomInventory {
    private InventoryHolder holder;
    private int size;
    private String name;
    private ArrayList<InventoryItem> contents;
    protected final Inventory inventory;
    protected int[] borderLarge = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

    public CustomInventory(InventoryHolder holder, int size, String name, ArrayList<InventoryItem> contents){
        this.holder = holder;
        this.size = size;
        this.name = name;
        this.inventory = Bukkit.createInventory(holder, size, name);
    }

    public CustomInventory(InventoryHolder holder, int size, String name){
        this.holder = holder;
        this.size = size;
        this.name = name;
        this.contents = new ArrayList<>();
        this.inventory = Bukkit.createInventory(holder, size, name);
    }

    public abstract void createInventoryItems();

    protected void setupInventoryContents(){
        for (InventoryItem item : this.contents){
            for (int slot : item.getSlots()){
                this.inventory.setItem(slot, item.getItem());
            }
        }
    }

    protected ItemStack getDecoratedItemStack(Material material, String displayName){
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(displayName);
        stack.setItemMeta(meta);
        return stack;
    }

    public static boolean isItem(Inventory inv, int slot){
        ItemStack item = inv.getItem(slot);
        return item != null && item.getType() != Material.AIR;
    }

    protected boolean isActionClick(Inventory inv, int slot){
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

    protected InventoryItem getInventoryItemClicked(int clickedSlot){
        for (InventoryItem item : this.getContents()){
            int[] slots = item.getSlots();

            for (int slot : slots){
                if (slot == clickedSlot){
                    return item;
                }
            }
        }
        return null;
    }

    public void openInventory(Player player){
        player.openInventory(this.inventory);
    }

    public <T extends CustomInventory> void openInventory(Player player, HashMap<UUID, T> map){
        if (map.containsKey(player.getUniqueId())){
            map.get(player.getUniqueId()).openInventory(player);
        }
        else{
            player.openInventory(this.inventory);
        }
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
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<InventoryItem> getContents() {
        return this.contents;
    }

    public void setContents(ArrayList<InventoryItem> contents) {
        this.contents = contents;
    }

    public void addContents(InventoryItem item) {
        this.contents.add(item);
    }
}
