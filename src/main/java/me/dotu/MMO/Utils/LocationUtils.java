package me.dotu.MMO.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    public static String serializeLocation(Location location) {
        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return world + "." + x + "." + y + "." + z;
    }

    public static Location deSerializeLocation(String location) {
        String[] locationData = location.split("\\.");
        World world = Bukkit.getServer().getWorld(locationData[0]);
        double x = Double.parseDouble(locationData[1]);
        double y = Double.parseDouble(locationData[2]);
        double z = Double.parseDouble(locationData[3]);
        return new Location(world, x, y, z);
    }
}
