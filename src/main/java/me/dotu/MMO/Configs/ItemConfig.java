package me.dotu.MMO.Configs;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Tables.LootTableItem;

public class ItemConfig extends JsonFileManager{
    public static ArrayList<LootTableItem> lootTablesItems = new ArrayList<>();

    public ItemConfig() {
        super("data", "itemdata");

        this.setupDefaults(Arrays.asList(DefaultConfig.SETTINGS));
    }

    @Override
    public void populateMap() {
        try(FileReader reader = new FileReader(this.file)){
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            
        }catch(Exception e){

        }
    }

    @Override
    public void saveAllToFile() {

    }
    
    
}
