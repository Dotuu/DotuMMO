package me.dotu.MMO.ItemData;

import org.bukkit.Material;

import me.dotu.MMO.Enums.ItemEnum;

public class Armor extends MasterItem{

    private Material type;
    private ItemEnum.Tier tier;
    private int dropChance;
    private String dropTable;
    private short sockets;
    private int durability;
    private String name;

    public Armor(String name, int durability, short sockets, int dropChance, String dropTable, Material type, ItemEnum.Tier tier) {
        super(name, durability, sockets, dropChance, type, tier);
        this.type = type;
        this.tier = tier;
        this.dropChance = dropChance;
        this.dropTable = dropTable;
        this.sockets = sockets;
        this.durability = durability;
        this.name = name;
    }

    public Material getType() {
        return this.type;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public ItemEnum.Tier getTier() {
        return this.tier;
    }

    public void setTier(ItemEnum.Tier tier) {
        this.tier = tier;
    }

    public int getDropChance() {
        return this.dropChance;
    }

    public void setDropChance(int dropChance) {
        this.dropChance = dropChance;
    }
    
    public String getDropTable() {
        return dropTable;
    }

    public void setDropTable(String dropTable) {
        this.dropTable = dropTable;
    }

    public short getSockets() {
        return this.sockets;
    }

    public void setSockets(short sockets) {
        this.sockets = sockets;
    }

    public int getDurability() {
        return this.durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
