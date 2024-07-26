package lol.vedant.skypvp.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Utils {

    public static Location getLocation(String loc) {
        String[] locs = loc.split(", ");
        return new Location(Bukkit.getWorld(locs[0]), Double.parseDouble(locs[1]), Double.parseDouble(locs[2]), Double.parseDouble(locs[3]));
    }

    public static String parseLocation(Location location) {
        // FORMAT: WORLD, X, Y, Z
        return location.getWorld().getName() + ", " + location.getX() + ", " + location.getY() + ", " + location.getZ();
    }

    public static String cc(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}