package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;

public class Sword extends MasterSkill implements Listener {

    private JavaPlugin plugin;

    public Sword(JavaPlugin plugin) {
        super("Axes", SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.AXE, 100, 0);
        this.plugin = plugin;
    }

    public void registerSkill() {
        addToSkillsMap(this);
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent event){
        // PVE
        if (event.getEntity().getKiller() instanceof Player && !(event.getEntity() instanceof Player)){
            Player player = (Player) event.getEntity().getKiller();
            if (holdingSword(player)){

                for (RewardTableEnum.SwordReward drop : RewardTableEnum.SwordReward.values()){
                    if (event.getEntity().getType() == drop.getEntityType()){
                        int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());

                        this.processExpReward(player, this, xpGained);
                    }
                }
            }
        }
        // PVP
        else if (event.getEntity() instanceof Player && event.getEntity().getKiller() instanceof Player){
            Player killer = (Player) event.getEntity().getKiller();
            Player dead = (Player) event.getEntity();
        }
    }

    private boolean holdingSword(Player player){
        return player.getInventory().getItemInMainHand().getType().toString().endsWith("_AXE");
    }
}
