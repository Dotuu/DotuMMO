package me.dotu.MMO.Skills;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.PlayerManager;
import me.dotu.MMO.DupeChecker;
import me.dotu.MMO.Enums.DropTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Messages.MessageManager;

public class Mining extends MasterSkill implements Listener{

    private final SkillEnum.Difficulty difficulty;

    public Mining() {
        super(SkillEnum.Difficulty.VERY_SLOW, SkillEnum.Skill.MINING, 2, 100, 0);
        MasterSkill.addToSkillsMap(SkillEnum.Difficulty.VERY_SLOW, SkillEnum.Skill.MINING, 2, 100, 0);
        this.difficulty = SkillEnum.Difficulty.VERY_SLOW;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if (DupeChecker.alreadyBroken(event.getBlock().getLocation()) == false){
            try {
                for (DropTableEnum.MiningDrop drop : DropTableEnum.MiningDrop.values()){
                    if (drop.toString().equals(event.getBlock().getType().toString())){
                        Player player = event.getPlayer();
                        UUID uuid = player.getUniqueId();
                        int xpGained = ExpCalculator.calculateRewardedExp(this.difficulty, drop.getXpValue());
                        PlayerManager manager = PlayerConfig.playerdataMap.get(uuid);
                        manager.setSkills(SkillEnum.Skill.MINING.toString(), xpGained);
                        player.sendMessage(MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + SkillEnum.Skill.MINING.toString().toLowerCase()));
                    }
                }
                
            } catch (Exception e) {
            }
        }
        else{
            DupeChecker.removeFromPlacedBlocks(event.getBlock().getLocation());
        }
    }
}
