package me.dotu.MMO.Commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Enums.PermissionEnum;
import me.dotu.MMO.Inventories.SpawnerSettingsInventory;

public class SpawnerSubCommand implements SubCommand{

    private static final HashMap<UUID, SpawnerSettingsInventory> spawnerSettingsSessions = new HashMap<>();


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
        SpawnerSettingsInventory inv = spawnerSettingsSessions.computeIfAbsent(
            player.getUniqueId(), uuid -> new SpawnerSettingsInventory()
        );
        inv.openInventory(player, spawnerSettingsSessions);
    }
}
