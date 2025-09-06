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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Main;
import me.dotu.MMO.Managers.JsonFileManager;
import me.dotu.MMO.Managers.PlayerManager;

public class PlayerConfig extends JsonFileManager implements Listener{
    public static HashMap<UUID, PlayerManager> playerSettings = new HashMap<>();

    public PlayerConfig() {
        super("data/playerdata", "");
    }

    @Override
    public void populateMap(){
        
    }

    public void saveToFile(UUID uuid){
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

    @Override
    public void saveAllToFile(){
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

    public void loadFromFile(UUID uuid){
        this.fileName = uuid.toString() + this.extension;
        if (!this.fileName.isEmpty()){
            this.file = new File(new File(Main.plugin.getDataFolder(), this.path), this.fileName);
            if (!this.getPlayerFile(uuid).exists()){
                this.setupPlayerDefaults(uuid);
            }
            playerSettings.put(uuid, new PlayerManager(getPlayerFileContents(uuid)));
        }
    }

    private File getPlayerFile(UUID uuid){
        String playerFile = uuid.toString() + this.extension;
        return new File(new File(Main.plugin.getDataFolder(), this.path), playerFile);
    }

    private JsonObject getPlayerFileContents(UUID uuid){
        File playerFile = getPlayerFile(uuid);
        if (!playerFile.exists()){
            return null;
        }
        try(FileReader reader = new FileReader(playerFile)){
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            return null;
        }
    }
    
    private void setupPlayerDefaults(UUID uuid) {
        File parentDir = this.getPlayerFile(uuid).getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        JsonObject defaultConfig = new JsonObject();
        
        DefaultConfig.PLAYERDATA.populate(defaultConfig);
        
        try (FileWriter writer = new FileWriter(this.getPlayerFile(uuid))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(defaultConfig, writer);
        } catch (Exception e) {
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        this.loadFromFile(uuid);
    }
    
    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        this.saveToFile(uuid);
    }
}