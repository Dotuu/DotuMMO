package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Augments.Augment;
import me.dotu.MMO.Enums.AugmentCategory;
import me.dotu.MMO.Enums.AugmentType;
import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Enums.GemCategory;
import me.dotu.MMO.Enums.GemType;
import me.dotu.MMO.Enums.ItemTier;
import me.dotu.MMO.Gems.Gem;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Tables.LootItem;
import me.dotu.MMO.Tables.LootTable;

public class LootTableConfig extends JsonFileManager {
    public static HashMap<String, LootTable> lootTables = new HashMap<>();

    public LootTableConfig() {
        super("tables", "");

        this.setupDefaults(Arrays.asList(DefaultConfig.SETTINGS));
    }

    @Override
    public void populateMap() {
        File[] files = this.file.listFiles((dir, name) -> name.endsWith(this.extension));
        file.listFiles();

        if (files != null) {
            for (File tableFile : files) {
                try (FileReader reader = new FileReader(tableFile)) {
                    JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                    ArrayList<LootItem> items = this.getLootItems(root.getAsJsonArray("items"));
                    String name = root.get("name").getAsString();

                    LootTable lootTable = new LootTable(name, items);
                    lootTables.put(name, lootTable);
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void saveAllToFile(){
        
    }

    private ArrayList<LootItem> getLootItems(JsonArray items) {
        ArrayList<LootItem> returnArray = new ArrayList<>();

        for (int x = 0; x < items.size(); x++) {
            JsonObject itemObj = items.get(x).getAsJsonObject();

            int weight = itemObj.get("weight").getAsInt();
            Material material = Material.valueOf(itemObj.get("material").getAsString());
            String displayName = itemObj.get("display_name").getAsString();

            JsonArray tiersJson = itemObj.get("tiers").getAsJsonArray();
            ItemTier[] tiers = this.getTiers(tiersJson);

            JsonArray loresJson = itemObj.get("lores").getAsJsonArray();
            List<String> lores = this.getLores(loresJson);

            JsonArray augmentsJson = itemObj.get("augments").getAsJsonArray();
            List<Augment> augments = this.getAugments(augmentsJson);

            JsonArray gemsJson = itemObj.get("gems").getAsJsonArray();
            List<Gem> gems = this.getGems(gemsJson);

            LootItem lootItem = new LootItem(augments, gems, material, displayName, lores, weight, tiers);
            returnArray.add(lootItem);
        }

        return returnArray;
    }

    private ArrayList<String> getLores(JsonArray jsonArray) {
        ArrayList<String> lores = new ArrayList<>();

        for (int x = 0; x < jsonArray.size(); x++) {
            String lore = jsonArray.get(x).getAsString();
            lores.add(lore);
        }
        return lores;
    }

    private ArrayList<Augment> getAugments(JsonArray jsonArray) {
        ArrayList<Augment> augments = new ArrayList<>();
        for (int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObj = jsonArray.get(x).getAsJsonObject();

            JsonArray tiersJson = jsonObj.get("tiers").getAsJsonArray();
            ItemTier[] tiers = this.getTiers(tiersJson);

            String categoryStr = jsonObj.get("category").getAsString();
            AugmentCategory category = AugmentCategory.valueOf(categoryStr);

            int minLevelToUse = jsonObj.get("minLevelToUse").getAsInt();

            String augmentStr = jsonObj.get("augment").getAsString();
            AugmentType augment = AugmentType.valueOf(augmentStr);

            String description = jsonObj.get("description").getAsString();

            Augment newAugment = new Augment(tiers, minLevelToUse, augment, description, category);

            augments.add(newAugment);
        }
        return augments;
    }

    private ArrayList<Gem> getGems(JsonArray jsonArray) {
        ArrayList<Gem> gems = new ArrayList<>();
        for (int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObj = jsonArray.get(x).getAsJsonObject();

            JsonArray tiersJson = jsonObj.get("tiers").getAsJsonArray();
            ItemTier[] tiers = this.getTiers(tiersJson);

            String categoryStr = jsonObj.get("category").getAsString();
            GemCategory category = GemCategory.valueOf(categoryStr);

            int minLevelToUse = jsonObj.get("minLevelToUse").getAsInt();

            String gemStr = jsonObj.get("gem").getAsString();
            GemType gem = GemType.valueOf(gemStr);

            String description;
            description = jsonObj.get("description").getAsString();

            Gem newGem = new Gem(tiers, minLevelToUse, gem, description, category);

            gems.add(newGem);
        }
        return gems;
    }

    private ItemTier[] getTiers(JsonArray jsonArray) {
        ItemTier tiers[] = new ItemTier[jsonArray.size()];
        for (int x = 0; x < jsonArray.size(); x++) {
            String tier = jsonArray.get(x).getAsString();
            tiers[x] = ItemTier.valueOf(tier);
        }
        return tiers;
    }
}
