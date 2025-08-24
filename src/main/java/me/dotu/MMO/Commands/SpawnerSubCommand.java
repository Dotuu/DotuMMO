package me.dotu.MMO.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Enums.PermissionEnum;
import me.dotu.MMO.Inventories.SpawnerInventory;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;
import net.md_5.bungee.api.ChatColor;

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
                    this.handleEditSpawnerCommand(player, args);
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
            String spawnerName = args[2];
            if (SpawnerConfig.spawners.containsKey(spawnerName)){
                CustomSpawner customSpawner = SpawnerConfig.spawners.get(spawnerName);
                ItemStack item = CustomSpawnerHandler.decorateSpawnerStack(customSpawner);

                player.getInventory().addItem(item).isEmpty();

                player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Spawner " + ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " added to inventory"));
            }
            else{
                player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Swawner not found, name is case sensitive"));
            }
        }
    }

    private void handleEditSpawnerCommand(Player player, String[] args){

    }
}
