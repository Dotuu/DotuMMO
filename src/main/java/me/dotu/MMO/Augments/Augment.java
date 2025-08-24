package me.dotu.MMO.Augments;

import me.dotu.MMO.Enums.AugmentEnum;
import me.dotu.MMO.Enums.ItemEnum;
import me.dotu.MMO.Managers.AugmentManager;

public class Augment extends AugmentManager{

    private ItemEnum.Tier[] tiers;
    private int minLevelToUse;
    private AugmentEnum.Augment augment;
    private String description;
    private AugmentEnum.Category category;

    public Augment(ItemEnum.Tier tiers[], int minLevelToUse, AugmentEnum.Augment augment, String desciption, AugmentEnum.Category category) {
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

    public AugmentEnum.Augment getAugment() {
        return this.augment;
    }

    public void setAugment(AugmentEnum.Augment augment) {
        this.augment = augment;
    }
}
