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

public class Woodcutting extends MasterSkill implements Listener{
    private final JavaPlugin plugin;

    public Woodcutting(JavaPlugin plugin) {
        super("Woodcutting", SkillEnum.Difficulty.SLOW, SkillEnum.Skill.WOODCUTTING, 100, 0);
        this.plugin = plugin;
    }

    public void registerSkill(){
        addToSkillsMap(this);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        ChunkDataManager cdm = new ChunkDataManager(this.plugin);
        if (cdm.wasBlockBroken(event.getBlock()) == false){
            for (RewardTableEnum.WoodcuttingReward drop : RewardTableEnum.WoodcuttingReward.values()){
                if (event.getBlock().getType() == drop.getMaterial()){
                    Player player = event.getPlayer();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                    this.processExpReward(player, this, xpGained);
                }
            }
        }
    }
}
