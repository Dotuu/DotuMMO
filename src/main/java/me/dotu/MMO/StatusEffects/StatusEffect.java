package me.dotu.MMO.StatusEffects;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StatusEffect {

    private PotionEffect[] effects;
    public static final HashMap<Material, StatusEffect> statusEffects = new HashMap<>();

    public StatusEffect(){

    }

    public StatusEffect(PotionEffect[] effects) {
        this.effects = effects;
    }

    public void apply(Player player) {
        for (PotionEffect effect : this.effects) {
            player.addPotionEffect(effect);
        }
    }

    public void setup() {
        PotionEffect[] cakeEffects = {
            new PotionEffect(PotionEffectType.HASTE, this.secondsToTicks(30), 2)
        };
        statusEffects.put(Material.CAKE, new StatusEffect(cakeEffects));

        PotionEffect[] cookieEffects = {
            new PotionEffect(PotionEffectType.JUMP_BOOST, this.secondsToTicks(30), 2)
        };
        statusEffects.put(Material.COOKIE, new StatusEffect(cookieEffects));
    }

    private int secondsToTicks(int num){
        return num * 20;
    }

    public PotionEffect[] getEffects() {
        return this.effects;
    }

    public void setEffects(PotionEffect[] effects) {
        this.effects = effects;
    }
}
