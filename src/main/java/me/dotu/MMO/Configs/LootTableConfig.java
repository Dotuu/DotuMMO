package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Augments.Augment;
import me.dotu.MMO.Enums.AugmentEnum;
import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Enums.GemEnum;
import me.dotu.MMO.Enums.ItemEnum;
import me.dotu.MMO.Gems.Gem;
import me.dotu.MMO.LootTables.LootItem;
import me.dotu.MMO.LootTables.LootTable;
import me.dotu.MMO.Managers.JsonFileManager;

public class LootTableConfig extends JsonFileManager {
    public static HashMap<String, LootTable> lootTables = new HashMap<>();
    public static ArrayList<LootItem> lootItems = new ArrayList<>();

    public LootTableConfig() {
        super("tables", "");

        this.setupDefaults(Arrays.asList(
            ConfigEnum.Type.SETTINGS
        ));
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
                    lootTables.put(name, lootTable);
                } catch (Exception e) {

                }
            }
        }
    }

    private ArrayList<LootItem> getLootItems(JsonArray items) {
        ArrayList<LootItem> returnArray = new ArrayList<>();

        for (int x = 0; x < items.size(); x++) {
            JsonObject itemObj = items.get(x).getAsJsonObject();

            int weight = itemObj.get("weight").getAsInt();
            Material material = Material.valueOf(itemObj.get("material").getAsString());
            String displayName = itemObj.get("display_name").getAsString();

            JsonArray tiersJson = itemObj.get("tiers").getAsJsonArray();
            ItemEnum.Tier[] tiers = this.getTiers(tiersJson);

            JsonArray loresJson = itemObj.get("lores").getAsJsonArray();
            ArrayList<String> lores = this.getLores(loresJson);

            JsonArray augmentsJson = itemObj.get("augments").getAsJsonArray();
            ArrayList<Augment> augments = this.getAugments(augmentsJson);

            JsonArray gemsJson = itemObj.get("gems").getAsJsonArray();
            ArrayList<Gem> gems = this.getGems(gemsJson);

            LootItem lootItem = new LootItem(augments, gems, material, displayName, lores, weight, tiers);
            returnArray.add(lootItem);
        }

        return returnArray;
    }

    private ArrayList<String> getLores(JsonArray jsonArray){
        ArrayList<String> lores = new ArrayList<>();

        for (int x = 0; x < jsonArray.size(); x++) {
            String lore = jsonArray.get(x).getAsString();
            lores.add(lore);
        }
        return lores;
    }

    private ArrayList<Augment> getAugments(JsonArray jsonArray){
        ArrayList<Augment> augments = new ArrayList<>();
        for (int x = 0; x < jsonArray.size(); x++){
            JsonObject jsonObj = jsonArray.get(x).getAsJsonObject();

            JsonArray tiersJson = jsonObj.get("tiers").getAsJsonArray();
            ItemEnum.Tier[] tiers = this.getTiers(tiersJson);

            String categoryStr = jsonObj.get("category").getAsString();
            AugmentEnum.Category category = AugmentEnum.Category.valueOf(categoryStr);

            int minLevelToUse = jsonObj.get("minLevelToUse").getAsInt();

            String augmentStr = jsonObj.get("augment").getAsString();
            AugmentEnum.Augment augment = AugmentEnum.Augment.valueOf(augmentStr);

            String description = jsonObj.get("description").getAsString();

            Augment newAugment = new Augment(tiers, minLevelToUse, augment, description, category);

            augments.add(newAugment);
        }
        return augments; 
    }

    private ArrayList<Gem> getGems(JsonArray jsonArray){
        ArrayList<Gem> gems = new ArrayList<>();
        for (int x = 0; x < jsonArray.size(); x++){
            JsonObject jsonObj = jsonArray.get(x).getAsJsonObject();

            JsonArray tiersJson = jsonObj.get("tiers").getAsJsonArray();
            ItemEnum.Tier[] tiers = this.getTiers(tiersJson);

            String categoryStr = jsonObj.get("category").getAsString();
            GemEnum.Category category = GemEnum.Category.valueOf(categoryStr);

            int minLevelToUse = jsonObj.get("minLevelToUse").getAsInt();

            String gemStr = jsonObj.get("gem").getAsString();
            GemEnum.Gem gem = GemEnum.Gem.valueOf(gemStr);

            String description = jsonObj.get("description").getAsString();

            Gem newGem = new Gem(tiers, minLevelToUse, gem, description, category);

            gems.add(newGem);
        }
        return gems; 
    }

    private ItemEnum.Tier[] getTiers(JsonArray jsonArray){
        ItemEnum.Tier tiers[] = new ItemEnum.Tier[jsonArray.size()];
        for (int x = 0; x < jsonArray.size(); x++){
            String tier = jsonArray.get(x).getAsString();
            tiers[x] = ItemEnum.Tier.valueOf(tier);
        }
        return tiers;
    }
}
