package me.dotu.MMO.Configs;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Spawners.SpawnerLocationData;
import me.dotu.MMO.Utils.LocationUtils;

public class SpawnerLocationDataConfig extends JsonFileManager{

    public static HashMap<String, SpawnerLocationData> spawnerLocationData = new HashMap<>();

    public SpawnerLocationDataConfig() {
        super("data", "spawnerdata");

        List<DefaultConfig.Type> defaults = Arrays.asList(DefaultConfig.Type.SPAWNER_LOCATION_DATA);

        this.setupDefaults(defaults);

        this.populateSpawnersMap();
    }

    public void saveAllSpawnerSettingsToFile() {
        JsonObject root = new JsonObject();

        for (SpawnerLocationData sld : spawnerLocationData.values()) {
            JsonObject spawnerObj = new JsonObject();

            spawnerObj.addProperty("linked_spawner", sld.getLinkedCustomSpawner());

            spawnerObj.addProperty("spawner_location", LocationUtils.serializeLocation(sld.getSpawnerLocation()));

            JsonArray spawnLocationsArray = new JsonArray();
            for (Location loc : sld.getSpawnLocations()) {
                spawnLocationsArray.add(LocationUtils.serializeLocation(loc));
            }
            spawnerObj.add("spawn_locations", spawnLocationsArray);

            root.add(LocationUtils.serializeLocation(sld.getSpawnerLocation()), spawnerObj);
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateSpawnersMap() {
        spawnerLocationData.clear();

        try (FileReader reader = new FileReader(this.file)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            for (String name : root.keySet()) {
                JsonObject spawnerObj = root.getAsJsonObject(name);

                String linkedSpawner = this.getStringFromJson(spawnerObj, "linked_spawner");

                Location spawnerLoc = LocationUtils.deSerializeLocation(this.getStringFromJson(spawnerObj, "spawner_location"));

                JsonArray spawnLocationsJson = spawnerObj.get("spawn_locations").getAsJsonArray();
                ArrayList<Location> spawnLocations = new ArrayList<>();

                for (int y = 0; y < spawnLocationsJson.size(); y++) {
                    String locationString = spawnLocationsJson.get(y).getAsString();
                    Location listLoc = LocationUtils.deSerializeLocation(locationString);
                    spawnLocations.add(listLoc);
                }

                SpawnerLocationData sld = new SpawnerLocationData(linkedSpawner, spawnerLoc, spawnLocations);

                spawnerLocationData.put(LocationUtils.serializeLocation(spawnerLoc), sld);
            }
        } catch (Exception e) {
        }
    }
} 
