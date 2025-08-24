package me.dotu.MMO.Configs;

import java.util.Arrays;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.JsonFileManager;

public class ItemConfig extends JsonFileManager{
    public ItemConfig() {
        super("configs", "items");

        this.setupDefaults(Arrays.asList(
            ConfigEnum.Type.TOOLS,
            ConfigEnum.Type.ARMORS,
            ConfigEnum.Type.WEAPONS
        ));
    }
}
