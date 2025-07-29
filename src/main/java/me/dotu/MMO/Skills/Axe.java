package me.dotu.MMO.Skills;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Managers.PlayerManager;
import me.dotu.MMO.Messages.MessageManager;
import me.dotu.MMO.UI.ExpBar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Axe extends MasterSkill implements Listener{
    
    private final JavaPlugin plugin;

    public Axe(JavaPlugin plugin) {
        super("Axes", SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.AXE, 4, 100, 0);
        MasterSkill.addToSkillsMap(this.getName(), SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.AXE, 4, 100, 0);
        this.plugin = plugin;
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent event){
        // PVE
        if (event.getEntity().getKiller() instanceof Player && !(event.getEntity() instanceof Player)){
            Player player = (Player) event.getEntity().getKiller();
            if (holdingAxe(player)){
                Entity dead = event.getEntity();
        
                for (RewardTableEnum.AxeReward drop : RewardTableEnum.AxeReward.values()){
                    if (dead.getName().replace(" ", "_").equalsIgnoreCase(drop.toString())){
                        UUID uuid = player.getUniqueId();
                        int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());
                        PlayerManager manager = PlayerConfig.playerdataMap.get(uuid);
                        manager.setSkills(SkillEnum.Skill.AXE.toString(), xpGained);

                        String msg = MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + this.getName());
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
                        ExpBar.setExpBarToSkill(player, MasterSkill.skillsMap.get(this.getSkill()));
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
