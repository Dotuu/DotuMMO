package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Enums.RewardTable;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.ExpCalculator;

public class Fishing extends Skill implements Listener {

    public Fishing() {
        super("Fishing", SkillDifficulty.NORMAL, SkillType.FISHING, 100, 0);
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void onFishCaught(PlayerFishEvent event) {
        boolean skillEnabled = false;
        try {
            JsonObject enabledSkills = SettingsConfig.settingsMap.get(DefaultConfig.Settings.ENABLED_SKILLS)
                    .getSettings();
            JsonObject miningSkill = enabledSkills.getAsJsonObject(SkillType.MINING.toString().toLowerCase());
            skillEnabled = miningSkill.getAsBoolean();

        } catch (Exception e) {
        }
        if (skillEnabled) {
            for (RewardTable.FishingReward drop : RewardTable.FishingReward.values()) {
                if (event.getCaught().getType() == drop.getEntityType()) {
                    Player player = event.getPlayer();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                    this.processExpReward(player, this, xpGained);
                }
            }
        }
    }
}