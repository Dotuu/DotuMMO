package me.dotu.MMO.ChunkLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import me.dotu.MMO.Main;
import me.dotu.MMO.Enums.RewardTableEnum;

public class ChunkDataManager implements Listener {
    public static HashMap<String, ChunkData> loadedChunks = new HashMap<>();

    public void saveAllChunkDataToFile(){
        for (String chunkId : loadedChunks.keySet()){
            saveChunkDataToJson(chunkId);
        }
    }

    public void saveChunkDataToJson(String chunkId) {
        ChunkData chunkData = loadedChunks.get(chunkId);
        if (chunkData.isUpdated()) {
            File chunkDataFolder = new File(Main.plugin.getDataFolder(), "chunkdata");
            if (!chunkDataFolder.exists()) {
                chunkDataFolder.mkdirs();
            }

            File chunkFile = new File(new File(Main.plugin.getDataFolder(), "chunkdata"), chunkId + ".json");
            ArrayList<String> locations = this.serialize(chunkData.getBlockLocations());

            if (locations.isEmpty()) {
                if (chunkFile.exists()) {
                    chunkFile.delete();
                }
            } else {
                try (FileWriter writer = new FileWriter(chunkFile)) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    gson.toJson(locations, writer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Location> loadChunkDataFromJson(String identifier) {
        String filename = identifier + ".json";
        File chunkFile = new File(new File(Main.plugin.getDataFolder(), "chunkdata"), filename);

        try (FileReader reader = new FileReader(chunkFile)) {
            JsonArray locationsArray = JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<String> locationStrings = new ArrayList<>();

            for (JsonElement elem : locationsArray) {
                locationStrings.add(elem.getAsString());
            }
            return this.deSerialize(locationStrings);
        } catch (Exception e) {
            return new ArrayList<Location>();
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        String chunkId = this.getChunkIdentifier(event.getChunk());

        if (loadedChunks.containsKey(chunkId)) {
            this.saveChunkDataToJson(chunkId);
            loadedChunks.remove(chunkId);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        ArrayList<Material> blocks = this.getBlocksList();

        Material placed = event.getBlock().getType();
        if (blocks.contains(placed)) {
            Block placedBlock = event.getBlock();
            String chunkId = getChunkIdentifier(placedBlock.getChunk());

            if (!loadedChunks.containsKey(chunkId)){
                ArrayList<Location> blockLocations = this.loadChunkDataFromJson(chunkId);
                loadedChunks.put(chunkId, new ChunkData(blockLocations, chunkId, false));
            }
            else{
                ChunkData chunkData = loadedChunks.get(chunkId);
                chunkData.setUpdated(true);

                Location placedLoc = placedBlock.getLocation();
                chunkData.addBlockLocations(placedLoc);
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        ArrayList<Material> blocks = this.getBlocksList();
        Material broken = event.getBlock().getType();

        Block brokenBlock = event.getBlock();
        String chunkId = getChunkIdentifier(brokenBlock.getChunk());

        if (!loadedChunks.containsKey(chunkId)){
            ArrayList<Location> blockLocations = this.loadChunkDataFromJson(chunkId);
            loadedChunks.put(chunkId, new ChunkData(blockLocations, chunkId, false));
        }

        if (blocks.contains(broken)) {
            ChunkData chunkData = loadedChunks.get(chunkId);
            Location brokenLoc = brokenBlock.getLocation();
            chunkData.removeBlockLocations(brokenLoc);
            chunkData.setUpdated(true);
        }
    }

    public boolean wasBlockBroken(Block block) {
        ArrayList<Material> blocks = this.getBlocksList();
        Material blockMat = block.getType();

        if (blocks.contains(blockMat)) {
            String chunkId = this.getChunkIdentifier(block.getChunk());

            if (!loadedChunks.containsKey(chunkId)){
                ArrayList<Location> blockLocations = this.loadChunkDataFromJson(chunkId);
                loadedChunks.put(chunkId, new ChunkData(blockLocations, chunkId, false));
            }

            Location blockLoc = block.getLocation();
            ChunkData chunkData = loadedChunks.get(chunkId);

            return chunkData.getBlockLocations().contains(blockLoc);
        }
        return false;
    }

    public ArrayList<Material> getBlocksList() {
        ArrayList<Material> returnArray = new ArrayList<>();
        for (RewardTableEnum.MiningReward drop : RewardTableEnum.MiningReward.values()) {
            returnArray.add(drop.getMaterial());
        }

        for (RewardTableEnum.WoodcuttingReward drop : RewardTableEnum.WoodcuttingReward.values()) {
            returnArray.add(drop.getMaterial());
        }

        return returnArray;
    }

    private String getChunkIdentifier(Chunk chunk) {
        String x = Integer.toString(chunk.getX());
        String z = Integer.toString(chunk.getZ());
        String world = chunk.getWorld().getName();
        return world + "." + x + "." + z;
    }

    private ArrayList<String> serialize(ArrayList<Location> locations) {
        ArrayList<String> returnList = new ArrayList<>();

        for (int i = 0; i < locations.size(); i++) {
            Location loc = locations.get(i);

            String world = loc.getWorld().getName();
            String x = Integer.toString(loc.getBlockX());
            String y = Integer.toString(loc.getBlockY());
            String z = Integer.toString(loc.getBlockZ());

            returnList.add(world + "," + x + "," + y + "," + z);
        }

        return returnList;
    }

    private ArrayList<Location> deSerialize(ArrayList<String> locationStrings) {
        ArrayList<Location> returnList = new ArrayList<>();

        for (String value : locationStrings) {

            String[] locationData = value.split(",");
            World world = Bukkit.getServer().getWorld(locationData[0]);
            double x = Double.parseDouble(locationData[1]);
            double y = Double.parseDouble(locationData[2]);
            double z = Double.parseDouble(locationData[3]);
            Location listLoc = new Location(world, x, y, z);

            returnList.add(listLoc);
        }
        return returnList;
    }
}