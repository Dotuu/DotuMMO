package me.dotu.MMO.Inventories;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class CustomInventoryClicked implements Listener{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory clickedInv = event.getClickedInventory();

        if (clickedInv == null){
            return;
        }

        InventoryHolder holder = clickedInv.getHolder();
        if (holder instanceof CustomInventoryHolder){
            CustomInventoryHolder spawnerHolder = (CustomInventoryHolder) holder;
            CustomInventory customInv = spawnerHolder.getCustomInventory();

            if (customInv == null){
                return;
            }

            ItemStack clickedItem = clickedInv.getItem(event.getSlot());
    
            if (clickedItem == null){
                return;
            }

            if (clickedItem.getType() != Material.PAPER){
                return;
            }

            if (clickedItem.hasItemMeta() == false){
                return;
            }
            
            ItemMeta meta = clickedItem.getItemMeta();

            if (meta.hasDisplayName() == false){
                return;
            }

            String displayName = meta.getDisplayName();

            if (displayName.equals(ChatColor.YELLOW + "Next Page")){
                int maxPage = customInv.contents.size() / customInv.usableSize;
                int currentPage = customInv.startingArrayIndex == 0 ? 0 : customInv.startingArrayIndex / customInv.usableSize;
                if (currentPage < maxPage){
                    customInv.setupNewPage(true);
                }
            }

            if (displayName.equals(ChatColor.YELLOW + "Previous Page")){
                if (customInv.startingArrayIndex != 0){
                    customInv.setupNewPage(false);
                }
            }
        }
    }
}
