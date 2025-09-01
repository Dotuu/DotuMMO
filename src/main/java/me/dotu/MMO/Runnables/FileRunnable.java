package me.dotu.MMO.Runnables;

import org.bukkit.Bukkit;

import me.dotu.MMO.Configs.ChunkDataConfig;
import me.dotu.MMO.Main;

public class FileRunnable implements Runnable {

    private int taskId = -2;
    private final Long interval = 6000L;

    public void start() {
        this.taskId = Bukkit.getScheduler().runTaskTimer(Main.plugin, this, 20L, this.interval).getTaskId();
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public void run() {
        Bukkit.broadcastMessage("Saving all chunk data to file!");
        this.saveChunkData();
    }

    private void saveChunkData() {
        ChunkDataConfig chunkDataConfig = new ChunkDataConfig();
        chunkDataConfig.saveAllToFile();
    }
}
