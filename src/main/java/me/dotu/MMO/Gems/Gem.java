package me.dotu.MMO.Gems;

import me.dotu.MMO.Enums.GemEnum;
import me.dotu.MMO.Enums.ItemEnum;

public class Gem {
    private ItemEnum.Tier[] tiers;
    private int minLevelToUse;
    private GemEnum.Gem gem;
    private String description;
    private GemEnum.Category category;

    public Gem(ItemEnum.Tier tiers[], int minLevelToUse, GemEnum.Gem gem, String desciption, GemEnum.Category category) {
        this.tiers = tiers;
        this.minLevelToUse = minLevelToUse;
        this.gem = gem;
        this.description = desciption;
        this.category = category;
    }

    public ItemEnum.Tier[] getTiers() {
        return this.tiers;
    }

    public void setTiers(ItemEnum.Tier[] tiers) {
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

    public GemEnum.Category getCategory() {
        return this.category;
    }

    public void setCategory(GemEnum.Category category) {
        this.category = category;
    }

    public GemEnum.Gem getGem() {
        return this.gem;
    }

    public void setGem(GemEnum.Gem gem) {
        this.gem = gem;
    }

}
