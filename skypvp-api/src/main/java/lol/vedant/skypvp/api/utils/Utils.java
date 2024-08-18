package lol.vedant.skypvp.api.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<String> cc(List<String> messages) {
        return messages.stream()
                .map(Utils::cc)
                .collect(Collectors.toList());
    }

    public static int getServerVersion() {
        String version = Bukkit.getVersion();

        if (version.contains("1.8")) {
            return 18;
        } else if (version.contains("1.9")) {
            return 19;
        } else if (version.contains("1.10")) {
            return 110;
        } else if (version.contains("1.11")) {
            return 111;
        } else if (version.contains("1.12")) {
            return 112;
        } else if (version.contains("1.13")) {
            return 113;
        } else if (version.contains("1.14")) {
            return 114;
        } else if (version.contains("1.15")) {
            return 115;
        } else if (version.contains("1.16")) {
            return 116;
        } else if (version.contains("1.17")) {
            return 117;
        } else if (version.contains("1.18")) {
            return 118;
        } else if (version.contains("1.19")) {
            return 119;
        } else if (version.contains("1.20")) {
            return 120;
        }
        return 500;
    }

    public static void setMaxHealth(Player p, int amount) {
        if (getServerVersion() >= 19) {
            AttributeInstance healthAttribute = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert healthAttribute != null;
            healthAttribute.setBaseValue(amount);
            return;
        }
        p.setMaxHealth(amount);
    }

}
