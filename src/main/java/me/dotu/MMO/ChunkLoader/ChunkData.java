package me.dotu.MMO.ChunkLoader;

import java.util.ArrayList;

import org.bukkit.Location;

public class ChunkData {

    private ArrayList<Location> blockLocations;
    private String chunkId;
    private boolean updated;
    private boolean loaded;

    public ChunkData(ArrayList<Location> blockLocations, String chunkId, boolean updated, boolean loaded) {
        this.blockLocations = blockLocations;
        this.chunkId = chunkId;
        this.updated = updated;
        this.loaded = loaded;
    }

    public String getChunkId() {
        return this.chunkId;
    }

    public void setChunkId(String chunkId) {
        this.chunkId = chunkId;
    }

    public ArrayList<Location> getBlockLocations() {
        return this.blockLocations;
    }

    public void setBlockLocations(ArrayList<Location> blockLocations) {
        this.blockLocations = blockLocations;
    }

    public void addBlockLocations(Location loc) {
        this.blockLocations.add(loc);
    }

    public void removeBlockLocations(Location loc) {
        this.blockLocations.remove(loc);
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public boolean isUpdated() {
        return this.updated;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
