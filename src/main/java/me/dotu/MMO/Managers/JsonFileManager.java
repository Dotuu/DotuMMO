package me.dotu.MMO.Managers;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Main;

public abstract class JsonFileManager {

    protected final String path;
    protected File file;
    protected String fileName;
    protected String extension = ".json";

    protected JsonFileManager(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    protected void reloadFile(File file) {
        // reload logic here later
    }

    protected void setupDefaults(List<DefaultConfig> fileDefaults) {
        if (this.file == null) {
            File dir = new File(Main.plugin.getDataFolder(), this.path);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            this.file = new File(dir, this.fileName + this.extension);
        }

        if (this.file.exists()) {
            this.populateMap();
            return;
        }

        JsonObject root = new JsonObject();

        for (DefaultConfig setting : fileDefaults) {
            setting.populate(root);
        }

        try (FileWriter writer = new FileWriter(this.file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(root, writer);
        } catch (Exception e) {
        }

        this.populateMap();
    }

    protected void setupDefaults(HashMap<File, DefaultConfig> files) {
        File dir = new File(Main.plugin.getDataFolder(), this.path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        for (Map.Entry<File, DefaultConfig> mapFile : files.entrySet()) {
            File target = mapFile.getKey();
            DefaultConfig type = mapFile.getValue();

            if (target.exists()){
                continue;
            }

            JsonObject root = new JsonObject();
            type.populate(root);

            try (FileWriter writer = new FileWriter(target)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(root, writer);
            } catch (Exception e) {
                
            }
        }

        this.file = dir;
        this.populateMap();
    }

    protected abstract void populateMap();

    protected abstract void saveAllToFile();

    protected boolean getBooleanFromJson(JsonObject obj, String member) {
        return obj.get(member).getAsBoolean();
    }

    protected String getStringFromJson(JsonObject obj, String member) {
        return obj.get(member).getAsString();
    }

    protected double getDoubleFromJson(JsonObject obj, String member) {
        return obj.get(member).getAsDouble();
    }

    protected int getIntFromJson(JsonObject obj, String member) {
        return obj.get(member).getAsInt();
    }
}
