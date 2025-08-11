package me.dotu.MMO.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Enums.PermissionEnum;
import me.dotu.MMO.Inventories.SpawnerSettingsInventory;

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
        Player player = (Player) sender;

        if (player.hasPermission(this.getPermission()) || this.hasAdminPermissions(player)){
            switch(args[1].toLowerCase()){
                case "create":
                    this.handleCreateSpawnerCommand(player);
                    break;
                case "edit":
                    break;
            }
        }
        return false;
    }
    
    private void handleCreateSpawnerCommand(Player player){
        SpawnerSettingsInventory settingsInv = new SpawnerSettingsInventory();
    }
}
