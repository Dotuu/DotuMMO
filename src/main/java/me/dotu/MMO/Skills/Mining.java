package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.dotu.MMO.ChunkLoader.ChunkDataManager;
import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;

public class Mining extends Skill implements Listener{

    public Mining() {
        super("Mining", SkillEnum.Difficulty.SLOW, SkillEnum.Skill.MINING, 100, 0);
    }
    
    public void registerSkill(){
        addToSkillsMap(this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        ChunkDataManager cdm = new ChunkDataManager();
        if (cdm.wasBlockBroken(event.getBlock()) == false){
            for (RewardTableEnum.MiningReward drop : RewardTableEnum.MiningReward.values()){
                if (event.getBlock().getType() == drop.getMaterial()){
                    Player player = event.getPlayer();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                    this.processExpReward(player, this, xpGained);
                }
            }
        }
    }
}
