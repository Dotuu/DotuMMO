package me.dotu.MMO.Enums;

public enum AugmentType {
    BOW_POWER("Bow_Power");

    private final String name;

    AugmentType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}