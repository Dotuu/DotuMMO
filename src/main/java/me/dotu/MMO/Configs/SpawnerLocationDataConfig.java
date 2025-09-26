package me.dotu.MMO.Configs;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Spawners.SpawnerLocationData;
import me.dotu.MMO.Utils.LocationUtils;

public class SpawnerLocationDataConfig extends JsonFileManager {

    public static HashMap<String, SpawnerLocationData> spawnerLocationData = new HashMap<>();

    public SpawnerLocationDataConfig() {
        super("spawners", "data");

        List<DefaultConfig> defaults = Arrays.asList();

        this.setupDefaults(defaults);

        this.populateMap();
    }

    @Override
    public void saveAllToFile() {
        JsonObject root = new JsonObject();

        for (SpawnerLocationData sld : spawnerLocationData.values()) {
            JsonObject spawnerObj = new JsonObject();

            spawnerObj.addProperty("linked_spawner", sld.getLinkedCustomSpawner());

            spawnerObj.addProperty("spawner_location", LocationUtils.serializeLocation(sld.getSpawnerLocation()));

            JsonArray spawnLocationsJson = new JsonArray();
            for (Location loc : sld.getSpawnLocations()) {
                spawnLocationsJson.add(LocationUtils.serializeLocation(loc));
            }
            spawnerObj.add("spawn_locations", spawnLocationsJson);

            ArrayList<ItemStack> equipableArmor = sld.getEquipableArmor();
            JsonArray equipableArmorJson = this.serializeItemStackArrayToJson(equipableArmor);
            spawnerObj.add("equipable_armor", equipableArmorJson);

            ArrayList<ItemStack> equipableWeapon = sld.getEquipableArmor();
            JsonArray equipableWeaponJson = this.serializeItemStackArrayToJson(equipableWeapon);
            spawnerObj.add("equipable_weapon", equipableWeaponJson);

            root.add(LocationUtils.serializeLocation(sld.getSpawnerLocation()), spawnerObj);
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
        }
    }

    @Override
    public void populateMap() {
        spawnerLocationData.clear();

        try (FileReader reader = new FileReader(this.file)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            for (String name : root.keySet()) {
                JsonObject spawnerObj = root.getAsJsonObject(name);

                String linkedSpawner = spawnerObj.get("linked_spawner").getAsString();

                Location spawnerLoc = LocationUtils.deSerializeLocation(spawnerObj.get("spawner_location").getAsString());

                JsonArray spawnLocationsJson = spawnerObj.get("spawn_locations").getAsJsonArray();
                ArrayList<Location> spawnLocations = new ArrayList<>();

                for (int y = 0; y < spawnLocationsJson.size(); y++) {
                    String locationString = spawnLocationsJson.get(y).getAsString();
                    Location listLoc = LocationUtils.deSerializeLocation(locationString);
                    spawnLocations.add(listLoc);
                }

                JsonArray equipableArmorJson = spawnerObj.get("equipable_armor").getAsJsonArray();
                ArrayList<ItemStack> equipableArmor = this.deserializeToItemstackArray(equipableArmorJson);

                JsonArray equipableWeaponJson = spawnerObj.get("equipable_weapon").getAsJsonArray();
                ArrayList<ItemStack> equipableWeapon = this.deserializeToItemstackArray(equipableWeaponJson);

                SpawnerLocationData sld = new SpawnerLocationData(linkedSpawner, spawnerLoc, spawnLocations, equipableArmor, equipableWeapon);

                spawnerLocationData.put(LocationUtils.serializeLocation(spawnerLoc), sld);
            }
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<ItemStack> deserializeToItemstackArray(JsonArray jsonArray){
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        Gson gson = new Gson();
        for (int x = 0; x < jsonArray.size(); x++){
            JsonObject itemJson = jsonArray.get(x).getAsJsonObject();

            Map<String, Object> itemMap = gson.fromJson(itemJson, Map.class);
            ItemStack item = ItemStack.deserialize(itemMap);
            returnArray.add(item);
        }
        return returnArray;
    }

    private JsonArray serializeItemStackArrayToJson(ArrayList<ItemStack> itemStacks) {
        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        
        for (ItemStack item : itemStacks) {
            if (item != null) {
                Map<String, Object> itemMap = item.serialize();
                JsonObject itemJson = (JsonObject) gson.toJsonTree(itemMap);
                jsonArray.add(itemJson);
            }
        }
        
        return jsonArray;
    }
}
