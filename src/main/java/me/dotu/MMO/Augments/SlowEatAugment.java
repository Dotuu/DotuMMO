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

public class SlowEatAugment extends Augment implements Listener{
    
    private final NamespacedKey namedKey = new NamespacedKey(Main.plugin, AugmentEnum.Augment.SLOW_EAT.getName());

    public SlowEatAugment() {
        super("Slow Eat", 1, new ItemEnum.Tier[]{
            ItemEnum.Tier.COMMON,
            ItemEnum.Tier.EPIC
        });
    }
    
    @EventHandler
    public void onEatEvent(PlayerItemConsumeEvent event){
        ArrayList<ItemStack> items = this.getAugmentableItems(event.getPlayer());

        if (this.hasAugment(items, this.namedKey)){
            event.getPlayer().sendMessage("Augment detected!");
        }
    }
}
