package me.dotu.MMO.ItemData;

import org.bukkit.Material;

import me.dotu.MMO.Enums.ItemEnum;

public class Armor extends MasterItem{

    private Material type;
    private ItemEnum.Tier tier;
    private short sockets;
    private int durability;
    private String name;

    public Armor(String name, int durability, short sockets, Material type, ItemEnum.Tier tier) {
        super(name, sockets, sockets, type, tier);
        this.type = type;
        this.tier = tier;
        this.sockets = sockets;
        this.durability = durability;
        this.name = name;
    }

    @Override
    public Material getType() {
        return this.type;
    }

    @Override
    public void setType(Material type) {
        this.type = type;
    }

    public ItemEnum.Tier getTier() {
        return this.tier;
    }

    public void setTier(ItemEnum.Tier tier) {
        this.tier = tier;
    }

    public short getSockets() {
        return this.sockets;
    }

    public void setSockets(short sockets) {
        this.sockets = sockets;
    }

    public int getArmorDurability() {
        return this.durability;
    }

    public void setArmorDurability(int durability) {
        this.durability = durability;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
