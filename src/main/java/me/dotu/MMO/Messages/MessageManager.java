package me.dotu.MMO.Messages;

import net.md_5.bungee.api.ChatColor;

public class MessageManager {
    public static enum Type{
        ACHIEVMENT,
        LEVEL_UP,
        FUN
    }

    private static String prefix = ChatColor.AQUA + "DotuMMO - ";

    public static String send(MessageManager.Type type, String message){
        switch(type){
            case ACHIEVMENT:
                return prefix + ChatColor.YELLOW + message + ".";
            case LEVEL_UP:
                return prefix + ChatColor.YELLOW + message + ".";
            case FUN:
                return prefix + ChatColor.LIGHT_PURPLE + message + ".";
            default:
                return message;
        }
    } 
}
