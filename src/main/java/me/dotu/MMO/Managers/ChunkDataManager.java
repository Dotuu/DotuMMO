package me.dotu.MMO.Managers;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import me.dotu.MMO.ChunkLoader.ChunkData;
import me.dotu.MMO.Configs.ChunkDataConfig;
import me.dotu.MMO.Configs.ExpTableConfig;
import me.dotu.MMO.Tables.ExpTable;

public class ChunkDataManager implements Listener {

    private ChunkDataConfig chunkDataConfig;

    private final String[] skillsToFetch = {"mining", "woodcutting"};

    public ChunkDataManager() {
        this.chunkDataConfig = new ChunkDataConfig();
    }

    @EventHandler
    public void chunkUnloadEvent(ChunkUnloadEvent event){
        String chunkId = this.getChunkIdentifier(event.getChunk());

        if (!ChunkDataConfig.loadedChunks.containsKey(chunkId)){
            return;
        }

        ChunkData chunkData = ChunkDataConfig.loadedChunks.get(chunkId);
        chunkData.setLoaded(false);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        ArrayList<Material> blocks = this.getBlocksList(this.skillsToFetch);
        Material placed = event.getBlock().getType();

        Block placedBlock = event.getBlock();
        String chunkId = getChunkIdentifier(placedBlock.getChunk());

        if (!blocks.contains(placed)) {
            return;
        }

        if (!ChunkDataConfig.loadedChunks.containsKey(chunkId)) {
            ArrayList<Location> blockLocations = chunkDataConfig.loadChunkDataFromJson(chunkId);
            ChunkDataConfig.loadedChunks.put(chunkId, new ChunkData(blockLocations, chunkId, true, true));
        }

        ChunkData chunkData = ChunkDataConfig.loadedChunks.get(chunkId);
        chunkData.setUpdated(true);
        Location placedLoc = placedBlock.getLocation();
        chunkData.addBlockLocations(placedLoc);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        ArrayList<Material> blocks = this.getBlocksList(this.skillsToFetch);
        Material broken = event.getBlock().getType();

        Block brokenBlock = event.getBlock();
        String chunkId = getChunkIdentifier(brokenBlock.getChunk());

        if (!blocks.contains(broken)) {
            return;
        }

        if (!ChunkDataConfig.loadedChunks.containsKey(chunkId)) {
            ArrayList<Location> blockLocations = chunkDataConfig.loadChunkDataFromJson(chunkId);
            ChunkDataConfig.loadedChunks.put(chunkId, new ChunkData(blockLocations, chunkId, false, true));
        }

        ChunkData chunkData = ChunkDataConfig.loadedChunks.get(chunkId);
        Location brokenLoc = brokenBlock.getLocation();
        chunkData.removeBlockLocations(brokenLoc);
        chunkData.setUpdated(true);
    }

    public boolean wasBlockBroken(Block block) {
        ArrayList<Material> blocks = this.getBlocksList(this.skillsToFetch);
        Material broken = block.getType();

        if (blocks.contains(broken)) {
            String chunkId = this.getChunkIdentifier(block.getChunk());

            if (!ChunkDataConfig.loadedChunks.containsKey(chunkId)) {
                ArrayList<Location> blockLocations = chunkDataConfig.loadChunkDataFromJson(chunkId);
                ChunkDataConfig.loadedChunks.put(chunkId, new ChunkData(blockLocations, chunkId, false, true));
            }

            Location blockLoc = block.getLocation();
            ChunkData chunkData = ChunkDataConfig.loadedChunks.get(chunkId);

            return chunkData.getBlockLocations().contains(blockLoc);
        }
        return false;
    }

    public ArrayList<Material> getBlocksList(String[] skills) {
        ArrayList<Material> returnArray = new ArrayList<>();

        for (String skill : skills){
            ExpTable<?> expTables = ExpTableConfig.expTables.get(skill);
            if (expTables.isMaterialTable()){
                for (Material material : expTables.asMaterials()){
                    returnArray.add(material);
                }
            }
        }

        return returnArray;
    }

    private String getChunkIdentifier(Chunk chunk) {
        String x = Integer.toString(chunk.getX());
        String z = Integer.toString(chunk.getZ());
        String world = chunk.getWorld().getName();
        return world + "." + x + "." + z;
    }
}