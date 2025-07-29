package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;

public class Fishing extends MasterSkill implements Listener {
    private final JavaPlugin plugin;

    public Fishing(JavaPlugin plugin) {
        super("Fishing", SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.FISHING, 1, 100, 0);
        MasterSkill.addToSkillsMap(this.getName(), this.getDifficulty(), this.getSkill(), this.getId(), this.getMaxLevel(), this.getStartingLevel());
        this.plugin = plugin;
    }

    @EventHandler
    public void onFishCaught(PlayerFishEvent event) {
        boolean skillEnabled = false;
        try {
            JsonObject enabledSkills = SettingsConfig.settingsMap.get(ConfigEnum.Settings.ENABLED_SKILLS).getSettings();
            JsonObject miningSkill = enabledSkills.getAsJsonObject(SkillEnum.Skill.MINING.toString().toLowerCase());
            skillEnabled = miningSkill.getAsBoolean();
            
        } catch (Exception e) {
        }
        if (skillEnabled){
            String fish = event.getCaught().getName().replace(" ", "_");
            
            for (RewardTableEnum.FishingReward drop : RewardTableEnum.FishingReward.values()) {
                if (drop.toString().equalsIgnoreCase(fish)) {
                    Player player = event.getPlayer();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                    this.processExpReward(player, this, xpGained);
                }
            }
        }
    }
}