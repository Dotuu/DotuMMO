package me.dotu.MMO.Gems;

import me.dotu.MMO.Enums.GemCategory;
import me.dotu.MMO.Enums.GemType;
import me.dotu.MMO.Enums.ItemTier;

public class Gem {
    private ItemTier[] tiers;
    private int minLevelToUse;
    private GemType gem;
    private String description;
    private GemCategory category;

    public Gem(ItemTier tiers[], int minLevelToUse, GemType gem, String desciption, GemCategory category) {
        this.tiers = tiers;
        this.minLevelToUse = minLevelToUse;
        this.gem = gem;
        this.description = desciption;
        this.category = category;
    }

    public ItemTier[] getTiers() {
        return this.tiers;
    }

    public void setTiers(ItemTier[] tiers) {
        this.tiers = tiers;
    }

    public int getMinLevelToUse() {
        return this.minLevelToUse;
    }

    public void setMinLevelToUse(int minLevelToUse) {
        this.minLevelToUse = minLevelToUse;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GemCategory getCategory() {
        return this.category;
    }

    public void setCategory(GemCategory category) {
        this.category = category;
    }

    public GemType getGem() {
        return this.gem;
    }

    public void setGem(GemType gem) {
        this.gem = gem;
    }

}
