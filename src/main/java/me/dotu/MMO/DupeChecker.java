package me.dotu.MMO;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import me.dotu.MMO.Data.PlacedBlocksData;
import me.dotu.MMO.Enums.DropTableEnum;

public class DupeChecker implements Listener{

    private ArrayList<String> blockNames;

    public DupeChecker(){
        getBlockNameArray();
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event){
        String placedName = event.getBlock().getType().toString();
        for (String blockName : this.blockNames){
            if (blockName.equals(placedName)){
                Location placedLocation = event.getBlock().getLocation();
                PlacedBlocksData.placedBlocks.put(placedLocation, blockName);
            }
        }
    }

    public static boolean alreadyBroken(Location brokenAt){
        return PlacedBlocksData.placedBlocks.containsKey(brokenAt);
    }

    public static void removeFromPlacedBlocks(Location brokenAt){
        PlacedBlocksData.placedBlocks.remove(brokenAt);
    }

    private void getBlockNameArray(){
        ArrayList<String> blockNames = new ArrayList<>();
        for (DropTableEnum.MiningDrop drop : DropTableEnum.MiningDrop.values()){
            blockNames.add(drop.toString());
        }

        for (DropTableEnum.WoodcuttingDrop drop : DropTableEnum.WoodcuttingDrop.values()){
            blockNames.add(drop.toString());
        }

        this.blockNames = blockNames;
    }
}
