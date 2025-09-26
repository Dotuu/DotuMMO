package me.dotu.MMO.Enums;

import net.md_5.bungee.api.ChatColor;

interface MessageColors{
    ChatColor colorBase = ChatColor.YELLOW;
    ChatColor colorErr = ChatColor.RED;
    ChatColor colorItem = ChatColor.BLUE;
    ChatColor colorCmd = ChatColor.AQUA;
}

public enum Messages implements MessageColors{
    // SPAWNER MESSAGES
    SPAWNER_CREATED(colorBase + "Spawner " + colorItem + "%s" + colorBase + " created"),
    SPAWNER_ADDED(colorBase + "Spawner " + colorItem + "%s" + colorBase + " added to inventory"),
    ERR_SPAWNER_ADDED(colorErr + "Swawner not added to inventory, please ensure there is room!"),
    ERR_SPAWNER_EXISTS(colorErr + "Swawner not found, name is case sensitive"),
    ERR_SPAWNER_BLOCK_NOT_FOUND(colorErr + "Valid spawner block not found, within 10 blocks"),
    ERR_SPAWNER_VALID_BLOCK(colorErr + "Target block is not a spawner block"),
    ERR_SPAWNER_CUSTOM_SPAWNER(colorErr + "Target block is not DotuMMO custom spawner"),
    ERR_SPAWNER_GENERIC(colorErr + "Spawner not found"),

    // EDIT MODE MESSAGES
    EDIT_MODE_LEAVE(colorBase + "Leaving editing mode"),
    EDIT_MODE_ENTER(colorBase + "Now in edit mode. use " + colorCmd + "/dotummo spawner edit " + colorBase + "to exit"),
    EDIT_MODE_INFO(colorBase + "Left click block to set a spawn block, right click to remove"),
    EDIT_MODE_LOC_ADD(colorBase + "Addded spawn location"),
    EDIT_MODE_LOC_REMOVE(colorBase + "Removed spawn location"),

    // LOOT TABLE MESSAGES
    TABLE_CREATED(colorBase + "Created table " + colorItem + "%s"),
    ERR_TABLE_ALREADY_EXISTS(colorErr + "A table with this name already exists"),
    ERR_TABLE_EXISTS(colorErr + "A table with the name " + colorItem + "%s" + colorErr + " does not exist"),
    ERR_TABLE_NAME(colorErr + "Table name can only contain letters, numbers, and underscores"),
    ERR_GLOBAL_TABLE_EXISTS(colorErr + "Operation failed, global table does not exists."),
    ERR_TABLE_WEIGHT(colorErr + "The weight argument must be a number, you entered " + colorItem + "%s"),

    // ITEM TABLE MESSAGES
    HAND_ITEM_CREATED(colorBase + "Successfully created loot item " + colorItem + "%s"),
    ITEM_CREATED_INV(colorBase + "All valid inventory items added to " + colorItem + "%s"),
    ITEM_CREATED(colorBase + "Hand item added to " + colorItem + "%s"),
    ERR_HAND_ITEM_NAME(colorErr + "Hand item requires name"),
    ERR_HAND_ITEM_NONE(colorErr + "Command requires an item in hand"),
    ERR_HAND_ITEM_TABLE(colorErr + "Hand item is not a valid drop table item"),
    ERR_ITEM_IN_TABLE(colorErr + "This item is not in the global table, does the global table exist?"),
    ERR_ITEM_DOTUMMO(colorErr + "Hand item is not a valid DotuMMO item"),

    // PVP SEASON TIMER MESSAGES
    SEASON_RESET(colorBase + "New season started, all stats have been reset"),

    // SKILL MESSAGES
    SKILL_SUCCESS(colorBase + "Earned " + colorItem + "%s" + colorBase + " xp from " + colorItem + "%s"),
    ERR_GENERIC_SKILL_LEVEL(colorErr + "You need to be " + colorItem + "%s" + colorBase + " level " + colorItem + "%s" + colorBase + " to do this"),

    // GENERIC MESSAGES
    ERR_CONSOLE_UNSAFE(colorErr + "The console cannot execute this command"),
    ERR_GENERIC_ARGS(colorErr + "You entered too few args, please check the command syntax"),
    ERR_GENERIC(colorErr + "Something went wrong, please check console for logs"),

    // PLACEHOLDER MESSAGE CUZ IM LAZY LOL
    PLACEHOLDER("LOL");
    
    private final String message;
    
    Messages(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message.matches(".*[?!]$") ? this.message : this.message + ".";
    }

    public String format(Object... args){
        return String.format(this.message, args);
    }
}
