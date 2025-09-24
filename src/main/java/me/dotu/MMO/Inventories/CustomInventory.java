package me.dotu.MMO.Inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public abstract class CustomInventory implements Listener{
    private InventoryHolder holder;
    private int size;
    public int usableSize;
    private String name;
    public ArrayList<InventoryItem> contents;
    public final ItemStack pageItem = new ItemStack(Material.PAPER, 1);
    public int startingArrayIndex;
    private boolean dynamicContents;
    private boolean ordered;
    protected final Inventory inventory;
    protected int[] borderLarge = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};

    public CustomInventory(InventoryHolder holder, int size, String name, boolean dynamicContents, boolean ordered){
        this.holder = holder;
        this.dynamicContents = dynamicContents;
        this.size = dynamicContents ? Math.max(size, 18) : size;
        this.usableSize = this.size - 9;
        this.name = name;
        this.ordered = ordered;
        this.inventory = Bukkit.createInventory(holder, this.size, name);
        this.startingArrayIndex = 0;

        if (holder instanceof CustomInventoryHolder){
            ((CustomInventoryHolder) holder).setInventory(this.inventory);
            ((CustomInventoryHolder) holder).setCustomInventory(this);
        }
    }

    public abstract void createInventoryItems();

    protected void setupInventory(){
        if (this.contents == null){
            return;
        }

        if (this.contents.size() > this.usableSize){
            this.setupMultiPageInventory();
        }
        else{
            this.setupSinglePageInventory();
        }
    }

    private void setupSinglePageInventory(){
        if (this.ordered){
            for (InventoryItem item : this.contents){
                for (int slot : item.getSlots()){
                    this.inventory.setItem(slot, item.getItem());
                }
            }
        }
        else{
            int slot = 0;
            for (InventoryItem item : this.contents){
                this.inventory.setItem(slot, item.getItem());
                slot++;
            }
        }
    }
    
    public void setupNewPage(boolean isNextClick){
        if (isNextClick){
            this.startingArrayIndex += this.usableSize;
        }
        else{
            this.startingArrayIndex -= this.usableSize;
        }
        
        this.inventory.clear();
        this.setupPageItems();

        int slot = 0;
        int maxItems = Math.min(this.usableSize, this.contents.size() - this.startingArrayIndex);
        
        for (int i = 0; i < maxItems; i++){
            int contentIndex = this.startingArrayIndex + i;
            if (contentIndex >= this.contents.size()) break;
            
            InventoryItem item = this.contents.get(contentIndex);
            if (item != null){
                this.inventory.setItem(slot, item.getItem());
                slot++;
            }
        }
    }

    private void setupMultiPageInventory(){
        this.setupPageItems();

        int slot = 0;
        for (InventoryItem item : this.contents){
            if (slot <= this.usableSize-1){
                this.inventory.setItem(slot, item.getItem());
                slot++;
            }
        }
    }

    private void setupPageItems(){
        ItemStack nextPageItem = this.pageItem.clone();
        ItemMeta nextMeta = nextPageItem.getItemMeta();
        nextMeta.setDisplayName(ChatColor.YELLOW + "Next Page");
        nextPageItem.setItemMeta(nextMeta);
        
        ItemStack prevPageItem = this.pageItem.clone();
        ItemMeta prevMeta = prevPageItem.getItemMeta();
        prevMeta.setDisplayName(ChatColor.YELLOW + "Previous Page");
        prevPageItem.setItemMeta(prevMeta);

        int lastRow = this.size - 9;
        this.inventory.setItem(lastRow + 3, prevPageItem);
        this.inventory.setItem(lastRow + 5, nextPageItem);
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

    public boolean isDynamic() {
        return this.dynamicContents;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamicContents = dynamic;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }
}