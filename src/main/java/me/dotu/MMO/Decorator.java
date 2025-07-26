package me.dotu.MMO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Decorator {
    public static ItemMeta decorate(ItemStack item, HashMap<String, String> props){
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        for (String key : props.keySet()){
            String value = props.get(key);
            lore.add(ChatColor.GRAY + key + " - " + value);
        }

        meta.setLore(lore);
        
        return meta;
    }
}
