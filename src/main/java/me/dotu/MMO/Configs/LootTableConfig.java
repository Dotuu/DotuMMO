package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Material;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.LootTables.LootItem;
import me.dotu.MMO.LootTables.LootTable;
import me.dotu.MMO.Managers.JsonFileManager;

public class LootTableConfig extends JsonFileManager {
    private String filename;
    public static ArrayList<LootTable> lootTables = new ArrayList<>();
    public static ArrayList<LootItem> lootItems = new ArrayList<>();

    public LootTableConfig() {
        super("tables");
    }

    public void loadTablesToMap() {
        File[] files = this.file.listFiles((dir, name) -> name.endsWith(".json"));
        file.listFiles();

        if (files != null) {
            for (File tableFile : files) {
                try (FileReader reader = new FileReader(tableFile)) {
                    JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                    ArrayList<LootItem> items = this.getLootItems(root.getAsJsonArray("items"));
                    String name = root.get("name").getAsString();
                    UUID uuid = UUID.fromString(tableFile.getName().replace(".json", ""));

                    LootTable lootTable = new LootTable(name, uuid);
                    lootTable.setItems(items);
                    lootTables.add(lootTable);
                } catch (Exception e) {

                }
            }
        }
    }

    // add the gem list and augment list to the LootItem when they are implemented
    private ArrayList<LootItem> getLootItems(JsonArray items) {
        ArrayList<LootItem> returnArray = new ArrayList<>();

        for (int x = 0; x < items.size(); x++) {
            JsonObject itemObj = items.get(x).getAsJsonObject();

            String identifier = itemObj.get("identifier").getAsString();
            int weight = itemObj.get("weight").getAsInt();
            Material material = Material.valueOf(itemObj.get("material").getAsString());
            String displayName = itemObj.get("display_name").getAsString();

            JsonArray loresJson = itemObj.get("lores").getAsJsonArray();

            ArrayList<String> lores = new ArrayList<>();
            for (int y = 0; y < loresJson.size(); y++) {
                String lore = loresJson.get(y).getAsString();
                lores.add(lore);
            }

            LootItem lootItem = new LootItem(null, null, material, displayName, lores, weight);
            returnArray.add(lootItem);
        }

        return returnArray;
    }
}
