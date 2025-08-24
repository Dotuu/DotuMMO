package me.dotu.MMO.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Enums.PermissionEnum;
import me.dotu.MMO.Inventories.SpawnerInventory;

public class SpawnerSubCommand implements SubCommand{
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

        if (player.hasPermission(this.getPermission()) || player.hasPermission(PermissionEnum.Permissions.ADMIN.getPermission())){
            if (args.length == 1){
                // send sub command list
                return false;
            }

            switch(args[1].toLowerCase()){
                case "add":
                    this.handleAddSpawnerCommand(player, args);
                    break;
                case "edit":
                    break;
            }
        }
        return false;
    }
    
    private void handleAddSpawnerCommand(Player player, String[] args){
        if (args.length == 2){
            SpawnerInventory spawnerInv = new SpawnerInventory(player, 0);
            spawnerInv.createInventoryItems();
            spawnerInv.openInventory(player);
        }
        else{

        }
    }
}
