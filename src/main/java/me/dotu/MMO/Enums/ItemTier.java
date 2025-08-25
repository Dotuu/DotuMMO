package me.dotu.MMO.Enums;

import net.md_5.bungee.api.ChatColor;

public enum ItemTier{
    COMMON (ChatColor.GRAY),
    UNCOMMON (ChatColor.GREEN),
    RARE (ChatColor.AQUA),
    EPIC (ChatColor.LIGHT_PURPLE),
    LEGENDARY (ChatColor.GOLD),
    MYTHIC (ChatColor.RED);

    private final ChatColor color;

    ItemTier(ChatColor color){
        this.color = color;
    }

    public ChatColor getColor(){
        return this.color;
    }
}
