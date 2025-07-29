package me.dotu.MMO.Skills;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Enums.RewardTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Managers.PlayerManager;
import me.dotu.MMO.Messages.MessageManager;
import me.dotu.MMO.UI.ExpBar;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Fishing extends MasterSkill implements Listener {
    private final JavaPlugin plugin;

    public Fishing(JavaPlugin plugin) {
        super("Fishing", SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.FISHING, 1, 100, 0);
        MasterSkill.addToSkillsMap(this.getName(), SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.FISHING, 1, 100, 0);
        this.plugin = plugin;
    }

    @EventHandler
    public void onFishCaught(PlayerFishEvent event) {
        boolean skillEnabled = false;
        try {
            JsonObject enabledSkills = SettingsConfig.settingsMap.get(ConfigEnum.Settings.ENABLED_SKILLS).getSettings();
            JsonObject miningSkill = enabledSkills.getAsJsonObject(SkillEnum.Skill.MINING.toString().toLowerCase());
            skillEnabled = miningSkill.getAsBoolean();
            
        } catch (Exception e) {
        }
        if (skillEnabled){
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();
            String fish = event.getCaught().getName().replace(" ", "_").toUpperCase();
            
            for (RewardTableEnum.FishingReward drop : RewardTableEnum.FishingReward.values()) {
                if (drop.toString().equals(fish)) {
                    int xpGained = ExpCalculator.calculateRewardedExp(this.getDifficulty(), drop.getXpValue());
                    PlayerManager manager = PlayerConfig.playerdataMap.get(uuid);
                    manager.setSkills(SkillEnum.Skill.FISHING.toString(), xpGained);

                    String msg = MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + this.getName());
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
                    ExpBar.setExpBarToSkill(player, MasterSkill.skillsMap.get(this.getSkill()));
                }
            }
        }
    }
}