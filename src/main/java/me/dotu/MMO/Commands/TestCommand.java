package me.dotu.MMO.Commands;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import me.dotu.MMO.ChunkLoader.ChunkDataManager;
import me.dotu.MMO.Decorator;
import me.dotu.MMO.Enums.ItemEnum;
import me.dotu.MMO.ItemData.Armor;

public class TestCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {
            if (sender instanceof Player){

                Player player = (Player) sender;

                Armor dotu = new Armor("Dotu's Helmet of FIRE", 100, (short) 2, 100, "overworld", Material.DIAMOND_HELMET, ItemEnum.Tier.COMMON);
                ItemStack item = new ItemStack(Material.DIAMOND_HELMET, 1);

                HashMap<String, String> props = new HashMap<>();
                props.put("Fire resistance", "1");
                props.put("Food regen", "3");

                ItemMeta decorate = Decorator.decorate(item, props);
                item.setItemMeta(decorate);
                
                Damageable damageable = (Damageable) decorate;
                damageable.setDamage(100);

                decorate.setDisplayName("Dotu's helm of fireeee");
                

                player.getInventory().addItem(item);
            }else {
                sender.sendMessage("This command can only be used by players.");
            }
            return true;
        }
        else if (command.getName().equalsIgnoreCase("chunktest")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                player.sendMessage(Integer.toString(ChunkDataManager.loadedChunks.size()));
            }
            else{
                System.out.println(Integer.toString(ChunkDataManager.loadedChunks.size()));
            }
        }
        return false;
    }
}
