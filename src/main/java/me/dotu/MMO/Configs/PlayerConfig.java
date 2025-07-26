package me.dotu.MMO.Configs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
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

public class PlayerConfig implements Listener{
    private File configFile;
    private final JavaPlugin plugin;
    private String filename;
    public static HashMap<UUID, PlayerManager> playerdataMap = new HashMap<>();

    public PlayerConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    
    public void updatePlayerConfig(UUID uuid){
        PlayerManager manager = playerdataMap.get(uuid);
        if (manager != null){
            File file = this.getPlayerConfig(uuid);
            
            try(FileWriter writer = new FileWriter(file)){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(manager.config, writer);
            }catch(Exception e){
            }
        }
    }

    public void mapPlayerConfig(UUID uuid){
        this.filename = uuid.toString() + ".json";
        if (!this.filename.isEmpty()){
            this.configFile = new File(new File(this.plugin.getDataFolder(), "playerdata"), this.filename);
            if (!this.configFile.exists()){
                this.setupDefaults(uuid);
            }
            else{
                playerdataMap.put(uuid, new PlayerManager(getPlayerData(uuid)));
            }
        }
    }

    private File getPlayerConfig(UUID uuid){
        String playerFile = uuid.toString() + ".json";
        return new File(new File(this.plugin.getDataFolder(), "playerdata"), playerFile);
    }

    private JsonObject getPlayerData(UUID uuid){
        File file = getPlayerConfig(uuid);
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
        System.out.println("DotuMMO - Creating player file for: " + uuid);
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
        this.mapPlayerConfig(uuid);
    }
    
    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        this.updatePlayerConfig(uuid);
    }
}
