package me.dotu.MMO.Commands;

import org.bukkit.command.CommandSender;

import me.dotu.MMO.Enums.PermissionEnum;

public class SpawnerSubCommand extends DotuMmoCommand implements SubCommand{

    @Override
    public String getName(){
        return "spawner";
    }

    @Override
    public String getPermission(){
        return PermissionEnum.Permissions.SPAWNER.getPermission();
    }

    @Override
    public boolean isConsoleSafe(){
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        
        return false;
    }
    
}
