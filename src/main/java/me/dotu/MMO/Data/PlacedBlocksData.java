package me.dotu.MMO.Data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class PlacedBlocksData {
    private final File configFile;
    private final JavaPlugin plugin;
    private final String filename = "data.json";
    public static HashMap<Location, String> placedBlocks;

    public PlacedBlocksData(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(this.plugin.getDataFolder(), this.filename);

        if (!this.configFile.exists()) {
            this.setupDefaults();
        }

    }

    public File getConfig() {
        return this.configFile;
    }

    private void setupDefaults() {
        System.out.println("DotuMMO - Item config: Running first time setup");
        File parentDir = this.configFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        JsonObject defaultConfig = new JsonObject();

        JsonObject placedBlocks = new JsonObject();

        defaultConfig.add("placed blocks", placedBlocks);

        try (FileWriter writer = new FileWriter(this.configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaultConfig, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populatePlacedBlocksMap(){
        JsonObject read = null;
        try(FileReader reader = new FileReader(this.configFile)){
            read = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
        }

        if (read != null){
            HashMap<Location, String> map = new HashMap<>();

            JsonObject placedBlocks = read.getAsJsonObject("placed blocks");
            for (String key : placedBlocks.keySet()){
                String[] locationParts = key.split(",");
                if (locationParts.length == 4){
                    try{
                        World world = Bukkit.getServer().getWorld(locationParts[0]);
                        double x = Double.parseDouble(locationParts[1]);
                        double y = Double.parseDouble(locationParts[2]);
                        double z = Double.parseDouble(locationParts[3]);

                        Location loc = new Location(world, x, y, z);
                        String value = placedBlocks.get(key).getAsString();
                        map.put(loc, value);
                    }catch(Exception ex){

                    }
                }
            }
            this.placedBlocks = map;
        }
    }

    public void savePlacedBlocksMapToConfig(){
        JsonObject root = new JsonObject();
        JsonObject placedBlocksJson = new JsonObject();

        for (Location loc : placedBlocks.keySet()){
            String key = loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
            String value = placedBlocks.get(loc);
            placedBlocksJson.addProperty(key, value);
        }

        root.add("placed blocks", placedBlocksJson);
        
        try (FileWriter writer = new FileWriter(this.configFile)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
