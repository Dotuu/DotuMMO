package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Tables.ItemSource;

public class Fishing extends Skill implements Listener {

    private final boolean skillEnabled;

    public Fishing() {
        super("FISHING", SkillDifficulty.NORMAL, SkillType.FISHING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("FISHING");
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

        ItemSource<?> source = this.getExpSourceEntity(event.getCaught(), player);

        if (source == null){
            return;
        }

        this.processExpReward(player, this, source.getMinExp(), source.getMaxExp(), 1);
    }
}