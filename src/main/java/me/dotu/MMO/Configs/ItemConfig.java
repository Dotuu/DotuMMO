package me.dotu.MMO.Configs;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Enums.ConfigEnum;
import me.dotu.MMO.Managers.JsonFileManager;

public class ItemConfig extends JsonFileManager{
    public ItemConfig(JavaPlugin plugin) {
        super(plugin);

        this.createFileIfNotExists("itemdata.json");

        this.setupDefaults(ConfigEnum.Type.TOOLS);
        this.setupDefaults(ConfigEnum.Type.ARMORS);
        this.setupDefaults(ConfigEnum.Type.WEAPONS);
    }

    @Override
    protected void loadFromFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void saveToFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
