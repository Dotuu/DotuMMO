package me.dotu.MMO.Enums;

public enum GemType {
    FIRE_IMMUNE("FIRE_IMMUNE");

    private final String name;

    GemType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
