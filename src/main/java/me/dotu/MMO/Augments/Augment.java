package me.dotu.MMO.Augments;

import me.dotu.MMO.Enums.AugmentCategory;
import me.dotu.MMO.Enums.AugmentType;
import me.dotu.MMO.Enums.ItemTier;
import me.dotu.MMO.Managers.AugmentManager;

public class Augment extends AugmentManager {

    private ItemTier[] tiers;
    private int minLevelToUse;
    private AugmentType augment;
    private String description;
    private AugmentCategory category;

    public Augment(ItemTier[] tiers, int minLevelToUse, AugmentType augment, String desciption,
            AugmentCategory category) {
        this.tiers = tiers;
        this.minLevelToUse = minLevelToUse;
        this.augment = augment;
        this.description = desciption;
        this.category = category;
    }

    public Integer getMinLevel() {
        return this.minLevelToUse;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevelToUse = minLevel;
    }

    public ItemTier[] getTiers() {
        return this.tiers;
    }

    public void setTiers(ItemTier[] tiers) {
        this.tiers = tiers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AugmentCategory getCategory() {
        return category;
    }

    public void setCategory(AugmentCategory category) {
        this.category = category;
    }

    public AugmentType getAugment() {
        return this.augment;
    }

    public void setAugment(AugmentType augment) {
        this.augment = augment;
    }
}
