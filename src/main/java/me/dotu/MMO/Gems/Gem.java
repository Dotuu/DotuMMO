package me.dotu.MMO.Gems;

import java.util.ArrayList;

import me.dotu.MMO.Enums.GemType;
import me.dotu.MMO.Enums.ItemTier;

public class Gem {
    public static ArrayList<Gem> gems = new ArrayList<>();
    private ItemTier tier;
    private int minLevelToUse;
    private GemType gem;
    private String description;

    public Gem(ItemTier tier, int minLevelToUse, GemType gem, String desciption) {
        this.tier = ItemTier.COMMON;
        this.minLevelToUse = minLevelToUse;
        this.gem = gem;
        this.description = desciption;
    }

    public Gem(int minLevelToUse, GemType gem, String desciption) {
        this.tier = ItemTier.COMMON;
        this.minLevelToUse = minLevelToUse;
        this.gem = gem;
        this.description = desciption;
    }

    public ItemTier getTier() {
        return this.tier;
    }

    public void setTier(ItemTier tier) {
        this.tier = tier;
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

    public GemType getGem() {
        return this.gem;
    }

    public void setGem(GemType gem) {
        this.gem = gem;
    }

}
