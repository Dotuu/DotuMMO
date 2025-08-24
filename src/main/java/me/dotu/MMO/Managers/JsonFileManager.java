package me.dotu.MMO.Managers;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Main;

public abstract class JsonFileManager {

    protected final String path;
    protected File file;
    protected String fileName;
    private String extension = ".json";
    
    protected JsonFileManager(String path, String fileName){
        this.path = path;
        this.fileName = fileName;
    }

    protected void reloadFile(File file){
        // reload logic here later
    }

    protected void setupDefaults(List<ConfigEnum.Type> fileDefaults){
        if (this.file == null){
            File dir = new File(Main.plugin.getDataFolder(), this.path);

            if (!dir.exists()){
                dir.mkdirs();
            }

            this.file = new File(dir, this.fileName + this.extension);
        }

        if (this.file.exists()){
            return;
        }

        JsonObject root = new JsonObject();

        for (ConfigEnum.Type setting : fileDefaults){
            setting.populate(root);
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
        }
    }
    
    protected void populateSettingsMap(){
        
    }

    protected void saveAllSettingsToFile(){

    }
    
    protected boolean getBooleanFromJson(JsonObject obj, String member){
        return obj.get(member).getAsBoolean();
    }

    protected String getStringFromJson(JsonObject obj, String member){
        return obj.get(member).getAsString();
    }

    protected double getDoubleFromJson(JsonObject obj, String member){
        return obj.get(member).getAsDouble();
    }

    protected int getIntFromJson(JsonObject obj, String member){
        return obj.get(member).getAsInt();
    }
}
