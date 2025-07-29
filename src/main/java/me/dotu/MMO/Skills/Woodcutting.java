package me.dotu.MMO.Skills;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.ChunkLoader.ChunkDataManager;
import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Managers.PlayerManager;
import me.dotu.MMO.Messages.MessageManager;
import me.dotu.MMO.UI.ExpBar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Woodcutting extends MasterSkill implements Listener{
    private final JavaPlugin plugin;

    public Woodcutting(JavaPlugin plugin) {
        super("Woodcutting", SkillEnum.Difficulty.VERY_SLOW, SkillEnum.Skill.WOODCUTTING, 3, 100, 0);
        MasterSkill.addToSkillsMap(this.getName(), SkillEnum.Difficulty.VERY_SLOW, SkillEnum.Skill.WOODCUTTING, 3, 100, 0);
        this.plugin = plugin;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        // if (DupeChecker.alreadyBroken(event.getBlock().getLocation())){
        ChunkDataManager cdm = new ChunkDataManager(this.plugin);
        if (cdm.wasBlockBroken(event.getBlock()) == false){
            for (RewardTableEnum.WoodcuttingReward drop : RewardTableEnum.WoodcuttingReward.values()){
                if (event.getBlock().getType().toString().equals(drop.toString())){
                    Player player = event.getPlayer();
                    UUID uuid = player.getUniqueId();

                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());
                    PlayerManager manager = PlayerConfig.playerdataMap.get(uuid);
                    manager.setSkills(SkillEnum.Skill.WOODCUTTING.toString(), xpGained);

                    String msg = MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + this.getName());
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
                    ExpBar.setExpBarToSkill(player, MasterSkill.skillsMap.get(this.getSkill()));
                }
            }
        }
    }
}
