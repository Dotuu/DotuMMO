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

public class Woodcutting extends MasterSkill implements Listener{
    
    private final SkillEnum.Difficulty difficulty;

    public Woodcutting() {
        super(SkillEnum.Difficulty.VERY_SLOW, SkillEnum.Skill.WOODCUTTING, 3, 100, 0);
        MasterSkill.addToSkillsMap(SkillEnum.Difficulty.VERY_SLOW, SkillEnum.Skill.WOODCUTTING, 3, 100, 0);
        this.difficulty = SkillEnum.Difficulty.VERY_SLOW;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        if (DupeChecker.alreadyBroken(event.getBlock().getLocation())){
            for (DropTableEnum.WoodcuttingDrop drop : DropTableEnum.WoodcuttingDrop.values()){
                if (event.getBlock().getType().toString().equals(drop.toString())){
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();
                    int xpGained = ExpCalculator.calculateRewardedExp(this.difficulty, drop.getXpValue());
                    PlayerManager manager = PlayerConfig.playerdataMap.get(uuid);
                    manager.setSkills(SkillEnum.Skill.WOODCUTTING.toString(), xpGained);
                    player.sendMessage(MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + SkillEnum.Skill.WOODCUTTING.toString().toLowerCase()));
                }
            }
        }
        else{
            DupeChecker.removeFromPlacedBlocks(event.getBlock().getLocation());
        }
    }
}
