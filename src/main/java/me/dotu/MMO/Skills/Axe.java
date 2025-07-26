package me.dotu.MMO.Skills;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.PlayerManager;
import me.dotu.MMO.Enums.DropTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Messages.MessageManager;
import me.dotu.MMO.UI.ExpBar;

public class Axe extends MasterSkill implements Listener{
    
    private SkillEnum.Difficulty difficulty;
    private SkillEnum.Skill name;

    public Axe() {
        super(SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.AXE, 4, 100, 0);
        MasterSkill.addToSkillsMap(SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.AXE, 4, 100, 0);
        this.difficulty = SkillEnum.Difficulty.NORMAL;
        this.name = SkillEnum.Skill.AXE;
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent event){
        // PVE
        if (event.getEntity().getKiller() instanceof Player && !(event.getEntity() instanceof Player)){
            Player player = (Player) event.getEntity().getKiller();
            if (holdingAxe(player)){
                Entity dead = event.getEntity();
        
                for (DropTableEnum.AxeDrop drop : DropTableEnum.AxeDrop.values()){
                    if (dead.getName().replace(" ", "_").equalsIgnoreCase(drop.toString())){
                        UUID uuid = player.getUniqueId();
                        int xpGained = ExpCalculator.calculateRewardedExp(this.difficulty, drop.getXpValue());
                        PlayerManager manager = PlayerConfig.playerdataMap.get(uuid);
                        manager.setSkills(SkillEnum.Skill.AXE.toString(), xpGained);
                        player.sendMessage(MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + SkillEnum.Skill.AXE.toString().toLowerCase()));
                        ExpBar.setExpBarToSkill(player, MasterSkill.skillsMap.get(this.name));
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

    private boolean holdingAxe(Player player){
        return player.getInventory().getItemInMainHand().getType().toString().endsWith("_AXE");
    }
}
