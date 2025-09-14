package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.ChunkDataManager;
import me.dotu.MMO.Tables.SkillSource;

public class Mining extends Skill implements Listener {

    private final boolean skillEnabled;

    public Mining() {
        super("MINING", SkillDifficulty.SLOW, SkillType.MINING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("MINING");
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.skillEnabled == false) {
            return;
        }

        ChunkDataManager cdm = new ChunkDataManager();
        Player player = event.getPlayer();

        if (cdm.wasBlockBroken(event.getBlock()) == false) {
            SkillSource<?> source = this.getSourceBlock(event.getBlock(), player);

            if (source == null){
                return;
            }

            this.processExpReward(player, this, source.getMinExp(), source.getMaxExp(), 1);
        }
    }
}
