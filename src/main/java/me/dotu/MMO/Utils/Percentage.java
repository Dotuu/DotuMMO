package me.dotu.MMO.Utils;

public class Percentage {
    public static boolean isSuccessRollPercent(int num){
        if (num > 100){
            return false;
        }
        int rand = RandomNum.getRandom(0, 100);
        return num >= rand;
    }
}
