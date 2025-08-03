package me.dotu.MMO.ItemData;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Enums.ItemEnum;

public abstract class MasterItem extends ItemStack{
  public MasterItem(String name, int durability, short sockets, Material type, ItemEnum.Tier tier) {
    super(type);
  }
}
