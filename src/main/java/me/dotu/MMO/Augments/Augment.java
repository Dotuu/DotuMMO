package me.dotu.MMO.Augments;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Enums.AugmentEnum;
import me.dotu.MMO.Enums.ItemEnum;

public class Augment {

    private ItemEnum.Tier[] tiers;
    private int minLevelToUse;
    private String name;
    private String description;
    private AugmentEnum.Category category;

    private final Set<String> suffixes = Set.of(
        "_PICKAXE", "_AXE", "_SHOVEL", "_HOE", "_SWORD", "_HELMET", "_CHESTPLATE", "_LEGGINGS", "_BOOTS"
    );
    private final Set<Material> materialsList = Set.of(
        Material.MACE, Material.BOW, Material.CROSSBOW, Material.TRIDENT
    );

    private final Set<Material> offhandList = Set.of(
        Material.TORCH
    );

    public Augment(ItemEnum.Tier tiers[], int minLevelToUse, String name, String desciption, AugmentEnum.Category category) {
        this.tiers = tiers;
        this.minLevelToUse = minLevelToUse;
        this.name = name;
        this.description = desciption;
        this.category = category;
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

    protected ItemStack applyAugments(ItemStack item, String displayName, List<String> lores){
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lores);
        meta.setDisplayName(displayName);

        item.setItemMeta(meta);
        return item;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinLevel() {
        return this.minLevelToUse;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevelToUse = minLevel;
    }

    public ItemEnum.Tier[] getTiers() {
        return this.tiers;
    }

    public void setTiers(ItemEnum.Tier[] tiers) {
        this.tiers = tiers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AugmentEnum.Category getCategory() {
        return category;
    }

    public void setCategory(AugmentEnum.Category category) {
        this.category = category;
    }
}
