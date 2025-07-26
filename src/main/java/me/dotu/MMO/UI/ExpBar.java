package me.dotu.MMO.UI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class ExpBar implements Listener{
    @EventHandler
    public void expGained(PlayerExpChangeEvent event){
        event.setAmount(0);
    }

    public static void setExpBarToSkill(Player player){
        float xpTowardsNextLevel = 0f;
        float xpNeededForNextLevel = 0f;
        float progress = xpTowardsNextLevel / xpNeededForNextLevel;
        progress = Math.max(0f, Math.min(1f, progress));
        player.setExp(progress);
    }
}
