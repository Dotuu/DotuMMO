package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Augments.Augment;
import me.dotu.MMO.Enums.AugmentType;
import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Enums.GemType;
import me.dotu.MMO.Enums.ItemTier;
import me.dotu.MMO.Gems.Gem;
import me.dotu.MMO.Main;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Tables.LootTable;
import me.dotu.MMO.Tables.LootTableItem;

public class LootTableConfig extends JsonFileManager {
    public static HashMap<String, LootTable> lootTables = new HashMap<>();
    public static HashMap<Long, LootTableItem> lootTableItems = new HashMap<>();

    public LootTableConfig() {
        super("tables", "");

        File dir = new File(Main.plugin.getDataFolder(), this.path);
        this.file = dir;

        HashMap<File, DefaultConfig> defaults = new HashMap<>();
        defaults.put(new File(this.file, "global" + this.extension), DefaultConfig.GLOBAL_LOOT_TABLE);
        this.setupDefaults(defaults);
    }

    @Override
    public void populateMap() {
        if (!this.file.exists() || !this.file.isDirectory()) {
            return;
        }
        
        File[] files = this.file.listFiles((dir, name) -> name.endsWith(this.extension));

        if (files != null) {
            for (File tableFile : files) {
                try (FileReader reader = new FileReader(tableFile)) {
                    JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

                    String name = root.get("name").getAsString();
                    LootTable lootTable = new LootTable(name);

                    if (name.equalsIgnoreCase("global")){
                        ArrayList<LootTableItem> items = this.getLootItems(root.getAsJsonArray("items"));

                        if (items != null){
                            lootTable.setItems(items);
                        }
                    }
                    else{
                        JsonArray itemIdsJson = root.get("items").getAsJsonArray();
                        ArrayList<Long> itemIds = new ArrayList<>();

                        for (int x = 0; x < itemIdsJson.size(); x++){   
                            itemIds.add(itemIdsJson.get(x).getAsLong());
                        }

                        lootTable.setItemIds(itemIds);
                    }
                    
                    lootTables.put(name, lootTable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.populateLootTableItems();
        }  
    }

    @Override
    public void saveAllToFile(){
        if (!file.exists()){
            this.file.mkdirs();
        }

        for (Map.Entry<String, LootTable> entry : lootTables.entrySet()){
            String tableName = entry.getKey();
            LootTable table = entry.getValue();

            if (tableName.equalsIgnoreCase("global")){
                this.saveGlobalTableToFile();
                continue;
            }

            File tableFile = new File(this.file, tableName + this.extension);

            try(FileWriter writer = new FileWriter(tableFile)){
                JsonObject root = new JsonObject();
                root.addProperty("name", tableName);

                JsonArray itemsArray = new JsonArray();

                for (LootTableItem item : table.getItems()){
                    itemsArray.add(item.getId());
                }

                root.add("items", itemsArray);

                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(root, writer);
            }catch(Exception ex){

            }
        }
    }

    public void saveGlobalTableToFile(){
        LootTable globalTable = lootTables.get("global");

        if (globalTable == null){
            return;
        }

        String tableName = globalTable.getTableName();

        File tableFile = new File(this.file, tableName + this.extension);

        try(FileWriter writer = new FileWriter(tableFile)){
            JsonObject root = new JsonObject();
            root.addProperty("name", tableName);

            JsonArray itemsArray = new JsonArray();

            for (LootTableItem item : globalTable.getItems()){
                JsonObject rootItemObj = new JsonObject();

                rootItemObj.addProperty("id", item.getId());
                rootItemObj.addProperty("material", item.getMaterial().name());
                rootItemObj.addProperty("display_name", item.getDisplayName());
                rootItemObj.addProperty("tier", item.getTier().name());
                rootItemObj.addProperty("weight", item.getWeight());

                JsonArray loresArray = new JsonArray();
                if (item.getLores() != null){
                    for (String lore : item.getLores()){
                        loresArray.add(lore);
                    }
                }
                rootItemObj.add("lores", loresArray);

                JsonArray gemsArray = new JsonArray();
                if (item.getGems() != null){
                    for (Gem gem : item.getGems()){
                        JsonObject gemObject = new JsonObject();
                        gemObject.addProperty("tier", gem.getTier().name());
                        gemObject.addProperty("min_level_to_use", gem.getMinLevelToUse());
                        gemObject.addProperty("gem", gem.getGem().name());
                        gemObject.addProperty("description", gem.getDescription());
                        gemsArray.add(gemObject);
                    }
                }
                rootItemObj.add("gems", gemsArray);

                JsonArray augmentsArray = new JsonArray();
                if (item.getAugments() != null){
                    for (Augment augment : item.getAugments()){
                        JsonObject augmentObject = new JsonObject();
                        augmentObject.addProperty("tier", augment.getTier().name());
                        augmentObject.addProperty("min_level_to_use",  augment.getMinLevelToUse());
                        augmentObject.addProperty("augment", augment.getAugment().name());
                        augmentObject.addProperty("description", augment.getDescription());
                        augmentsArray.add(augmentObject);
                    }
                }
                rootItemObj.add("augments", augmentsArray);

                itemsArray.add(rootItemObj);
            }

            root.add("items", itemsArray);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);

        }catch(Exception e){
        }
    }

    private ArrayList<LootTableItem> getLootItems(JsonArray items) {
        ArrayList<LootTableItem> returnArray = new ArrayList<>();

        for (int x = 0; x < items.size(); x++) {
            JsonObject itemObj = items.get(x).getAsJsonObject();

            Material material = Material.valueOf(itemObj.get("material").getAsString());
            String displayName = itemObj.get("display_name").getAsString();
            
            LootTableItem lootItem = new LootTableItem(material);

            lootItem.setDisplayName(displayName);
            
            String tierString = itemObj.get("tier").getAsString();
            ItemTier tier = ItemTier.valueOf(tierString);
            lootItem.setTier(tier);

            Long id = itemObj.get("id").getAsLong();
            lootItem.setId(id);
            
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

    private void populateLootTableItems(){
        LootTable globalTable = lootTables.get("global");

        for (LootTableItem tableItem : globalTable.getItems()){
            if (lootTableItems.containsKey(tableItem.getId())){
                continue;
            }
            lootTableItems.put(tableItem.getId(), tableItem);
        }
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

            String itemTier = jsonObj.get("tier").getAsString();
            ItemTier tier = ItemTier.valueOf(itemTier);

            int minLevelToUse = jsonObj.get("min_level_to_use").getAsInt();

            String augmentStr = jsonObj.get("augment").getAsString();
            AugmentType augment = AugmentType.valueOf(augmentStr);

            String description = jsonObj.get("description").getAsString();

            Augment newAugment = new Augment(tier, minLevelToUse, augment, description);

            augments.add(newAugment);
        }
        return augments;
    }

    private ArrayList<Gem> getGems(JsonArray jsonArray) {
        ArrayList<Gem> gems = new ArrayList<>();
        for (int x = 0; x < jsonArray.size(); x++) {
            JsonObject jsonObj = jsonArray.get(x).getAsJsonObject();

            String itemTier = jsonObj.get("tier").getAsString();
            ItemTier tier = ItemTier.valueOf(itemTier);

            int minLevelToUse = jsonObj.get("min_level_to_use").getAsInt();

            String gemStr = jsonObj.get("gem").getAsString();
            GemType gem = GemType.valueOf(gemStr);

            String description;
            description = jsonObj.get("description").getAsString();

            Gem newGem = new Gem(tier, minLevelToUse, gem, description);

            gems.add(newGem);
        }
        return gems;
    }
}
