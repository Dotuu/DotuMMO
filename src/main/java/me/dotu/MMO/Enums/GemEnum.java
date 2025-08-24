package me.dotu.MMO.Enums;

public class GemEnum {
    public static enum Gem {
        FIRE_IMMUNE("Fire Immune");

        private final String name;

        Gem(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }
    }

    public static enum Category {
        FIRE,
        POISON
    }
}
