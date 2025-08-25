package me.dotu.MMO.Configs;

import java.util.Arrays;

import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Managers.JsonFileManager;

public class ItemConfig extends JsonFileManager{
    public ItemConfig() {
        super("configs", "items");

        this.setupDefaults(Arrays.asList(DefaultConfig.Type.TOOLS,
            DefaultConfig.Type.ARMORS,
            DefaultConfig.Type.WEAPONS
        ));
    }
}
