package me.dotu.MMO.Inventories.CustomSpawner;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Inventories.CustomInventory;
import me.dotu.MMO.Inventories.CustomInventoryHolder;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;
import net.md_5.bungee.api.ChatColor;

public class SpawnerInventoryClicked implements Listener{
    @EventHandler
    private void clickedActionItem(InventoryClickEvent event){
        if (event.getInventory().getHolder() instanceof CustomInventoryHolder){
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            
            if (event.getClickedInventory() != event.getInventory()){
                return;
            }

            if (CustomInventory.isItem(event.getClickedInventory(), event.getSlot())){
                ItemStack item = event.getClickedInventory().getItem(event.getSlot());
                String spawnerName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

                if (!SpawnerConfig.spawners.containsKey(spawnerName)){
                    return;
                }

                CustomSpawner customSpawner = SpawnerConfig.spawners.get(spawnerName);
                ItemStack spawnerStack = CustomSpawnerHandler.decorateSpawnerStack(customSpawner);

                if (player.getInventory().addItem(spawnerStack).isEmpty()){
                    MessageManager.send(player, Messages.SPAWNER_ADDED, true, spawnerName);
                }
                else{
                    MessageManager.send(player, Messages.ERR_SPAWNER_ADDED, true, spawnerName);
                }
            }
        }
    }
}
