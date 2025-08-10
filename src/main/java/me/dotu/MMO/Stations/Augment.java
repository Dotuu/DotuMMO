package me.dotu.MMO.Stations;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Augment implements Listener{

    private final Material station = Material.SMITHING_TABLE;

    public Augment(){
        
    }

    @EventHandler
    public void augmentBlockClick(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            if (block != null && block.getType() == this.station){
                event.setCancelled(true);
            }
        }
    }
    
}
