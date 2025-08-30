package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import me.dotu.MMO.Enums.RewardTable;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.ExpCalculator;

public class Fishing extends Skill implements Listener {

    private final boolean skillEnabled;

    public Fishing() {
        super("Fishing", SkillDifficulty.NORMAL, SkillType.FISHING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("fishing");
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void onFishCaught(PlayerFishEvent event) {
        if (this.skillEnabled == false){
            return;
        }
        
        for (RewardTable.FishingReward drop : RewardTable.FishingReward.values()) {
            if (event.getCaught().getType() == drop.getEntityType()) {
                Player player = event.getPlayer();
                int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                this.processExpReward(player, this, xpGained);
            }
        }
    }
}