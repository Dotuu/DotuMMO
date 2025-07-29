package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.ChunkLoader.ChunkDataManager;
import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;

public class Mining extends MasterSkill implements Listener{
    private final JavaPlugin plugin;

    public Mining(JavaPlugin plugin) {
        super("Mining", SkillEnum.Difficulty.SLOW, SkillEnum.Skill.MINING, 2, 100, 0);
        MasterSkill.addToSkillsMap(this.getName(), this.getDifficulty(), this.getSkill(), this.getId(), this.getMaxLevel(), this.getStartingLevel());
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        ChunkDataManager cdm = new ChunkDataManager(this.plugin);
        if (cdm.wasBlockBroken(event.getBlock()) == false){
            for (RewardTableEnum.MiningReward drop : RewardTableEnum.MiningReward.values()){
                if (drop.toString().equals(event.getBlock().getType().toString())){
                    Player player = event.getPlayer();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                    this.processExpReward(player, this, xpGained);
                }
            }
        }
    }
}
