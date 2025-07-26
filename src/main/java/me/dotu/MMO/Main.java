package me.dotu.MMO;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Commands.TestCommand;
import me.dotu.MMO.Configs.ItemConfig;
import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Data.PlacedBlocksData;
import me.dotu.MMO.Skills.Fishing;
import me.dotu.MMO.Skills.Mining;
import me.dotu.MMO.Skills.Woodcutting;
import me.dotu.MMO.UI.ExpBar;

public class Main extends JavaPlugin {

    PlacedBlocksData placedBlocksData;

    @Override
    public void onEnable() {
        System.out.println("DotuMMO has been enabled!");

        // setup config files
        new ItemConfig(this);
        new SettingsConfig(this);

        // setup data files
        new PlacedBlocksData(this);
        
        // Event Listeners
        // Skills
        this.getServer().getPluginManager().registerEvents(new Fishing(), this);
        this.getServer().getPluginManager().registerEvents(new Mining(), this);
        this.getServer().getPluginManager().registerEvents(new Woodcutting(), this);
        // Other
        this.getServer().getPluginManager().registerEvents(new PlayerConfig(this), this);
        this.getServer().getPluginManager().registerEvents(new ExpBar(), this);
        this.getServer().getPluginManager().registerEvents(new DupeChecker(), this);

        // Command executors
        this.getCommand("test").setExecutor(new TestCommand());

        // Load essentials
        this.placedBlocksData = new PlacedBlocksData(this);
        placedBlocksData.populatePlacedBlocksMap();
    }

    @Override
    public void onDisable() {
        System.out.println("DotuMMO has been disabled!");
        placedBlocksData.savePlacedBlocksMapToConfig();
    }

    public static void main(String[] args) {
        
    }
}