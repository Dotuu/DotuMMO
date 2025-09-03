package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Tables.ExpSource;

public class Axe extends Skill implements Listener {

    private final boolean skillEnabled;

    public Axe() {
        super("Axe", SkillDifficulty.NORMAL, SkillType.AXE, 100, 0);
        this.skillEnabled = this.isSkillEnabled("axe");
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        if (this.skillEnabled == false){
            return;
        }

        // PVE
        if (event.getEntity().getKiller() instanceof Player && !(event.getEntity() instanceof Player)) {
            Player player = (Player) event.getEntity().getKiller();
            if (holdingAxe(player)) {
                ExpSource<?> source = this.getExpSourceEntity(event.getEntity(), player);

                if (source == null){
                    return;
                }

                this.processExpReward(player, this, source.getMinExp(), source.getMaxExp());
            }
        }
        // PVP change to process pvp reward instead
        else if (event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player) {
            Player killer = (Player) event.getEntity().getKiller();
            Player dead = (Player) event.getEntity();

            ExpSource<?> source = this.getExpSourceEntity(dead, killer);

            if (source == null){
                return;
            }

            this.processExpReward(killer, this, source.getMinExp(), source.getMaxExp());
        }
    }

    private boolean holdingAxe(Player player) {
        return player.getInventory().getItemInMainHand().getType().toString().endsWith("_AXE");
    }
}
