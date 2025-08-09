package me.dotu.MMO.Configs;

import java.io.File;
import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.JsonFileManager;

public class ItemConfig extends JsonFileManager{
    public ItemConfig(JavaPlugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "items.json"), "configs");

        this.createFileIfNotExists("items.json");

        this.setupDefaults(Arrays.asList(
            ConfigEnum.Type.TOOLS,
            ConfigEnum.Type.ARMORS,
            ConfigEnum.Type.WEAPONS
        ));
    }
}
