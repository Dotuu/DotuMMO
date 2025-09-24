package me.dotu.MMO.Commands.SubCommands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Commands.SubCommand;
import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Configs.SpawnerLocationDataConfig;
import me.dotu.MMO.Enums.MarkerColor;
import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Enums.SpawnerKey;
import me.dotu.MMO.Inventories.CustomSpawner.SpawnerInventory;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;
import me.dotu.MMO.Spawners.SpawnerLocationData;
import me.dotu.MMO.Utils.LocationUtils;
import me.dotu.MMO.Utils.Marker;

public class SpawnerSubCommand implements SubCommand, Listener {

    private HashMap<String, String> editing = new HashMap<>();
    private HashMap<String, Marker> viewingMarkers = new HashMap<>();

    @Override
    public String getName() {
        return "spawner";
    }

    @Override
    public String getPermission() {
        return PermissionType.SPAWNER.getPermission();
    }

    @Override
    public boolean isConsoleSafe() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission(this.getPermission()) || player.hasPermission(PermissionType.ADMIN.getPermission())) {
            if (args.length == 1) {
                // send sub command list
                return false;
            }

            switch (args[1].toLowerCase()) {
                case "add":
                    this.handleAddSpawnerCommand(player, args);
                    break;
                case "edit":
                    this.handleEditSpawnerCommand(player, args);
                    break;
            }
        }
        return true;
    }

    private void handleAddSpawnerCommand(Player player, String[] args) {
        if (args.length == 2) {
            SpawnerInventory spawnerInv = new SpawnerInventory(player, 0);
            spawnerInv.createInventoryItems();
            spawnerInv.openInventory(player);
        } else {
            String spawnerName = args[2];
            if (SpawnerConfig.spawners.containsKey(spawnerName)) {
                CustomSpawner customSpawner = SpawnerConfig.spawners.get(spawnerName);
                ItemStack item = CustomSpawnerHandler.decorateSpawnerStack(customSpawner);

                player.getInventory().addItem(item).isEmpty();

                MessageManager.send(player, Messages.SPAWNER_ADDED, true, spawnerName);
            } else {
                MessageManager.send(player, Messages.ERR_SPAWNER_EXISTS, true);
            }
        }
    }

    private void handleEditSpawnerCommand(Player player, String[] args) {
        if (this.editing.containsKey(player.getName())) {
            Marker marker = this.viewingMarkers.get(player.getName());
            marker.removeAllMarkers();
            this.editing.remove(player.getName());
            this.viewingMarkers.remove(player.getName());
            MessageManager.send(player, Messages.EDIT_MODE_LEAVE, true);
            return;
        }

        Block block = player.getTargetBlockExact(10);
        if (block == null) {
            MessageManager.send(player, Messages.ERR_SPAWNER_BLOCK_NOT_FOUND, true);
            return;
        }

        if (block.getType() != Material.SPAWNER) {
            MessageManager.send(player, Messages.ERR_SPAWNER_VALID_BLOCK, true);
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        if (!spawner.getPersistentDataContainer().has(SpawnerKey.NAME.getKey())) {
            MessageManager.send(player, Messages.ERR_SPAWNER_CUSTOM_SPAWNER, true);
        }

        else {
            String spawnerLocString = LocationUtils.serializeLocation(block.getLocation());
            if (!SpawnerLocationDataConfig.spawnerLocationData.containsKey(spawnerLocString)) {
                MessageManager.send(player, Messages.ERR_SPAWNER_GENERIC, true);
                return;
            }

            SpawnerLocationData sld = SpawnerLocationDataConfig.spawnerLocationData.get(LocationUtils.serializeLocation(block.getLocation()));

            MessageManager.send(player, Messages.EDIT_MODE_ENTER, true);
            MessageManager.send(player, Messages.EDIT_MODE_INFO, true);

            Marker marker = new Marker(player, sld.getSpawnLocations(), MarkerColor.RED, 3);
            this.editing.put(player.getName(), LocationUtils.serializeLocation(sld.getSpawnerLocation()));
            this.viewingMarkers.put(player.getName(), marker);
        }
    }

    @EventHandler
    public void interactSpawnerBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (this.editing.containsKey(player.getName())) {
            String spawnerKey = this.editing.get(player.getName());
            SpawnerLocationData sld = SpawnerLocationDataConfig.spawnerLocationData.get(spawnerKey);
            Marker marker = viewingMarkers.computeIfAbsent(player.getName(),
                    k -> new Marker(player, sld.getSpawnLocations(), MarkerColor.RED, 10));

            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
                Location clickedLoc = event.getClickedBlock().getLocation();
                if (!sld.getSpawnLocations().contains(clickedLoc)) {
                    sld.addSpawnLocations(clickedLoc);
                    marker.addMarker(clickedLoc);
                    MessageManager.send(player, Messages.EDIT_MODE_LOC_ADD, true);
                }
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Location clickedLoc = event.getClickedBlock().getLocation();
                if (sld.getSpawnLocations().contains(clickedLoc)) {
                    sld.removeSpawnLocations(clickedLoc);
                    marker.removeMarker(clickedLoc);
                    MessageManager.send(player, Messages.EDIT_MODE_LOC_REMOVE, true);
                }
            }
        }
    }

    @EventHandler
    public void blockBreakCancel(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (this.editing.containsKey(player.getName())) {
            event.setCancelled(true);
        }
    }
}
