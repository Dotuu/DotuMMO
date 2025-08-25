package me.dotu.MMO.Enums;

import org.bukkit.Material;

public enum MarkerColor {
    RED(Material.RED_STAINED_GLASS),
    BLUE(Material.BLUE_STAINED_GLASS),
    YELLOW(Material.YELLOW_STAINED_GLASS),
    GREEN(Material.GREEN_STAINED_GLASS);

    private final Material material;

    MarkerColor(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return this.material;
    }
}
