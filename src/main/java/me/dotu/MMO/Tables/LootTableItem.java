package me.dotu.MMO.Tables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import me.dotu.MMO.Augments.Augment;
import me.dotu.MMO.Enums.ItemTier;
import me.dotu.MMO.Gems.Gem;

public class LootTableItem {
    private List<Augment> augments;
    private List<Gem> gems;
    private Material material;
    private String displayName;
    private List<String> lores;
    private int weight;
    private ItemTier tier;

    public LootTableItem(Material material, String displayName) {
        this.material = material;
        this.displayName = displayName;
        this.tier = ItemTier.COMMON;
    }

    public LootTableItem(Material material, String displayName, List<Augment> augments, List<Gem> gems, List<String> lores, int weight, ItemTier tier) {
        this.material = material;
        this.displayName = displayName;
        this.augments = augments != null ? augments : new ArrayList<>();
        this.gems = gems != null ? gems : new ArrayList<>();
        this.lores = lores != null ? lores : new ArrayList<>();
        this.weight = weight < 0 ? 20 : weight;
        this.tier = tier != null ? tier : ItemTier.COMMON;
    }
    
    public List<Augment> getAugments() {
        return this.augments;
    }
    
    public void setAugments(List<Augment> augments) {
        this.augments = augments;
    }
    
    public List<Gem> getGems() {
        return this.gems;
    }

    public void setGems(List<Gem> gems) {
        this.gems = gems;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLores() {
        return this.lores;
    }

    public void setLores(List<String> lores) {
        this.lores = lores;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTier(ItemTier tier) {
        this.tier = tier;
    }

    public ItemTier getTier() {
        return this.tier;
    }
}
