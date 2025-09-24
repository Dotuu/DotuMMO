package me.dotu.MMO.Skills;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Tables.SkillSource;

public class Farming extends Skill implements Listener{
    
    private final boolean skillEnabled;

    public Farming(){
        super("FARMING", SkillDifficulty.SLOW, SkillType.FARMING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("FARMING");
    }

    @EventHandler
    public void farmBlock(BlockBreakEvent event){
        if (this.skillEnabled == false){
            return;
        }

        Player player = event.getPlayer();
        Block block = event.getBlock();
        
        if (block.getType() == Material.AIR){
            return;
        }
        
        SkillSource<?> source = this.getExpSourceMaterial(block.getType(), player);
        
        if (source == null){
            return;
        }
        
        if (!this.isRequiredLevel(source, player, this.getName().toUpperCase())){
            event.setCancelled(true);
            MessageManager.send(player, Messages.ERR_GENERIC_SKILL_LEVEL, true, this.getName(), source.getRequiredLevel());

            return;
        }
        
        if (this.isFullyGrown(event.getBlock()) == false){
            return;
        }

        this.processExpReward(player, this, source.getMinExp(), source.getMaxExp(), 1);
    }

    private boolean isFullyGrown(Block block){
        if (!(block.getBlockData() instanceof Ageable)){
            return false;
        }

        Ageable ageData = (Ageable) block.getBlockData();
        
        return ageData.getAge() == ageData.getMaximumAge();
    }
}
