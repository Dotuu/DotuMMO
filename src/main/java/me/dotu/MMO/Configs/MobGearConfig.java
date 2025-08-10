package me.dotu.MMO.Configs;

import java.io.File;
import java.util.Arrays;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Main;
import me.dotu.MMO.Managers.JsonFileManager;

public class MobGearConfig extends JsonFileManager{
    
    public MobGearConfig() {

        super(new File(Main.plugin.getDataFolder(), "mobtable.json"), "configs");

        this.createFileIfNotExists("mobtable.json");

        this.setupDefaults(Arrays.asList(
            ConfigEnum.Type.MOB_TABLE
        ));
    }
}
