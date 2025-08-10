package me.dotu.MMO.Augments;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Enums.ItemEnum;

public abstract class MasterAugment {

    private String name;
    private Integer minLevel;
    private ItemEnum.Tier[] tiers;

    private final Set<String> suffixes = Set.of(
        "_PICKAXE", "_AXE", "_SHOVEL", "_HOE", "_SWORD", "_HELMET", "_CHESTPLATE", "_LEGGINGS", "_BOOTS"
    );
    private final Set<Material> materialsList = Set.of(
        Material.MACE, Material.BOW, Material.CROSSBOW, Material.TRIDENT
    );

    private final Set<Material> offhandList = Set.of(
        Material.TORCH
    );

    public MasterAugment(String name, int level, ItemEnum.Tier[] tiers){
        this.name = name;
        this.minLevel = level;
        this.tiers = tiers;
    }

    public boolean hasAugment(ArrayList<ItemStack> items, NamespacedKey key){
        for (ItemStack item : items) {
            if (item != null && !item.getType().equals(Material.AIR)){
                ItemMeta meta = item.getItemMeta();
                if (meta.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<ItemStack> getAugmentableItems(Player player){
        ArrayList<ItemStack> returnArray = new ArrayList<>();

        PlayerInventory inv = player.getInventory();
        for (ItemStack stack : inv){
            if (this.isToolOrWeaponOrArmor(stack)){
                returnArray.add(stack);
            }
        }

        ItemStack offhand = inv.getItemInOffHand();
        if (this.offhandList.contains(offhand.getType())){
            returnArray.add(offhand);
        }

        for (ItemStack armor : inv.getArmorContents()){
            if (this.isToolOrWeaponOrArmor(armor)){
                returnArray.add(armor);
            }
        }
        return returnArray;
    }

    public boolean isToolOrWeaponOrArmor(ItemStack item){
        if (item == null || item.getType().equals(Material.AIR)){
            return false;
        }
        else{
            String typeName = item.getType().name();

            for (String suffix : this.suffixes){
                if (typeName.endsWith(suffix)){
                    return true;
                }
            }
            return this.materialsList.contains(item.getType());
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinLevel() {
        return this.minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public ItemEnum.Tier[] getTiers() {
        return this.tiers;
    }

    public void setTiers(ItemEnum.Tier[] tiers) {
        this.tiers = tiers;
    }
}
