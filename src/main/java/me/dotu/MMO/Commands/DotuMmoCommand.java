package me.dotu.MMO.Commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Enums.PermissionEnum;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Managers.PvpManager;
import me.dotu.MMO.Managers.SettingsManager;

public class DotuMmoCommand implements CommandExecutor{

    private final String prefix = "dotummo.";
    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    public DotuMmoCommand(){
        this.subCommands.put("spawner", new SpawnerSubCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("dotummo")){
            if (!this.isPlayer(sender)){
                if (args[0].equalsIgnoreCase("pvp")){
                    switch (args[1].toLowerCase()){
                        default:
                            this.sendHelpListConsole();
                            break;
                    }
                }
                
                else if (args[0].equalsIgnoreCase("reload")){
                    
                }
                
                else{
                    this.sendHelpListConsole();
                }
            }
            else{
                Player player = (Player) sender;
                if (player.hasPermission(this.prefix + PermissionEnum.Permissions.STARTSEASON.getPermission()) || this.hasAdminPermissions(player)){
                    if (args[0].equalsIgnoreCase("pvp")){
                        switch(args[1].toLowerCase()){
                            case "startseason":
                                if (this.handleStartSeasonCommand()){
                                    player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "New season started, all stats have been reset"));
                                    break;
                                }
                                player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Something went wrong, please check console for logs"));
                                break;
                            default:
                                this.sendHelpListPlayer();
                                break;
                        }
                    }

                    else if (args[0].equalsIgnoreCase("reload")){

                    }

                    else{
                        this.sendHelpListPlayer();
                    }
                }
            }
        }
        return false;
    }

    public boolean hasAdminPermissions(Player player){
        return player.hasPermission(PermissionEnum.Permissions.ADMIN.getPermission());
    }

    public boolean isPlayer(CommandSender sender){
        return sender instanceof Player;
    }

    public boolean handleStartSeasonCommand(){
        try {
            PvpManager pvpManager = new PvpManager();
            pvpManager.resetPvpSeasonForOnlinePlayers();
            
            SettingsManager settingsManager = SettingsConfig.settingsMap.get(ConfigEnum.Settings.PVP);
            settingsManager.setSettingsLong(ConfigEnum.Settings.PVP, "season_timer", System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendHelpListPlayer(){

    }

    public void sendHelpListConsole(){
        
    }
}
