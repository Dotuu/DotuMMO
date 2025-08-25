package me.dotu.MMO;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import me.dotu.MMO.Enums.MarkerColor;

public class Marker {
    private final Player player;
    private ArrayList<Location> markerLocations;
    private final HashMap<Location, Block> blockData = new HashMap<>();
    private MarkerColor glassColor;
    private int height;
    private final int heightFromGround = 2;

    public Marker(Player player, ArrayList<Location> markerLocations, MarkerColor glassColor, int height) {
        this.player = player;
        this.markerLocations = markerLocations;
        this.glassColor = glassColor;
        this.height = height;
        this.setupMarkers();
    }

    private void setupMarkers() {
        for (Location loc : this.markerLocations) {
            if (loc == null || loc.getWorld() == null) {
                continue;
            }
            this.addMarker(loc);
        }
    }

    public void addMarker(Location loc) {
        if (loc == null || loc.getWorld() == null) {
            return;
        }
        Location base = loc.clone().add(0, heightFromGround, 0);
        Location current = base.clone();

        for (int i = 0; i <= this.height; i++) {
            blockData.putIfAbsent(current.clone(), current.getBlock());
            player.sendBlockChange(current, this.glassColor.getMaterial().createBlockData());
            current.add(0, 1, 0);
        }
    }

    public void removeMarker(Location loc) {
        if (loc == null || loc.getWorld() == null) {
            return;
        }
        Location base = loc.clone().add(0, heightFromGround, 0);
        Location current = base.clone();

        for (int i = 0; i <= this.height; i++) {
            Block original = blockData.get(current);
            if (original != null) {
                player.sendBlockChange(current, original.getBlockData());
            } else {
                player.sendBlockChange(current, current.getBlock().getBlockData());
            }
            current.add(0, 1, 0);
        }
    }

    public void removeAllMarkers() {
        for (Location loc : this.markerLocations) {
            if (loc == null || loc.getWorld() == null) {
                continue;
            }
            Location base = loc.clone().add(0, heightFromGround, 0);
            Location current = base.clone();
            for (int i = 0; i <= this.height; i++) {
                Block original = blockData.get(current);
                if (original != null) {
                    player.sendBlockChange(current, original.getBlockData());
                } else {
                    player.sendBlockChange(current, current.getBlock().getBlockData());
                }
                current.add(0, 1, 0);
            }
        }
    }

    public ArrayList<Location> getMarkerLocations() {
        return this.markerLocations;
    }

    public void setMarkerLocations(ArrayList<Location> markerLocations) {
        this.markerLocations = markerLocations;
    }

    public MarkerColor getGlassColor() {
        return this.glassColor;
    }

    public void setGlassColor(MarkerColor glassColor) {
        this.glassColor = glassColor;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}