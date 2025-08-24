package me.dotu.MMO.Enums;

public class AugmentEnum {
    public static enum Augment {
        BOW_POWER("Bow_Power");

        private final String name;

        Augment(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }
    }

    public static enum Category{
        BOW
    }
}