package me.dotu.MMO.Commands;

import org.bukkit.command.CommandSender;

public class SpawnerSubCommand implements SubCommand{

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        
        return false;
    }
    
    @Override
    public String getName(){
        return "spawner";
    }
}
