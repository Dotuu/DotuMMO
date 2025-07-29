package me.dotu.MMO;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.ChunkLoader.ChunkDataManager;
import me.dotu.MMO.Commands.TestCommand;
import me.dotu.MMO.Configs.ItemConfig;
import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Skills.Axe;
import me.dotu.MMO.Skills.Fishing;
import me.dotu.MMO.Skills.Mining;
import me.dotu.MMO.Skills.Woodcutting;
import me.dotu.MMO.UI.ExpBar;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("DotuMMO has been enabled!");

        // setup config files
        new ItemConfig(this);
        new SettingsConfig(this);

        // setup data files
        
        // Event Listeners
        // Skills
        this.getServer().getPluginManager().registerEvents(new Fishing(this), this);
        this.getServer().getPluginManager().registerEvents(new Mining(this), this);
        this.getServer().getPluginManager().registerEvents(new Woodcutting(this), this);
        this.getServer().getPluginManager().registerEvents(new Axe(this), this);

        // Other
        this.getServer().getPluginManager().registerEvents(new PlayerConfig(this), this);
        this.getServer().getPluginManager().registerEvents(new ExpBar(), this);
        this.getServer().getPluginManager().registerEvents(new ChunkDataManager(this), this);

        // Command executors
        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("chunktest").setExecutor(new TestCommand());


        // Load essentials
    }

    @Override
    public void onDisable() {
        System.out.println("DotuMMO has been disabled!");
    }

    public static void main(String[] args) {
        
    }
}