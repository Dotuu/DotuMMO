package me.dotu.MMO.Augments;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Enums.AugmentEnum;
import me.dotu.MMO.Enums.ItemEnum;
import me.dotu.MMO.Main;

public class BowPowerAugment extends Augment implements Listener{
    
    private final NamespacedKey namedKey = new NamespacedKey(Main.plugin, AugmentEnum.Augment.BOW_POWER.getName());

    public BowPowerAugment() {
        super(new ItemEnum.Tier[]{
            ItemEnum.Tier.COMMON,
            ItemEnum.Tier.EPIC
        }, 10, AugmentEnum.Augment.BOW_POWER, "This augment increases bow power", AugmentEnum.Category.BOW);
    }
    
    @EventHandler
    public void onEatEvent(PlayerItemConsumeEvent event){
        ArrayList<ItemStack> items = this.getAugmentableItems(event.getPlayer());

        if (this.hasAugment(items, this.namedKey)){
            event.getPlayer().sendMessage("Augment detected!");
        }
    }
}
