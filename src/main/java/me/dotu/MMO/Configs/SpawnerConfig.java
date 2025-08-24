package me.dotu.MMO.Configs;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Spawners.CustomSpawner;

public class SpawnerConfig extends JsonFileManager {
    public static HashMap<String, CustomSpawner> spawners = new HashMap<>();

    public SpawnerConfig() {
        super("data", "spawners");

        List<ConfigEnum.Type> defaults = Arrays.asList(
            ConfigEnum.Type.SPAWNER_DATA
        );

        this.setupDefaults(defaults);

        this.populateSpawnersMap();
    }

    public void saveAllSpawnerSettingsToFile() {
    JsonObject root = new JsonObject();

    for (CustomSpawner spawner : spawners.values()) {
        JsonObject spawnerObj = new JsonObject();
        spawnerObj.addProperty("min_level", spawner.getMinLevel());
        spawnerObj.addProperty("max_level", spawner.getMaxLevel());
        spawnerObj.addProperty("min_spawn_delay", spawner.getMinSpawnDelay());
        spawnerObj.addProperty("max_spawn_delay", spawner.getMaxSpawnDelay());
        spawnerObj.addProperty("spawn_range", spawner.getSpawnRange());
        spawnerObj.addProperty("difficulty", spawner.getDifficulty());
        spawnerObj.addProperty("armored", spawner.isArmored());
        spawnerObj.addProperty("weaponed", spawner.isWeaponed());
        spawnerObj.addProperty("name_visible", spawner.isNameVisible());
        spawnerObj.addProperty("spawn_randomly", spawner.isSpawnRandomly());
        spawnerObj.addProperty("table", spawner.getTable());

        // Save spawn_locations
        JsonArray spawnLocationsArray = new JsonArray();
        for (Location loc : spawner.getSpawnLocations()) {
            spawnLocationsArray.add(serializeLocation(loc)); // Use your location serialization method
        }
        spawnerObj.add("spawn_locations", spawnLocationsArray);

        root.add(spawner.getName(), spawnerObj);
    }

    try (FileWriter writer = new FileWriter(this.file)) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(root, writer);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void populateSpawnersMap() {
        spawners.clear();

        try (FileReader reader = new FileReader(this.file)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            
            for (String name : root.keySet()) {
                JsonObject spawnerObj = root.getAsJsonObject(name);

                int minLevel = this.getIntFromJson(spawnerObj, "min_level");
                int maxLevel = this.getIntFromJson(spawnerObj, "max_level");
                int minSpawnDelay = this.getIntFromJson(spawnerObj, "min_spawn_delay");
                int maxSpawnDelay = this.getIntFromJson(spawnerObj, "max_spawn_delay");
                int spawnRange = this.getIntFromJson(spawnerObj, "spawn_range");

                double difficulty = this.getDoubleFromJson(spawnerObj, "difficulty");

                boolean armored = this.getBooleanFromJson(spawnerObj, "armored");
                boolean weaponed = this.getBooleanFromJson(spawnerObj, "weaponed");
                boolean nameVisible = this.getBooleanFromJson(spawnerObj, "name_visible");
                boolean spawnRandomly = this.getBooleanFromJson(spawnerObj, "spawn_randomly");

                String table = this.getStringFromJson(spawnerObj, "table");

                JsonArray spawnLocationsJson = spawnerObj.get("spawn_locations").getAsJsonArray();
                ArrayList<Location> spawnLocations = new ArrayList<>();

                for (int y = 0; y < spawnLocationsJson.size(); y++) {
                    String locationString = spawnLocationsJson.get(y).getAsString();
                    Location listLoc = this.deSerializeLocation(locationString);
                    spawnLocations.add(listLoc);
                }

                CustomSpawner customSpawner = new CustomSpawner(minLevel, maxLevel, difficulty, armored, weaponed,
                        nameVisible, spawnRandomly, name, table, minSpawnDelay, maxSpawnDelay, spawnRange);

                customSpawner.setSpawnLocations(spawnLocations);

                spawners.put(name, customSpawner);
            }
        } catch (Exception e) {
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