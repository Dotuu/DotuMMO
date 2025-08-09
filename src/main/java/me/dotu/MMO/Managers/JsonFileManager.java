package me.dotu.MMO.Managers;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import me.dotu.MMO.Enums.ConfigEnum;

public abstract class JsonFileManager {

    protected final JavaPlugin plugin;
    protected final String path;
    protected File file;

    protected JsonFileManager(JavaPlugin plugin, String path){
        this.plugin = plugin;
        this.path = path;
    }
    
    protected JsonFileManager(JavaPlugin plugin, File file, String path){
        this.plugin = plugin;
        this.file = file;
        this.path = path;
    }

    protected void createFileIfNotExists(String fileName){
        File dir = new File(this.plugin.getDataFolder(), this.path);
        
        if (!dir.exists()){
            dir.mkdirs();
        }
        this.file = new File(dir, fileName);

        try{
            if (!this.file.exists()){
                this.file.createNewFile();
            }
        }
        catch(Exception e){

        }
    }

    protected void reloadFile(File file){
        // reload logic here later
    }

    protected void setupDefaults(List<ConfigEnum.Type> fileDefaults){
        if (!this.file.exists()){
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

    protected void loadFromFile(){
        
    }

    protected void saveToFile(){

    }
}
