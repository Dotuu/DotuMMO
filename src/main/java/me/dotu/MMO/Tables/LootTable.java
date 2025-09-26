package me.dotu.MMO.Tables;

import java.util.ArrayList;

public class LootTable {
    private String tableName;
    private ArrayList<LootTableItem> items;
    private ArrayList<Long> itemIds;

    public LootTable(String tableName) {
        this.tableName = tableName;
        this.items = new ArrayList<>();
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setItemIds(ArrayList<Long> itemIds){
        this.itemIds = itemIds;
    }
    
    public ArrayList<Long> getItemIds(){
        return this.itemIds;
    }

    public void addItemId(Long itemId){
        this.itemIds.add(itemId);
    }

    public ArrayList<LootTableItem> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<LootTableItem> items) {
        this.items = items;
    }

    public void addItem(LootTableItem item) {
        this.items.add(item);
    }
}
