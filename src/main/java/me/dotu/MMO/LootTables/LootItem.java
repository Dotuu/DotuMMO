package me.dotu.MMO.LootTables;

import java.util.List;

import org.bukkit.Material;

import me.dotu.MMO.Augments.Augment;
import me.dotu.MMO.Enums.ItemEnum;
import me.dotu.MMO.Gems.Gem;

public class LootItem {
    private List<Augment> augments;
    private List<Gem> gems;
    private Material material;
    private String displayName;
    private List<String> lores;
    private int weight;
    private ItemEnum.Tier[] tiers;

    public LootItem(List<Augment> augments, List<Gem> gems, Material material, String displayName, List<String> lores,
            int weight, ItemEnum.Tier[] tiers) {
        this.augments = augments;
        this.gems = gems;
        this.material = material;
        this.displayName = displayName;
        this.lores = lores;
        this.tiers = tiers;
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
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setTiers(ItemEnum.Tier[] tier) {
        this.tiers = tier;
    }

    public ItemEnum.Tier[] getTiers() {
        return this.tiers;
    }
}
