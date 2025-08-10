package me.dotu.MMO.Commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    String getName();
    String getPermission();
    boolean isConsoleSafe();
    boolean execute(CommandSender sender, String[] args);
}
