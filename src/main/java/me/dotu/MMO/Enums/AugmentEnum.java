package me.dotu.MMO.Enums;

public class AugmentEnum {
    public static enum Augment {
        SLOW_EAT("slow_eat");

        private final String name;

        Augment(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }
    }
}