package me.dotu.MMO.UI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Managers.PlayerManager;
import me.dotu.MMO.Skills.MasterSkill;

public class ExpBar implements Listener{
    @EventHandler
    public void expGained(PlayerExpChangeEvent event){
        event.setAmount(0);
    }

    public static void setExpBarToSkill(Player player, MasterSkill masterSkill){
        PlayerManager manager = PlayerConfig.playerdataMap.get(player.getUniqueId());
        int currentXp = manager.getSkillExp(masterSkill.getSkill());
        int currentLevel = ExpCalculator.getLevelFromExp(currentXp, masterSkill.getDifficulty());
        
        int xpForCurrentLevel = ExpCalculator.getExpNeededForNextLevel(currentLevel, masterSkill.getDifficulty());
        int xpForNextLevel = ExpCalculator.getExpNeededForNextLevel(currentLevel + 1, masterSkill.getDifficulty());
        
        int xpTowardsNextLevel = currentXp - xpForCurrentLevel;
        int xpNeededForNextLevel = xpForNextLevel - xpForCurrentLevel;
        
        float progress = xpNeededForNextLevel > 0 ? (float) xpTowardsNextLevel / xpNeededForNextLevel : 0f;
        progress = Math.max(0f, Math.min(1f, progress));

        player.setLevel(ExpCalculator.getLevelFromExp(currentXp, masterSkill.getDifficulty()));
        player.setExp(progress);
    }
}
