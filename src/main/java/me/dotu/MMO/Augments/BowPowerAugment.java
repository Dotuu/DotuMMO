package me.dotu.MMO.Augments;

import java.util.ArrayList;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Main;
import me.dotu.MMO.Enums.AugmentCategory;
import me.dotu.MMO.Enums.AugmentType;
import me.dotu.MMO.Enums.ItemTier;

public class BowPowerAugment extends Augment implements Listener {

    private final NamespacedKey namedKey = new NamespacedKey(Main.plugin, AugmentType.BOW_POWER.getName());

    public BowPowerAugment() {
        super(new ItemTier[] { ItemTier.COMMON, ItemTier.EPIC }, 10, AugmentType.BOW_POWER,
                "This augment increases bow power", AugmentCategory.BOW);
    }

    @EventHandler
    public void onEatEvent(PlayerItemConsumeEvent event) {
        ArrayList<ItemStack> items = this.getAugmentableItems(event.getPlayer());

        if (this.hasAugment(items, this.namedKey)) {
            event.getPlayer().sendMessage("Augment detected!");
        }
    }
}
