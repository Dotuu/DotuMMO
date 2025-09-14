package me.dotu.MMO;

import org.bukkit.plugin.java.JavaPlugin;

import me.dotu.MMO.Commands.DotuMmoCommand;
import me.dotu.MMO.Commands.SubCommands.ItemSubCommand;
import me.dotu.MMO.Commands.SubCommands.PvpSubCommand;
import me.dotu.MMO.Commands.SubCommands.SpawnerSubCommand;
import me.dotu.MMO.Commands.SubCommands.TableSubCommand;
import me.dotu.MMO.Configs.ChunkDataConfig;
import me.dotu.MMO.Configs.LootTableConfig;
import me.dotu.MMO.Configs.PlayerConfig;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Configs.SkillTableConfig;
import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Configs.SpawnerLocationDataConfig;
import me.dotu.MMO.Inventories.SpawnerInventoryClicked;
import me.dotu.MMO.Managers.ChunkDataManager;
import me.dotu.MMO.Managers.PvpManager;
import me.dotu.MMO.Runnables.FileRunnable;
import me.dotu.MMO.Runnables.SpawnerRunnable;
import me.dotu.MMO.Skills.Axe;
import me.dotu.MMO.Skills.Cooking;
import me.dotu.MMO.Skills.Farming;
import me.dotu.MMO.Skills.Fishing;
import me.dotu.MMO.Skills.Mining;
import me.dotu.MMO.Skills.Sword;
import me.dotu.MMO.Skills.Woodcutting;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;
import me.dotu.MMO.Stations.Augment;
import me.dotu.MMO.StatusEffects.StatusEffect;
import me.dotu.MMO.UI.ExpBar;

public class Main extends JavaPlugin {
    public static Main plugin;

    /*
     * Make function to kill every entity on the map that has dotummo tag for
     * spawners
     * I need active entity count to be seperate for every spawner on the map
     */

    private SpawnerConfig spawnerConfig;
    private SettingsConfig settingsConfig;
    private LootTableConfig lootTableConfig;
    private SkillTableConfig itemTableConfig;
    private PlayerConfig playerConfig;
    private ChunkDataConfig chunkDataConfig;
    private CustomSpawnerHandler customSpawnerHandler;
    private SpawnerLocationDataConfig spawnerLocationDataConfig;
    private SpawnerRunnable spawnerRunnable;
    private FileRunnable fileRunnable;

    @Override
    public void onEnable() {
        System.out.println("DotuMMO has been enabled!");

        plugin = this;

        this.spawnerConfig = new SpawnerConfig();
        this.settingsConfig = new SettingsConfig();
        this.lootTableConfig = new LootTableConfig();
        this.playerConfig = new PlayerConfig();
        this.chunkDataConfig = new ChunkDataConfig();
        this.itemTableConfig = new SkillTableConfig();
        this.customSpawnerHandler = new CustomSpawnerHandler();
        this.spawnerLocationDataConfig = new SpawnerLocationDataConfig();
        this.spawnerRunnable = new SpawnerRunnable();
        this.fileRunnable = new FileRunnable();

        SpawnerSubCommand spawnerSubCommand = new SpawnerSubCommand();
        PvpSubCommand pvpSubCommand = new PvpSubCommand();
        TableSubCommand tableSubCommand = new TableSubCommand();
        ItemSubCommand itemSubCommand = new ItemSubCommand();

        // Event Listeners
        this.registerSkills();

        // Other
        this.getServer().getPluginManager().registerEvents(new ChunkDataConfig(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerConfig(), this);
        this.getServer().getPluginManager().registerEvents(new ExpBar(), this);
        this.getServer().getPluginManager().registerEvents(new PvpManager(), this);
        this.getServer().getPluginManager().registerEvents(new Augment(), this);
        this.getServer().getPluginManager().registerEvents(new CustomSpawnerHandler(), this);
        this.getServer().getPluginManager().registerEvents(new ChunkDataManager(), this);
        this.getServer().getPluginManager().registerEvents(spawnerSubCommand, this);

        // Event Listeners (Augments)

        // Event Listeners (Inventory)
        this.getServer().getPluginManager().registerEvents(new SpawnerInventoryClicked(), this);

        // Command executors
        this.getCommand("dotummo").setExecutor(new DotuMmoCommand(spawnerSubCommand, pvpSubCommand, tableSubCommand, itemSubCommand));

        // Runnables
        this.spawnerRunnable.start();
        this.fileRunnable.start();

        // Status Effects
        StatusEffect statusEffect = new StatusEffect();
        statusEffect.setup();
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

        Cooking cooking = new Cooking();
        this.getServer().getPluginManager().registerEvents(cooking, this);
        cooking.addToSkillsMap(cooking);

        Farming farming = new Farming();
        this.getServer().getPluginManager().registerEvents(farming, this);
        farming.addToSkillsMap(farming);
    }

    @Override
    public void onDisable() {
        System.out.println("DotuMMO has been disabled!");

        this.playerConfig.saveAllToFile();
        this.settingsConfig.saveAllToFile();
        this.chunkDataConfig.saveAllChunkDataToFileOnDisable();
        ;
        this.spawnerConfig.saveAllToFile();
        this.customSpawnerHandler.killTaggedEntities();
        this.spawnerLocationDataConfig.saveAllToFile();

        this.spawnerRunnable.stop();
        this.fileRunnable.stop();
    }

    public static void main(String[] args) {

    }

    private static class StatusEffects {

        public StatusEffects() {
        }
    }
}