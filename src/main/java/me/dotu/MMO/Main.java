package me.dotu.MMO;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Augments.SlowEatAugment;
import me.dotu.MMO.ChunkLoader.ChunkDataManager;
import me.dotu.MMO.Commands.DotuMmoCommand;
import me.dotu.MMO.Commands.TestCommand;
import me.dotu.MMO.Configs.ItemConfig;
import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Managers.PvpManager;
import me.dotu.MMO.Skills.Axe;
import me.dotu.MMO.Skills.Fishing;
import me.dotu.MMO.Skills.Mining;
import me.dotu.MMO.Skills.Sword;
import me.dotu.MMO.Skills.Woodcutting;
import me.dotu.MMO.Stations.Augment;
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
        this.registerSkills();
        
        // Other
        this.getServer().getPluginManager().registerEvents(new ChunkDataManager(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerConfig(this), this);
        this.getServer().getPluginManager().registerEvents(new ExpBar(), this);
        this.getServer().getPluginManager().registerEvents(new PvpManager(), this);
        this.getServer().getPluginManager().registerEvents(new Augment(this), this);

        // Event Listeners (Augments)
        this.getServer().getPluginManager().registerEvents(new SlowEatAugment(this), this);

        // Command executors
        this.getCommand("test").setExecutor(new TestCommand(this));
        this.getCommand("chunktest").setExecutor(new TestCommand(this));
        this.getCommand("dotummo").setExecutor(new DotuMmoCommand());


        // Load essentials
    }

    public void registerSkills(){
        Fishing fishing = new Fishing(this);
        this.getServer().getPluginManager().registerEvents(fishing, this);
        fishing.addToSkillsMap(fishing);

        Axe axe = new Axe(this);
        this.getServer().getPluginManager().registerEvents(axe, this);
        axe.addToSkillsMap(axe);

        Mining mining = new Mining(this);
        this.getServer().getPluginManager().registerEvents(mining, this);
        mining.addToSkillsMap(mining);

        Sword sword;
        sword = new Sword(this);
        this.getServer().getPluginManager().registerEvents(sword, this);
        sword.addToSkillsMap(sword);

        Woodcutting woodcutting = new Woodcutting(this);
        this.getServer().getPluginManager().registerEvents(woodcutting, this);
        woodcutting.addToSkillsMap(woodcutting);
    }

    @Override
    public void onDisable() {
        System.out.println("DotuMMO has been disabled!");
        PlayerConfig playerConfig = new PlayerConfig(this);
        playerConfig.saveAllPlayerSettingsToFile();

        SettingsConfig settingsConfig = new SettingsConfig(this);
        settingsConfig.saveSettingsToFile();
    }

    public static void main(String[] args) {
        
    }
}