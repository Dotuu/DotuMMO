package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.Listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import me.dotu.MMO.Main;
import me.dotu.MMO.ChunkLoader.ChunkData;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Utils.LocationUtils;

public class ChunkDataConfig extends JsonFileManager implements Listener {
    public ChunkDataConfig() {
        super("data/chunkdata", "");
    }

    public static HashMap<String, ChunkData> loadedChunks = new HashMap<>();

    public void saveAllChunkDataToFile() {
        for (Map.Entry<String, ChunkData> chunk : loadedChunks.entrySet()) {
            ChunkData chunkData = chunk.getValue();
            String chunkId = chunk.getKey();

            if (chunkData.isUpdated() == false) {
                continue;
            }

            this.saveChunkDataToJson(chunkId, chunkData);
        }
        loadedChunks.clear();
    }

    public void saveChunkDataToJson(String chunkId, ChunkData chunkData) {
        File chunkDataFolder = new File(Main.plugin.getDataFolder(), "chunkdata");
        if (!chunkDataFolder.exists()) {
            chunkDataFolder.mkdirs();
        }

        File chunkFile = new File(new File(Main.plugin.getDataFolder(), "chunkdata"), chunkId + ".json");
        ArrayList<String> locations = new ArrayList<>();
        for (Location blockLocation : chunkData.getBlockLocations()) {
            String locationStr = LocationUtils.serializeLocation(blockLocation);
            locations.add(locationStr);
        }

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

    public ArrayList<Location> loadChunkDataFromJson(String identifier) {
        String filename = identifier + ".json";
        File chunkFile = new File(new File(Main.plugin.getDataFolder(), "chunkdata"), filename);

        try (FileReader reader = new FileReader(chunkFile)) {
            JsonArray locationsArray = JsonParser.parseReader(reader).getAsJsonArray();
            ArrayList<String> locationStrings = new ArrayList<>();

            for (JsonElement elem : locationsArray) {
                locationStrings.add(elem.getAsString());
            }
            ArrayList<Location> locations = new ArrayList<>();
            for (String locationStr : locationStrings) {
                Location location = LocationUtils.deSerializeLocation(locationStr);
                locations.add(location);
            }
            return locations;
        } catch (Exception e) {
            return new ArrayList<Location>();
        }
    }
}