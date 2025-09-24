package me.dotu.MMO.Managers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Enums.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class MessageManager  {
    private static final String PREFIX = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "DotuMMO" + ChatColor.DARK_GRAY + "] ";
    
    public static void send(CommandSender sender, Messages message, boolean usePrefix){
        String formattedMessage = usePrefix ? PREFIX + message.getMessage() : message.getMessage();
        sender.sendMessage(formattedMessage);
    }

    public static void send(CommandSender sender, Messages message, boolean usePrefix, Object... args){
        String baseMessage = String.format(message.getMessage(), args);
        String formattedMessage = usePrefix ? PREFIX + baseMessage : baseMessage;
        sender.sendMessage(formattedMessage);
    }

    public static void sendActionBar(Player player, Messages message, boolean usePrefix){
        String formattedMessage = usePrefix ? PREFIX + message.getMessage() : message.getMessage();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(formattedMessage));
    }

    public static void sendActionBar(Player player, Messages message, boolean usePrefix, Object... args){
        String baseMessage = String.format(message.getMessage(), args);
        String formattedMessage = usePrefix ? PREFIX + baseMessage : baseMessage;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(formattedMessage));
    }

    public static void sendToAll(Messages message, boolean usePrefix){
        String formattedMessage = usePrefix ? PREFIX + message.getMessage() : message.getMessage();
        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            player.sendMessage(formattedMessage);
        }
    }

    public static void sendToAll(Messages message, boolean usePrefix, Object... args){
        String baseMessage = String.format(message.getMessage(), args);
        String formattedMessage = usePrefix ? PREFIX + baseMessage : baseMessage;
        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            player.sendMessage(formattedMessage);
        }
    }

    public static void alert(String message){
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        for (Player player : Bukkit.getServer().getOnlinePlayers()){
            player.sendTitle("", coloredMessage, 10, 60, 20);
        }
    }
}
