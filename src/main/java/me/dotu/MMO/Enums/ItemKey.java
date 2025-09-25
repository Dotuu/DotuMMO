package me.dotu.MMO.Enums;

import org.bukkit.NamespacedKey;

import me.dotu.MMO.Main;

public enum ItemKey {
    CUSTOM_ITEM_ID,
    GEMS,
    AUGMENTS,
    LORES;
    
    private final NamespacedKey nsk;
    private static final String prefix = "DotuMMO_";

    ItemKey(){
        this.nsk = new NamespacedKey(Main.plugin, prefix + this.name());
    }

    public NamespacedKey getKey(){
        return this.nsk;
    }
}
