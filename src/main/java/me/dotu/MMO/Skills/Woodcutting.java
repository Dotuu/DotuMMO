package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.dotu.MMO.Enums.RewardTable;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.ChunkDataManager;
import me.dotu.MMO.ExpCalculator;

public class Woodcutting extends Skill implements Listener {

    private final boolean skillEnabled;

    public Woodcutting() {
        super("Woodcutting", SkillDifficulty.SLOW, SkillType.WOODCUTTING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("woodcutting");
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (this.skillEnabled == false) {
            return;
        }

        ChunkDataManager cdm = new ChunkDataManager();
        if (cdm.wasBlockBroken(event.getBlock()) == false) {
            for (RewardTable.WoodcuttingReward drop : RewardTable.WoodcuttingReward.values()) {
                if (event.getBlock().getType() == drop.getMaterial()) {
                    Player player = event.getPlayer();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                    this.processExpReward(player, this, xpGained);
                }
            }
        }
    }
}
