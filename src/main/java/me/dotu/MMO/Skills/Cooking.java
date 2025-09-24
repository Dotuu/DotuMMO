package me.dotu.MMO.Skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Enums.SkillDifficulty;
import me.dotu.MMO.Enums.SkillType;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.StatusEffects.StatusEffect;
import me.dotu.MMO.Tables.SkillSource;
import me.dotu.MMO.Utils.Multiplier;

public class Cooking extends Skill implements Listener {

    private final boolean skillEnabled;
    private final Material[] clickableFoods = {
        Material.CAKE
    };

    public Cooking() {
        super("COOKING", SkillDifficulty.NORMAL, SkillType.COOKING, 100, 0);
        this.skillEnabled = this.isSkillEnabled("COOKING");
    }

    @EventHandler
    public void craftFoodItemEvent(CraftItemEvent event){
        if (this.skillEnabled == false){
            return;
        }

        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null && event.getCurrentItem().getType() == Material.AIR){
            return;
        }

        SkillSource<?> source = this.getExpSourceMaterial(event.getCurrentItem().getType(), player);

        if (source == null){
            return;
        }

        if (!(this.isRequiredLevel(source, player, this.getName().toUpperCase()))){
            event.setCancelled(true);
            MessageManager.send(player, Messages.ERR_GENERIC_SKILL_LEVEL, true, this.getName(), source.getRequiredLevel());
        }

        int perCraft = event.getRecipe().getResult().getAmount();
        ItemStack[] matrix = event.getInventory().getMatrix();
        int multiplier;

        if (!event.isShiftClick()){
            multiplier = perCraft;
        }
        else{
            Multiplier multiplierC = new Multiplier(matrix, perCraft);
            multiplier = multiplierC.calculate();
        }

        this.processExpReward(player, this, source.getMinExp(), source.getMaxExp(), multiplier);
    }

    @EventHandler
    public void consumeFoodItem(PlayerItemConsumeEvent event){
        if (this.skillEnabled == false){
            return;
        }

        Player player = event.getPlayer();

        SkillSource<?> source = this.getExpSourceMaterial(event.getItem().getType(), player);
        
        if (source == null){
            return;
        }

        if (!this.isRequiredLevel(source, player, this.getName().toUpperCase())){
            return;
        }

        if (event.getItem().getType().isEdible() == false){
            return;
        }

        StatusEffect statusEffects = StatusEffect.statusEffects.get(event.getItem().getType());

        if (statusEffects == null){
            return;
        }

        statusEffects.apply(player);
    }

    @EventHandler
    public void interactWithFood(PlayerInteractEvent event){
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR) || !(event.getAction() == Action.RIGHT_CLICK_BLOCK)){
            return;
        }

        Player player = event.getPlayer();
        ItemStack handItem = event.getItem();

        if (handItem == null && handItem.getType() == Material.AIR){
            return;   
        }

        SkillSource<?> source = this.getExpSourceMaterial(handItem.getType(), player);

        if (source == null){
            return;
        }

        if (!this.isRequiredLevel(source, player, this.getName().toUpperCase())){
            return;
        }

        for (Material material : this.clickableFoods){
            if (material == handItem.getType()){
                StatusEffect statusEffects = StatusEffect.statusEffects.get(handItem.getType());
                statusEffects.apply(player);
                break;
            }
        }
    }
}
