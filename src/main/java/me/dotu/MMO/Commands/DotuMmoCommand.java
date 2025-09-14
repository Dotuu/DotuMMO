package me.dotu.MMO.Commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.dotu.MMO.Commands.SubCommands.ItemSubCommand;
import me.dotu.MMO.Commands.SubCommands.PvpSubCommand;
import me.dotu.MMO.Commands.SubCommands.SpawnerSubCommand;
import me.dotu.MMO.Commands.SubCommands.TableSubCommand;
import me.dotu.MMO.Managers.MessageManager;

public class DotuMmoCommand implements CommandExecutor {

    private final HashMap<String, SubCommand> subCommands = new HashMap<>();

    public DotuMmoCommand(SpawnerSubCommand spawnerSubCommand, PvpSubCommand pvpSubCommand, TableSubCommand tableSubCommand, ItemSubCommand itemSubCommand) {
        this.subCommands.put("spawner", spawnerSubCommand);
        this.subCommands.put("pvp", pvpSubCommand);
        this.subCommands.put("table", tableSubCommand);
        this.subCommands.put("item", itemSubCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("dotummo")){
            if (args.length == 0){
                // send command list
                 return true;
            }

            SubCommand subCommand = this.subCommands.get(args[0].toLowerCase());

            if (subCommand == null){
                // send sub command list
                return true;
            }

            if (subCommand.isConsoleSafe() == false){
                sender.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "The console cannot execute this command!"));
                return true;
            }

            subCommand.execute(sender, args);
        }
        return true;
    }
}
