package me.dotu.MMO.Enums;

import org.bukkit.NamespacedKey;

import me.dotu.MMO.Main;

public enum NamedKey {
    CUSTOM_ITEM,
    GEMS,
    AUGMENTS;
    
    private final NamespacedKey nsk;
    private static final String prefix = "DotuMMO_";

    NamedKey(){
        this.nsk = new NamespacedKey(Main.plugin, prefix + this.name());
    }

    public NamespacedKey getKey(){
        return this.nsk;
    }
}
