package me.dotu.MMO.Commands;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Enums.MarkerColor;
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Inventories.SpawnerInventory;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Marker;
import me.dotu.MMO.Spawners.CustomSpawner;
import me.dotu.MMO.Spawners.CustomSpawnerHandler;
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
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission(this.getPermission())
                || player.hasPermission(PermissionType.ADMIN.getPermission())) {
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
        return false;
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

        if (args.length == 2) {
            player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Please specify a spawner name"));
        } else {
            if (!SpawnerConfig.spawners.containsKey(args[2])) {
                player.sendMessage(
                        MessageManager.send(MessageManager.Type.ERROR, "Spawner with that name does not exist"));
                return;
            }

            CustomSpawner customSpawner = SpawnerConfig.spawners.get(args[2]);
            player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Now in edit mode for " + args[2]));
            player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Use /dotummo spawner edit to exit"));
            player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS,
                    "Left Click block to set spawn block, right click block to remove spawn block"));

            Marker marker = new Marker(player, customSpawner.getSpawnLocations(), MarkerColor.RED, 10);
            this.editing.put(player.getName(), args[2]);
            this.viewingMarkers.put(player.getName(), marker);
        }
    }

    @EventHandler
    public void interactSpawnerBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (this.editing.containsKey(player.getName())) {
            CustomSpawner customSpawner = SpawnerConfig.spawners.get(this.editing.get(player.getName()));
            Marker marker = viewingMarkers.computeIfAbsent(player.getName(),
                    k -> new Marker(player, customSpawner.getSpawnLocations(), MarkerColor.RED, 10));

            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
                Location clickedLoc = event.getClickedBlock().getLocation();
                if (!customSpawner.getSpawnLocations().contains(clickedLoc)) {
                    customSpawner.addSpawnLocations(clickedLoc);
                    marker.addMarker(clickedLoc);
                    player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS, "Added spawn location"));
                }
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Location clickedLoc = event.getClickedBlock().getLocation();
                if (customSpawner.getSpawnLocations().contains(clickedLoc)) {
                    customSpawner.removeSpawnLocations(clickedLoc);
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
