package me.dotu.MMO.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Multiplier {

    private final ItemStack[] matrix;
    private final int perCraft;

    public Multiplier(ItemStack[] matrix, int perCraft) {
        this.matrix = matrix;
        this.perCraft = perCraft;
    }

    public int calculate() {
        int maxCrafts = Integer.MAX_VALUE;

        for (ItemStack stack : this.matrix) {
            if (stack == null || stack.getType() == Material.AIR) {
                continue;
            }
            maxCrafts = Math.min(maxCrafts, stack.getAmount());
        }

        if (maxCrafts == Integer.MAX_VALUE) {
            return 0;
        }
        return this.perCraft * maxCrafts;
    }
}
