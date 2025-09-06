package me.dotu.MMO.Configs;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.SpawnerEntityData;

public class SpawnerConfig extends JsonFileManager {
    public static HashMap<String, CustomSpawner> spawners = new HashMap<>();
    public static HashMap<String, SpawnerEntityData> spawnerDataList = new HashMap<>();

    public SpawnerConfig() {
        super("configs", "spawners");

        List<DefaultConfig> defaults = Arrays.asList(DefaultConfig.SPAWNER_DATA);

        this.setupDefaults(defaults);
    }

    @Override
    public void saveAllToFile() {
        JsonObject root = new JsonObject();

        for (CustomSpawner spawner : spawners.values()) {
            JsonObject spawnerObj = new JsonObject();
            spawnerObj.addProperty("min_level", spawner.getMinLevel());
            spawnerObj.addProperty("max_level", spawner.getMaxLevel());
            spawnerObj.addProperty("spawn_delay", spawner.getSpawnDelay());
            spawnerObj.addProperty("spawn_range", spawner.getSpawnRange());
            spawnerObj.addProperty("spawn_count", spawner.getMaxSpawnCount());
            spawnerObj.addProperty("difficulty", spawner.getDifficulty());
            spawnerObj.addProperty("armored", spawner.isArmored());
            spawnerObj.addProperty("weaponed", spawner.isWeaponed());
            spawnerObj.addProperty("name_visible", spawner.isNameVisible());
            spawnerObj.addProperty("spawn_randomly", spawner.isSpawnRandomly());
            spawnerObj.addProperty("table", spawner.getTable());

            root.add(spawner.getName(), spawnerObj);
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populateMap() {
        spawners.clear();

        try (FileReader reader = new FileReader(this.file)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            for (String name : root.keySet()) {
                JsonObject spawnerObj = root.getAsJsonObject(name);

                int minLevel = this.getIntFromJson(spawnerObj, "min_level");
                int maxLevel = this.getIntFromJson(spawnerObj, "max_level");
                int spawnDelay = this.getIntFromJson(spawnerObj, "spawn_delay");
                int spawnRange = this.getIntFromJson(spawnerObj, "spawn_range");
                int spawnCount = this.getIntFromJson(spawnerObj, "spawn_count");

                double difficulty = this.getDoubleFromJson(spawnerObj, "difficulty");

                boolean armored = this.getBooleanFromJson(spawnerObj, "armored");
                boolean weaponed = this.getBooleanFromJson(spawnerObj, "weaponed");
                boolean nameVisible = this.getBooleanFromJson(spawnerObj, "name_visible");
                boolean spawnRandomly = this.getBooleanFromJson(spawnerObj, "spawn_randomly");

                String table = this.getStringFromJson(spawnerObj, "table");

                CustomSpawner customSpawner = new CustomSpawner(
                        minLevel,
                        maxLevel,
                        difficulty,
                        armored,
                        weaponed,
                        nameVisible,
                        spawnRandomly,
                        name,
                        table,
                        spawnDelay,
                        spawnRange,
                        spawnCount);

                spawners.put(name, customSpawner);
            }
        } catch (Exception e) {
        }
    }
}