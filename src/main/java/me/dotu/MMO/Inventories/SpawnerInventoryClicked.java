package me.dotu.MMO.Inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Managers.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class SpawnerInventoryClicked implements Listener{
    @EventHandler
    private void inventoryClick(InventoryClickEvent event){
        if (event.getInventory().getHolder() instanceof SpawnerHolder){
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            
            if (event.getClickedInventory() != event.getInventory()){
                return;
            }

            if (CustomInventory.isItem(event.getClickedInventory(), event.getSlot())){
                ItemStack item = event.getClickedInventory().getItem(event.getSlot());
                if (player.getInventory().addItem(item).isEmpty()){
                    player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Spawner " + ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " added to inventory."));
                }
                else{
                    player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Swawner not added to inventory, please ensure there is room!"));
                }
            }
        }
    }
}
