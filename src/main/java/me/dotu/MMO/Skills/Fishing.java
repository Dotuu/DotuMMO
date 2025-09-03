package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Tables.ExpSource;

public class Fishing extends Skill implements Listener {

    private final boolean skillEnabled;

    public Fishing() {
        super("Fishing", SkillDifficulty.NORMAL, SkillType.FISHING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("fishing");
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void onFishCaught(PlayerFishEvent event) {
        if (this.skillEnabled == false){
            return;
        }
        
        Player player = event.getPlayer();

        ExpSource<?> source = this.getExpSourceEntity(event.getCaught(), player);

        if (source == null){
            return;
        }

        this.processExpReward(player, this, source.getMinExp(), source.getMaxExp());
    }
}