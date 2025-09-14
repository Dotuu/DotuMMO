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
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Enums.SpawnerKey;
import me.dotu.MMO.Inventories.SpawnerInventory;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;
import me.dotu.MMO.Spawners.SpawnerLocationData;
import me.dotu.MMO.Utils.LocationUtils;
import me.dotu.MMO.Utils.Marker;
import net.md_5.bungee.api.ChatColor;

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

                player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Spawner "
                        + ChatColor.stripColor(item.getItemMeta().getDisplayName()) + " added to inventory"));
            } else {
                player.sendMessage(
                        MessageManager.send(MessageManager.Type.ERROR, "Swawner not found, name is case sensitive"));
            }
        }
    }

    private void handleEditSpawnerCommand(Player player, String[] args) {
        if (this.editing.containsKey(player.getName())) {
            Marker marker = this.viewingMarkers.get(player.getName());
            marker.removeAllMarkers();
            this.editing.remove(player.getName());
            this.viewingMarkers.remove(player.getName());
            player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Leaving editing mode"));
            return;
        }

        Block block = player.getTargetBlockExact(10);
        if (block == null) {
            player.sendMessage(
                    MessageManager.send(MessageManager.Type.ERROR, "Spawner block not found within 10 blocks"));
            return;
        }

        if (block.getType() != Material.SPAWNER) {
            player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Target block is not a spawner block"));
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        if (!spawner.getPersistentDataContainer().has(SpawnerKey.NAME.getKey())) {
            player.sendMessage(
                    MessageManager.send(MessageManager.Type.ERROR, "Target block is not a DotuMMO custom spawner"));
        }

        else {
            String spawnerLocString = LocationUtils.serializeLocation(block.getLocation());
            if (!SpawnerLocationDataConfig.spawnerLocationData.containsKey(spawnerLocString)) {
                player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Spawner not found"));
                return;
            }

            SpawnerLocationData sld = SpawnerLocationDataConfig.spawnerLocationData
                    .get(LocationUtils.serializeLocation(block.getLocation()));

            player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Now in edit mode"));
            player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Use /dotummo spawner edit to exit"));
            player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS,
                    "Left Click block to set spawn block, right click block to remove spawn block"));

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
                    player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Added spawn location"));
                }
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Location clickedLoc = event.getClickedBlock().getLocation();
                if (sld.getSpawnLocations().contains(clickedLoc)) {
                    sld.removeSpawnLocations(clickedLoc);
                    marker.removeMarker(clickedLoc);
                    player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Removed spawn location"));
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
