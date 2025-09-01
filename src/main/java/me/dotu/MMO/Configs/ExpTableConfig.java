package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Main;
import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Managers.JsonFileManager;
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
        File[] files = this.file.listFiles((dir, name) -> name.endsWith(this.extension));
        file.listFiles();

        if (files != null) {
            for (File tableFile : files) {
                try (FileReader reader = new FileReader(tableFile)) {
                    JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                    String name = root.get("name").getAsString();
                    int minExp = root.get("min_exp").getAsInt();
                    int maxExp = root.get("max_exp").getAsInt();

                    ExpTable<?> expTable = null;

                    if (root.has("entity")) {
                        String entityName = root.get("entity").getAsString();
                        EntityType entity = EntityType.valueOf(entityName.trim().toUpperCase());
                        expTable = new ExpTable<EntityType>(name, minExp, maxExp, entity);
                    }

                    if (root.has("material")) {
                        String materialString = root.get("material").getAsString();
                        Material material = Material.matchMaterial(materialString);
                        expTable = new ExpTable<Material>(name, minExp, maxExp, material);
                    }

                    if (expTable != null) {
                        expTables.put(name, expTable);
                    }
                } catch (Exception e) {
                    System.out.println("Error when loading exp tables.");
                    System.out.println("Please ensure that everything is spelt & formatted correctly!");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveAllToFile() {
        JsonObject tables = new JsonObject();

        for (Map.Entry<String, ExpTable<?>> entry : expTables.entrySet()) {
            String name = entry.getKey();
            ExpTable<?> expTable = entry.getValue();

            tables.addProperty("name", name);
            tables.addProperty("min_exp", expTable.getMinExp());
            tables.addProperty("max_exp", expTable.getMaxExp());

            tables.add(name, tables);
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(tables, writer);
        } catch (Exception e) {

        }
    }
}