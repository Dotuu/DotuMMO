package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.PlayerManager;

public class PlayerConfig implements Listener{
    private File configFile;
    private final JavaPlugin plugin;
    private String filename;
    public static HashMap<UUID, PlayerManager> playerSettings = new HashMap<>();

    public PlayerConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    
    public void saveSettingsToFile(UUID uuid){
        PlayerManager manager = playerSettings.get(uuid);
        if (manager != null){
            File file = this.getPlayerFile(uuid);
            
            try(FileWriter writer = new FileWriter(file)){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(manager.getSettings(), writer);
                playerSettings.remove(uuid);
            }catch(Exception e){
            }
        }
    }

    public void saveAllPlayerSettingsToFile(){
        for (Map.Entry<UUID, PlayerManager> entry : playerSettings.entrySet()){
            UUID uuid = entry.getKey();
            PlayerManager manager = entry.getValue();

            if (manager != null){
            File file = this.getPlayerFile(uuid);
            
            try(FileWriter writer = new FileWriter(file)){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(manager.getSettings(), writer);
                playerSettings.remove(uuid);
            }catch(Exception e){
            }
        }
        }
    }

    public void loadSettingsFromFile(UUID uuid){
        this.filename = uuid.toString() + ".json";
        if (!this.filename.isEmpty()){
            this.configFile = new File(new File(this.plugin.getDataFolder(), "playerdata"), this.filename);
            if (!this.configFile.exists()){
                this.setupDefaults(uuid);
            }
            else{
                playerSettings.put(uuid, new PlayerManager(getPlayerFileData(uuid)));
            }
        }
    }

    private File getPlayerFile(UUID uuid){
        String playerFile = uuid.toString() + ".json";
        return new File(new File(this.plugin.getDataFolder(), "playerdata"), playerFile);
    }

    private JsonObject getPlayerFileData(UUID uuid){
        File file = getPlayerFile(uuid);
        if (!file.exists()){
            return null;
        }
        try(FileReader reader = new FileReader(file)){
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void setupDefaults(UUID uuid) {
        File parentDir = this.configFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        JsonObject defaultConfig = new JsonObject();
        
        ConfigEnum.Type.PLAYERDATA.populate(defaultConfig);
        
        try (FileWriter writer = new FileWriter(this.configFile)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaultConfig, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        this.loadSettingsFromFile(uuid);
    }
    
    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        this.saveSettingsToFile(uuid);
    }
}