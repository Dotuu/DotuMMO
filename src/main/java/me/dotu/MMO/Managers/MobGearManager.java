package me.dotu.MMO.Managers;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.ItemData.MobGear;
import me.dotu.MMO.Main;

public class MobGearManager {
    public static Map<String, List<MobGear>> lootTables;
    
    public MobGearManager(){
        initLootTables();
    }

    private void initLootTables(){
        try (FileReader reader = new FileReader(new File(Main.plugin.getDataFolder(), "configs/mobtable.json"))){
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonObject lootTablesJson = root.getAsJsonObject("Tables");

            for (Map.Entry<String, JsonElement> tableEntry : lootTablesJson.entrySet()){
                String difficultyName = tableEntry.getKey();
                JsonObject itemJson = tableEntry.getValue().getAsJsonObject();
                
                List<MobGear> gearList = new ArrayList<>();

                for (Map.Entry<String, JsonElement> itemEntry : itemJson.entrySet()){
                    Material material = this.getMaterial(itemEntry.getKey());
                    int weight = this.getWeight(itemEntry.getValue().getAsString());

                    gearList.add(new MobGear(new ItemStack(material), weight));
                }

                lootTables.put(difficultyName, gearList);
            }
        } catch (Exception e) {
        }
    }

    private int getWeight(String weight){
        try {
            return Integer.parseInt(weight);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    private Material getMaterial(String materialName){
        try {
            return Material.valueOf(materialName);
        } catch (Exception e) {
            return Material.AIR;
        }
    }
}
