package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SpawnerConfig {
    private final JavaPlugin plugin;
    private final String filename = "spawners.json";
    public static HashMap<Location, HashMap<Location, Boolean>> spawners = new HashMap<>();

    public SpawnerConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        this.populateSpawnersMap();
    }

    public void saveAllSpawnerSettingsToFile() {
        File file = new File(this.plugin.getDataFolder(), this.filename);
        JsonObject root = new JsonObject();
        JsonObject spawnersObj = new JsonObject();

        for (Map.Entry<Location, HashMap<Location, Boolean>> entry : spawners.entrySet()) {
            String spawnerLocStr = serializeLocation(entry.getKey());
            HashMap<Location, Boolean> spawnLocList = entry.getValue();

            JsonArray locArray = new JsonArray();
            for (Map.Entry<Location, Boolean> locEntry : spawnLocList.entrySet()) {
                String locStr = serializeLocation(locEntry.getKey()) + ":" + locEntry.getValue();
                locArray.add(locStr);
            }
            spawnersObj.add(spawnerLocStr, locArray);
        }

        root.add("spawners", spawnersObj);

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateSpawnersMap() {
        File file = new File(this.plugin.getDataFolder(), this.filename);
        if (!file.exists()) {
            return;
        }
        try (FileReader reader = new FileReader(file)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject spawnersObj = root.getAsJsonObject("spawners");

            if (spawnersObj == null) {
                return;
            }

            for (String spawnerLocStr : spawnersObj.keySet()) {
                Location spawnerLoc = deSerializeLocation(spawnerLocStr);
                HashMap<Location, Boolean> spawnLocList = new HashMap<>();

                JsonArray locArray = spawnersObj.getAsJsonArray(spawnerLocStr);
                for (JsonElement spawnLocElem : locArray) {
                    String[] parts = spawnLocElem.getAsString().split(":");
                    Location loc = deSerializeLocation(parts[0]);
                    boolean canSpawn = Boolean.parseBoolean(parts[1]);
                    spawnLocList.put(loc, canSpawn);
                }
                spawners.put(spawnerLoc, spawnLocList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String serializeLocation(Location location) {
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return world + "." + x + "." + y + "." + z;
    }

    private Location deSerializeLocation(String location) {
        String[] locationData = location.split("\\.");
        World world = Bukkit.getServer().getWorld(locationData[0]);
        double x = Double.parseDouble(locationData[1]);
        double y = Double.parseDouble(locationData[2]);
        double z = Double.parseDouble(locationData[3]);
        return new Location(world, x, y, z);
    }
}