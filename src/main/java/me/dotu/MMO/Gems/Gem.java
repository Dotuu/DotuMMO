package me.dotu.MMO.Gems;

import me.dotu.MMO.Enums.ItemEnum;

public abstract class Gem {
    private String name;
    private int minLevelToUse;
    private ItemEnum.Tier[] tiers;

    public Gem(String name, int minLevelToUse, ItemEnum.Tier[] tiers){
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinLevelToUse() {
        return minLevelToUse;
    }

    public void setMinLevelToUse(int minLevelToUse) {
        this.minLevelToUse = minLevelToUse;
    }

    public ItemEnum.Tier[] getTiers() {
        return tiers;
    }

    public void setTiers(ItemEnum.Tier[] tiers) {
        this.tiers = tiers;
    }
}
