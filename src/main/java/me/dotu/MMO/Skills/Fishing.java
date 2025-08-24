package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;

public class Fishing extends Skill implements Listener {

    public Fishing() {
        super("Fishing", SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.FISHING, 100, 0);
    }

    public void registerSkill(){
        addToSkillsMap(this);
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
            for (RewardTableEnum.FishingReward drop : RewardTableEnum.FishingReward.values()) {
                if (event.getCaught().getType() == drop.getEntityType()) {
                    Player player = event.getPlayer();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                    this.processExpReward(player, this, xpGained);
                }
            }
        }
    }
}