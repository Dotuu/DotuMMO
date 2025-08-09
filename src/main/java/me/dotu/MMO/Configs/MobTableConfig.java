package me.dotu.MMO.Configs;

import java.io.File;
import java.util.Arrays;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.JsonFileManager;

public class MobTableConfig extends JsonFileManager{

    public MobTableConfig(JavaPlugin plugin) {
        super(plugin, new File(plugin.getDataFolder(), "mobtable.json"), "configs");

        this.createFileIfNotExists("mobtable.json");

        this.setupDefaults(Arrays.asList(
            ConfigEnum.Type.MOB_TABLE
        )); 
    }
}
