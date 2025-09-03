package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.ChunkDataManager;
import me.dotu.MMO.Tables.ExpSource;

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

        Player player = event.getPlayer();
        ChunkDataManager cdm = new ChunkDataManager();
        if (cdm.wasBlockBroken(event.getBlock()) == false) {
            ExpSource<?> source = this.getExpSourceBlock(event.getBlock(), player);

            if (source == null){
                return;
            }

            this.processExpReward(player, this, source.getMinExp(), source.getMaxExp());
        }
    }
}
