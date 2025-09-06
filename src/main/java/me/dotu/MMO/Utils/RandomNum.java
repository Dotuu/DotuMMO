package me.dotu.MMO.Utils;

public class RandomNum {
    public static int getRandom(int num1, int num2){
        if (num1 > num2){
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        int range = (num2 - num1) + 1;
        return num1 + (int)(Math.random() * range);
    }
}
