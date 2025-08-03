package me.dotu.MMO.Managers;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.ConfigEnum;

public class PvpManager implements Listener{


    public PvpManager(){
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        SettingsManager settingsManager = SettingsConfig.settingsMap.get(ConfigEnum.Settings.PVP);
        PlayerManager playerManager = PlayerConfig.playerSettings.get(event.getPlayer().getUniqueId());
        try{
            long seasonStart = settingsManager.getSettingsLong(ConfigEnum.Settings.PVP, "season_timer", System.currentTimeMillis());
            long currentPlayerSeason = playerManager.getSettingsLong(event.getPlayer().getUniqueId(), ConfigEnum.PlayerSettings.PVP, "season_timer", System.currentTimeMillis());

            event.getPlayer().sendMessage("Season start: " + Long.toString(seasonStart));
            event.getPlayer().sendMessage("Player season: " + Long.toString(currentPlayerSeason));
            if (seasonStart > currentPlayerSeason){
                playerManager.setSettingsInt(event.getPlayer().getUniqueId(), ConfigEnum.PlayerSettings.PVP, "kills", 0);
                playerManager.setSettingsInt(event.getPlayer().getUniqueId(), ConfigEnum.PlayerSettings.PVP, "deaths", 0);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getPvpKills(UUID uuid){
        try {
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject pvp = PlayerManager.getOrCreateObject(data, "pvp");

            return pvp.has("kills") ? pvp.get("kills").getAsInt() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getPvpDeaths(UUID uuid){
        try {
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject pvp = PlayerManager.getOrCreateObject(data, "pvp");

            return pvp.has("deaths") ? pvp.get("deaths").getAsInt() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public double getKdr(UUID uuid){
        try {
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject pvp = PlayerManager.getOrCreateObject(data, "pvp");

            int kills = pvp.has("kills") ? pvp.get("kills").getAsInt() : 0;
            int deaths = pvp.has("deaths") ? pvp.get("deaths").getAsInt() : 0;
            
            if (deaths == 0){
                return kills;
            }else{
                return kills / deaths;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public int getPvpRank(UUID uuid){
        try {
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            JsonObject data = PlayerManager.getOrCreateObject(manager.getSettings(), "Data");
            JsonObject pvp = PlayerManager.getOrCreateObject(data, "pvp");

            return pvp.has("rank") ? pvp.get("rank").getAsInt() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public void resetPvpSeasonForOnlinePlayers(){
        for (UUID uuid : PlayerConfig.playerSettings.keySet()){
            PlayerManager manager = PlayerConfig.playerSettings.get(uuid);
            manager.setSettingsInt(uuid, ConfigEnum.PlayerSettings.PVP, "kills", 0);
            manager.setSettingsInt(uuid, ConfigEnum.PlayerSettings.PVP, "deaths", 0);
            manager.setSettingsLong(uuid, ConfigEnum.PlayerSettings.PVP, "season_timer", System.currentTimeMillis());
        }
    }
}
