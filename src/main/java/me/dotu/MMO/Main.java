package me.dotu.MMO;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Augments.BowPowerAugment;
import me.dotu.MMO.ChunkLoader.ChunkDataManager;
import me.dotu.MMO.Commands.DotuMmoCommand;
import me.dotu.MMO.Commands.TestCommand;
import me.dotu.MMO.Configs.ItemConfig;
import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Inventories.SpawnerInventoryClicked;
import me.dotu.MMO.Managers.PvpManager;
import me.dotu.MMO.Skills.Axe;
import me.dotu.MMO.Skills.Fishing;
import me.dotu.MMO.Skills.Mining;
import me.dotu.MMO.Skills.Sword;
import me.dotu.MMO.Skills.Woodcutting;
import me.dotu.MMO.Stations.Augment;
import me.dotu.MMO.UI.ExpBar;

public class Main extends JavaPlugin {

    public static Main plugin;
    /*
     * move augment code to seperate file to manage augmenting an item
     * do the same for gems
     */
    @Override
    public void onEnable() {
        System.out.println("DotuMMO has been enabled!");

        plugin = this;

        // setup config files
        new ItemConfig();
        new SettingsConfig();
        new SpawnerConfig();

        // setup data files

        // Event Listeners
        this.registerSkills();

        // Other
        this.getServer().getPluginManager().registerEvents(new ChunkDataManager(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerConfig(), this);
        this.getServer().getPluginManager().registerEvents(new ExpBar(), this);
        this.getServer().getPluginManager().registerEvents(new PvpManager(), this);
        this.getServer().getPluginManager().registerEvents(new Augment(), this);

        // Event Listeners (Augments)
        this.getServer().getPluginManager().registerEvents(new BowPowerAugment(), this);

        // Event Listeners (Inventory)
        this.getServer().getPluginManager().registerEvents(new SpawnerInventoryClicked(), this);

        // Command executors
        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("chunktest").setExecutor(new TestCommand());
        this.getCommand("dotummo").setExecutor(new DotuMmoCommand());

        // Load essentials
    }

    public void registerSkills() {
        Fishing fishing = new Fishing();
        this.getServer().getPluginManager().registerEvents(fishing, this);
        fishing.addToSkillsMap(fishing);

        Axe axe = new Axe();
        this.getServer().getPluginManager().registerEvents(axe, this);
        axe.addToSkillsMap(axe);

        Mining mining = new Mining();
        this.getServer().getPluginManager().registerEvents(mining, this);
        mining.addToSkillsMap(mining);

        Sword sword;
        sword = new Sword();
        this.getServer().getPluginManager().registerEvents(sword, this);
        sword.addToSkillsMap(sword);

        Woodcutting woodcutting = new Woodcutting();
        this.getServer().getPluginManager().registerEvents(woodcutting, this);
        woodcutting.addToSkillsMap(woodcutting);
    }

    @Override
    public void onDisable() {
        System.out.println("DotuMMO has been disabled!");

        PlayerConfig playerConfig = new PlayerConfig();
        playerConfig.saveAllPlayerSettingsToFile();

        SettingsConfig settingsConfig = new SettingsConfig();
        settingsConfig.saveAllSettingsToFile();

        ChunkDataManager cdm = new ChunkDataManager();
        cdm.saveAllChunkDataToFile();

        SpawnerConfig spawnerConfig = new SpawnerConfig();
        spawnerConfig.saveAllSpawnerSettingsToFile();
    }

    public static void main(String[] args) {

    }
}