package me.dotu.MMO.Enums;

public enum GemType {
    FIRE_IMMUNE("Fire Immune");

    private final String name;

    GemType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
