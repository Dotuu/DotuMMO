package me.dotu.MMO.Tables;

import java.util.ArrayList;

public class LootTable {
    private String tableName;
    private ArrayList<LootItem> items;

    public LootTable(String tableName, ArrayList<LootItem> items) {
        this.tableName = tableName;
        this.items = items;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<LootItem> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<LootItem> items) {
        this.items = items;
    }

    public void addItem(LootItem item) {
        this.items.add(item);
    }
}
