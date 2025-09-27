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
    public static HashMap<Long, CustomSpawner> spawners = new HashMap<>();
    public static HashMap<String, SpawnerEntityData> spawnerDataList = new HashMap<>();

    public SpawnerConfig() {
        super("spawners", "spawners");

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
            spawnerObj.addProperty("table", spawner.getDropTable());
            spawnerObj.addProperty("spawner_id", spawner.getId());

            root.add(spawner.getName(), spawnerObj);
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
        }
    }

    @Override
    public void populateMap() {
        spawners.clear();

        try (FileReader reader = new FileReader(this.file)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            for (String name : root.keySet()) {
                JsonObject spawnerObj = root.getAsJsonObject(name);

                int minLevel = spawnerObj.get("min_level").getAsInt();
                int maxLevel = spawnerObj.get("max_level").getAsInt();
                int spawnDelay = spawnerObj.get("spawn_delay").getAsInt();
                int spawnRange = spawnerObj.get("spawn_range").getAsInt();
                int spawnCount = spawnerObj.get("spawn_count").getAsInt();

                double difficulty = spawnerObj.get("difficulty").getAsDouble();

                boolean armored = spawnerObj.get("armored").getAsBoolean();
                boolean weaponed = spawnerObj.get("weaponed").getAsBoolean();
                boolean nameVisible = spawnerObj.get("name_visible").getAsBoolean();
                boolean spawnRandomly = spawnerObj.get("spawn_randomly").getAsBoolean();

                Long id = spawnerObj.get("spawner_id").getAsLong();

                String table = spawnerObj.get("table").getAsString();

                CustomSpawner customSpawner;
                customSpawner = new CustomSpawner(
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
                    spawnCount,
                    id
                );

                spawners.put(customSpawner.getId(), customSpawner);
            }
        } catch (Exception e) {
        }
    }
}