package me.dotu.MMO.Gems;

import me.dotu.MMO.Enums.GemEnum;
import me.dotu.MMO.Enums.ItemEnum;

public class Gem {
    private ItemEnum.Tier[] tiers;
    private int minLevelToUse;
    private String name;
    private String description;
    private GemEnum.Category category;

    public Gem(ItemEnum.Tier tiers[], int minLevelToUse, String name, String desciption, GemEnum.Category category) {
        this.tiers = tiers;
        this.minLevelToUse = minLevelToUse;
        this.name = name;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

}
