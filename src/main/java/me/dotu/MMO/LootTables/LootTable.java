package me.dotu.MMO.LootTables;

import java.util.ArrayList;
import java.util.UUID;

public class LootTable {
    private String tableName;
    private UUID uuid;
    private ArrayList<LootItem> items;

    public LootTable(String tableName, UUID uuid) {
        this.tableName = tableName;
        this.uuid = uuid;
        this.items = new ArrayList<LootItem>();
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
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
