package me.dotu.MMO.Tables;

import java.util.ArrayList;

public class LootTable {
    private String tableName;
    private ArrayList<LootTableItem> items;

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
