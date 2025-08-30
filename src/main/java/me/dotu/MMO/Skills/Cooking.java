package me.dotu.MMO.Skills;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;

public class Cooking extends Skill implements Listener {

    private final boolean skillEnabled;

    public Cooking() {
        super("Cooking", SkillDifficulty.NORMAL, SkillType.COOKING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("cooking");
    }

    @EventHandler
    public void furnaceSmeltEvent(FurnaceSmeltEvent event) {
        if (this.skillEnabled == false){
            return;
        }

        ItemStack source = event.getSource();
        Material sourceMaterial = source.getType();
    }
}
