package me.dotu.MMO.Configs;

import java.util.Arrays;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Managers.JsonFileManager;

public class CustomItemConfig extends JsonFileManager{
    public CustomItemConfig() {
        super("configs", "items");

        this.setupDefaults(Arrays.asList(DefaultConfig.TOOLS,
            DefaultConfig.ARMORS,
            DefaultConfig.WEAPONS
        ));
    }

    @Override
    public void populateMap(){

    }

    @Override
    protected void saveAllToFile() {
        
    }
}
