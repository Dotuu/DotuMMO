package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Main;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Tables.ExpSource;
import me.dotu.MMO.Tables.ExpTable;

public class ExpTableConfig extends JsonFileManager {
    public static HashMap<String, ExpTable<?>> expTables = new HashMap<>();
    private final File[] files = {
            this.makeFile("fishing"),
            this.makeFile("mining"),
            this.makeFile("woodcutting"),
            this.makeFile("axe"),
            this.makeFile("sword"),
            this.makeFile("farming"),
            this.makeFile("cooking"),
            this.makeFile("archery"),
            this.makeFile("mace"),
            this.makeFile("potion_crafting"),
            this.makeFile("gem_crafting"),
            this.makeFile("armor_crafting"),
            this.makeFile("weapon_crafting"),
            this.makeFile("block"),
            this.makeFile("unarmed"),
            this.makeFile("taming"),
    };

    public ExpTableConfig() {
        super("tables/exp", "");

        HashMap<File, DefaultConfig> files = new HashMap<>();

        for (File file : this.files) {
            String fileName = file.getName().toUpperCase();
            int dot = fileName.indexOf(".");
            String enumName = fileName.substring(0, dot);
            DefaultConfig type = DefaultConfig.valueOf(enumName);
            files.put(file, type);
        }

        this.setupDefaults(files);
    }

    public void reloadConfig() {
        expTables.clear();
        this.populateMap();
    }

    private File makeFile(String fileName) {
        return new File(new File(Main.plugin.getDataFolder(), this.path), fileName + this.extension);
    }

    @Override
    public void populateMap() {
        expTables.clear();
        File[] files = this.file.listFiles();
        boolean isTableTypeMaterial = true;
        if (files == null) return;

        for (File tableFile : files) {
            if (!tableFile.isFile()) continue;
            try (FileReader reader = new FileReader(tableFile)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                String name = root.get("name").getAsString();
                JsonArray sources = root.getAsJsonArray("sources");
                ArrayList<ExpSource<?>> items = new ArrayList<>();

                for (JsonElement element : sources) {
                    JsonObject obj = element.getAsJsonObject();
                    int min = obj.get("min_exp").getAsInt();
                    int max = obj.get("max_exp").getAsInt();

                    if (obj.has("material")) {
                        isTableTypeMaterial = true;
                        Material material = Material.matchMaterial(obj.get("material").getAsString());
                        if (material != null){
                            items.add(new ExpSource<>(min, max, material));
                        }
                    } 
                    else if (obj.has("entitytype")) {
                        try {
                            isTableTypeMaterial = false;
                            EntityType entityType = EntityType.valueOf(obj.get("entitytype").getAsString().toUpperCase());
                            items.add(new ExpSource<>(min, max, entityType));
                        } catch (IllegalArgumentException e) {

                        }
                    }
                }
                
                ExpTable<?> table = null;

                if (isTableTypeMaterial){
                    table = new ExpTable<>(name, items, Material.ACACIA_BOAT);
                }
                else{
                    table = new ExpTable<>(name, items, EntityType.ACACIA_BOAT);
                }

                expTables.put(name, table);

            } catch (Exception e) {
                Main.plugin.getLogger().warning("Failed loading exp table file " + tableFile.getName() + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void saveAllToFile() {
        for (Map.Entry<String, ExpTable<?>> entry : expTables.entrySet()) {
            String tableName = entry.getKey();
            ExpTable<?> expTable = entry.getValue();

            JsonObject root = new JsonObject();
            root.addProperty("name", tableName);

            JsonArray sourcesArr = new JsonArray();
            for (ExpSource<?> item : expTable.getExpItems()) {
                if (item == null){
                    continue;
                }

                Object src = item.getTableSource();

                JsonObject obj = new JsonObject();
                if (src instanceof Material) {
                    Material material = (Material) src;
                    obj.addProperty("material", material.name());
                }
                else if (src instanceof EntityType) {
                    EntityType entityType = (EntityType) src;
                    obj.addProperty("entitytype", entityType.name());
                }
                else {
                    continue;
                }

                obj.addProperty("min_exp", item.getMinExp());
                obj.addProperty("max_exp", item.getMaxExp());
                sourcesArr.add(obj);
            }
            root.add("sources", sourcesArr);

            File outFile = null;
            for (File f : this.files) {
                if (f.getName().equalsIgnoreCase(tableName + this.extension)) {
                    outFile = f;
                    break;
                }
            }
            if (outFile == null) {
                outFile = new File(new File(Main.plugin.getDataFolder(), this.path), tableName + this.extension);
            }
            outFile.getParentFile().mkdirs();

            try (FileWriter writer = new FileWriter(outFile)) {
                new GsonBuilder().setPrettyPrinting().create().toJson(root, writer);
            } catch (Exception e) {
                Main.plugin.getLogger().warning("Failed saving exp table " + tableName + ": " + e.getMessage());
            }
        }
    }
}