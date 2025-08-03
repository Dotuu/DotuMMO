package me.dotu.MMO.Enums;

import net.md_5.bungee.api.ChatColor;

public class ItemEnum {
    public static enum Tier{
        COMMON (ChatColor.GRAY),
        UNCOMMON (ChatColor.GREEN),
        RARE (ChatColor.AQUA),
        EPIC (ChatColor.LIGHT_PURPLE),
        LEGENDARY (ChatColor.GOLD),
        MYTHIC (ChatColor.RED);

        private final ChatColor color;

        Tier(ChatColor color){
            this.color = color;
        }

        public ChatColor getColor(){
            return this.color;
        }
    }
}
