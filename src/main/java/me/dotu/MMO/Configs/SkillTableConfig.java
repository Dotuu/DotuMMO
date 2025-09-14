package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

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
import me.dotu.MMO.Tables.SkillSource;
import me.dotu.MMO.Tables.SkillTable;

public class SkillTableConfig extends JsonFileManager {
    public static HashMap<String, SkillTable<?>> itemTables = new HashMap<>();
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

    public SkillTableConfig() {
        super("data/skilldata", "");

        HashMap<File, DefaultConfig> filesMap = new HashMap<>();

        for (File f : this.files) {
            String fName = f.getName().toUpperCase();
            int dot = fName.indexOf(".");
            String enumName = fName.substring(0, dot);
            DefaultConfig type = DefaultConfig.valueOf(enumName);
            filesMap.put(f, type);
        }

        this.setupDefaults(filesMap);
    }

    public void reloadConfig() {
        itemTables.clear();
        this.populateMap();
    }

    private File makeFile(String fileName) {
        return new File(new File(Main.plugin.getDataFolder(), this.path), fileName + this.extension);
    }

    @Override
    public void populateMap() {
        itemTables.clear();
        File[] dirFiles = this.file.listFiles((d,n) -> n.endsWith(".json"));
        boolean isTableTypeMaterial = true;

        if (dirFiles == null){
            return;
        }

        for (File tableFile : dirFiles) {
            if (!tableFile.isFile()){
                continue;
            }

            try (FileReader reader = new FileReader(tableFile)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                String name = tableFile.getName().replaceFirst("\\.json$", "");
                JsonArray sources = root.getAsJsonArray("sources");
                ArrayList<SkillSource<?>> items = new ArrayList<>();

                for (JsonElement element : sources) {
                    JsonObject obj = element.getAsJsonObject();
                    int min = obj.get("min_exp").getAsInt();
                    int max = obj.get("max_exp").getAsInt();
                    int requiredLevel = obj.get("required_level").getAsInt();

                    if (obj.has("material")) {
                        isTableTypeMaterial = true;
                        Material material = Material.matchMaterial(obj.get("material").getAsString());
                        if (material != null){
                            items.add(new SkillSource<>(min, max, material, requiredLevel));
                        }
                    } 
                    else if (obj.has("entitytype")) {
                        try {
                            isTableTypeMaterial = false;
                            EntityType entityType = EntityType.valueOf(obj.get("entitytype").getAsString().toUpperCase());
                            items.add(new SkillSource<>(min, max, entityType, requiredLevel));
                        } catch (IllegalArgumentException e) {

                        }
                    }

                    if (isTableTypeMaterial){
                        SkillTable<?> table = new SkillTable<>(name, items, Material.AIR);
                        itemTables.put(name.toUpperCase(), table);
                    }

                    else{
                        SkillTable<?> table = new SkillTable<>(name, items, EntityType.ZOMBIE);
                        itemTables.put(name.toUpperCase(), table);
                    }
                }
            } catch (Exception e) {
                Main.plugin.getLogger().log(Level.WARNING, "Failed loading exp table file {0}: {1}", new Object[]{tableFile.getName(), e.getMessage()});
            }
        }
    }

    @Override
    public void saveAllToFile() {
        for (Map.Entry<String, SkillTable<?>> entry : itemTables.entrySet()) {
            String tableName = entry.getKey();
            SkillTable<?> expTable = entry.getValue();

            JsonObject root = new JsonObject();

            JsonArray sourcesArr = new JsonArray();
            for (SkillSource<?> item : expTable.getExpItems()) {
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
                obj.addProperty("required_level", item.getRequiredLevel());
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