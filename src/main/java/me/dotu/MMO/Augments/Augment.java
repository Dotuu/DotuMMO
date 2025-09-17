package me.dotu.MMO.Augments;

import me.dotu.MMO.Enums.AugmentType;
import me.dotu.MMO.Enums.ItemTier;
import me.dotu.MMO.Managers.AugmentManager;

public class Augment extends AugmentManager {

    private ItemTier tier;
    private int minLevelToUse;
    private AugmentType augment;
    private String description;

    public Augment(ItemTier tier, int minLevelToUse, AugmentType augment, String desciption) {
        this.tier = tier;
        this.minLevelToUse = minLevelToUse;
        this.augment = augment;
        this.description = desciption;
    }

    public Integer getMinLevelToUse() {
        return this.minLevelToUse;
    }

    public void setMinLevelToUse(Integer minLevelToUse) {
        this.minLevelToUse = minLevelToUse;
    }

    public ItemTier getTier() {
        return this.tier;
    }

    public void setTier(ItemTier tier) {
        this.tier = tier;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public AugmentType getAugment() {
        return this.augment;
    }

    public void setAugment(AugmentType augment) {
        this.augment = augment;
    }
}
