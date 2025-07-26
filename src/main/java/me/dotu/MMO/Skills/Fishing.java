package me.dotu.MMO.Skills;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import com.google.gson.JsonObject;

import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.PlayerManager;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Enums.DropTableEnum;
import me.dotu.MMO.Enums.SkillEnum;
import me.dotu.MMO.ExpCalculator;
import me.dotu.MMO.Messages.MessageManager;

public class Fishing extends MasterSkill implements Listener {

    private final SkillEnum.Difficulty difficulty;

    public Fishing() {
        super(SkillEnum.Difficulty.NORMAL, SkillEnum.Skill.FISHING, 1, 100, 0);
        this.difficulty = SkillEnum.Difficulty.NORMAL;
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
            
            for (DropTableEnum.FishingDrop drop : DropTableEnum.FishingDrop.values()) {
                if (drop.toString().equals(fish)) {
                    int xpGained = ExpCalculator.calculate(this.difficulty, drop.getXpValue());
                     try {
                        PlayerManager manager = PlayerConfig.playerdataMap.get(uuid);
                        manager.setSkills(SkillEnum.Skill.FISHING.toString(), xpGained);
                        player.sendMessage(MessageManager.send(MessageManager.Type.FUN, "Earned " + xpGained + " xp from " + SkillEnum.Skill.FISHING.toString().toLowerCase()));
                        break;
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}