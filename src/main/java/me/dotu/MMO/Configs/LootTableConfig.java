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
import me.dotu.MMO.Enums.AugmentType;
import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Enums.GemType;
import me.dotu.MMO.Enums.ItemTier;
import me.dotu.MMO.Gems.Gem;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Tables.LootTable;
import me.dotu.MMO.Tables.LootTableItem;

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

                    ArrayList<LootTableItem> items = this.getLootItems(root.getAsJsonArray("items"));
                    String name = root.get("name").getAsString();

                    LootTable lootTable = new LootTable(name);
                    if (items != null){
                        lootTable.setItems(items);
                    }
                    lootTables.put(name, lootTable);
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public void saveAllToFile(){
        
    }

    private ArrayList<LootTableItem> getLootItems(JsonArray items) {
        ArrayList<LootTableItem> returnArray = new ArrayList<>();

        for (int x = 0; x < items.size(); x++) {
            JsonObject itemObj = items.get(x).getAsJsonObject();

            Material material = Material.valueOf(itemObj.get("material").getAsString());
            String displayName = itemObj.get("display_name").getAsString();
            
            String tierString = itemObj.get("tier").getAsString();
            ItemTier tier = ItemTier.valueOf(tierString);

            
            LootTableItem lootItem = new LootTableItem(material, displayName);

            lootItem.setTier(tier);
            
            int weight = itemObj.get("weight").getAsInt();
            lootItem.setWeight(weight);

            JsonArray loresJson = itemObj.get("lores").getAsJsonArray();
            List<String> lores = this.getLores(loresJson);
            if (lores != null){
                lootItem.setLores(lores);
            }

            JsonArray augmentsJson = itemObj.get("augments").getAsJsonArray();
            List<Augment> augments = this.getAugments(augmentsJson);
            if (augments != null){
                lootItem.setAugments(augments);
            }

            JsonArray gemsJson = itemObj.get("gems").getAsJsonArray();
            List<Gem> gems = this.getGems(gemsJson);
            if (gems != null){
                lootItem.setGems(gems);
            }

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

            int minLevelToUse = jsonObj.get("minLevelToUse").getAsInt();

            String augmentStr = jsonObj.get("augment").getAsString();
            AugmentType augment = AugmentType.valueOf(augmentStr);

            String description = jsonObj.get("description").getAsString();

            Augment newAugment = new Augment(tiers, minLevelToUse, augment, description);

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

            int minLevelToUse = jsonObj.get("minLevelToUse").getAsInt();

            String gemStr = jsonObj.get("gem").getAsString();
            GemType gem = GemType.valueOf(gemStr);

            String description;
            description = jsonObj.get("description").getAsString();

            Gem newGem = new Gem(tiers, minLevelToUse, gem, description);

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
