package me.dotu.MMO.Commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Enums.PermissionEnum;
import me.dotu.MMO.Managers.MessageManager;

public class DotuMmoCommand implements CommandExecutor{

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    public DotuMmoCommand(){
        this.subCommands.put("spawner", new SpawnerSubCommand());
        this.subCommands.put("pvp", new PvpSubCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("dotummo")){
            if (sender instanceof Player){
                if (args.length == 0){
                    this.sendHelpListPlayer();
                    return true;
                }

                SubCommand sub = subCommands.get(args[0].toLowerCase());
                if (sub != null){
                    sub.execute(sender, args);
                }
                else{
                    this.sendHelpListPlayer();
                }
            }
            else{
                if (args.length == 0){
                    this.sendHelpListConsole();
                    return true;
                }

                SubCommand sub = subCommands.get(args[0].toLowerCase());
                if (sub != null){
                    if (sub.isConsoleSafe()){
                        sub.execute(sender, args);
                    }
                    else{
                        MessageManager.send(MessageManager.Type.ERROR, "The console cannot execute this command!");
                    }
                }
                else{
                    this.sendHelpListConsole();
                }
            }
        }
        return false;
    }

    public boolean hasAdminPermissions(Player player){
        return player.hasPermission(PermissionEnum.Permissions.ADMIN.getPermission());
    }

    public void sendHelpListPlayer(){

    }

    public void sendHelpListConsole(){
        
    }
}
