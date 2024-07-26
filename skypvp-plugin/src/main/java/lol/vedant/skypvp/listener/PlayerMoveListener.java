package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.events.SpawnEnterEvent;
import lol.vedant.skypvp.api.events.SpawnExitEvent;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.game.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static lol.vedant.skypvp.SkyPVP.config;

public class PlayerMoveListener implements Listener {



    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if(!config.getYml().contains(ConfigPath.ARENA_MAX_SPAWN) && !config.getYml().contains(ConfigPath.ARENA_MIN_SPAWN)) {
            return;
        }

        Location spawnMin = Utils.getLocation(config.getString(ConfigPath.ARENA_MIN_SPAWN));
        Location spawnMax = Utils.getLocation(config.getString(ConfigPath.ARENA_MAX_SPAWN));

        if(isInSpawn(player.getLocation(), spawnMin, spawnMax) && !SpawnManager.isInSpawn(player)) {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnEnterEvent(player));
            SpawnManager.addPlayer(player);
        } else {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnExitEvent(player));
            SpawnManager.removePlayer(player);
        }

    }

    public boolean isInSpawn(Location location, Location minLocation, Location maxLocation) {

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double minX = Math.min(minLocation.getX(), maxLocation.getX());
        double minY = Math.min(minLocation.getY(), maxLocation.getY());
        double minZ = Math.min(minLocation.getZ(), maxLocation.getZ());
        double maxX = Math.max(minLocation.getX(), maxLocation.getX());
        double maxY = Math.max(minLocation.getY(), maxLocation.getY());
        double maxZ = Math.max(minLocation.getZ(), maxLocation.getZ());

        boolean isInX = x >= minX && x <= maxX;
        boolean isInY = y >= minY && y <= maxY;
        boolean isInZ = z >= minZ && z <= maxZ;
        return isInX && isInY && isInZ;

    }

}
