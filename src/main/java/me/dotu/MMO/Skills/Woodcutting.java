package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.ChunkDataManager;
import me.dotu.MMO.Tables.ItemSource;

public class Woodcutting extends Skill implements Listener {

    private final boolean skillEnabled;

    public Woodcutting() {
        super("WOODCUTTING", SkillDifficulty.SLOW, SkillType.WOODCUTTING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("WOODCUTTING");
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (this.skillEnabled == false) {
            return;
        }

        Player player = event.getPlayer();
        ChunkDataManager cdm = new ChunkDataManager();
        if (cdm.wasBlockBroken(event.getBlock()) == false) {
            ItemSource<?> source = this.getSourceBlock(event.getBlock(), player);

            if (source == null){
                return;
            }

            this.processExpReward(player, this, source.getMinExp(), source.getMaxExp(), 1);
        }
    }
}
