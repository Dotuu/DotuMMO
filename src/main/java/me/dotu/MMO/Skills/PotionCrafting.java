package me.dotu.MMO.Skills;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;

public class PotionCrafting extends Skill implements Listener {

    private boolean skillEnabled;

    protected PotionCrafting() {
        super("POTION_CRAFTING", SkillDifficulty.NORMAL, SkillType.POTION_CRAFTING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("POTION_CRAFTING");
    }

    @EventHandler
    public void potionBrewed(BrewEvent event){
        if (this.skillEnabled == false){
            return;
        }

        List<ItemStack> results = event.getResults();
        int amountBrewed = results.size() <=3 ? results.size() : 1;

    }
}
