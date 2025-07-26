package me.dotu.MMO.Skills;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.dotu.MMO.Enums.SkillEnum;

public class Axe extends MasterSkill implements Listener{
    
    public Axe() {
        super(SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.AXE, 4, 100, 0);
    }
    
    @EventHandler
    public void damagedByEntity(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
        }
        else if (event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (damager.getHealth() == 0){
                
            }
        }
    }
}
