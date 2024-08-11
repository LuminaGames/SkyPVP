package lol.vedant.skypvp.game;

import lol.vedant.skypvp.api.arena.SpawnArea;
import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static lol.vedant.skypvp.SkyPVP.config;

public class GameManager {

    private Location spawnLocation;
    private Location arenaMinSpawn;
    private Location arenaMaxSpawn;
    private SpawnArea spawnArea;
    private boolean isSetup;

    private List<Player> buildMode = new ArrayList<>();

    public void loadArena() {
        boolean isSpawnPresent = config.getYml().contains(ConfigPath.ARENA_SPAWN);
        boolean isMinSpawnPresent = config.getYml().contains(ConfigPath.ARENA_MIN_SPAWN);
        boolean isMaxSpawnPresent = config.getYml().contains(ConfigPath.ARENA_MAX_SPAWN);

        if (isSpawnPresent && isMinSpawnPresent && isMaxSpawnPresent) {
            spawnLocation = Utils.getLocation(config.getString(ConfigPath.ARENA_SPAWN));
            arenaMinSpawn = Utils.getLocation(config.getString(ConfigPath.ARENA_MIN_SPAWN));
            arenaMaxSpawn = Utils.getLocation(config.getString(ConfigPath.ARENA_MAX_SPAWN));
            this.isSetup = true;
        } else {
            this.isSetup = false;
        }
    }

    public boolean isSetup() {
        return isSetup;
    }

    public void enableBuildMode(Player player) {
        if (!buildMode.contains(player)) {
            buildMode.add(player);
        }
    }

    public void disableBuildMode(Player player) {
        buildMode.remove(player);
    }

    public boolean hasBuildMode(Player player) {
        return buildMode.contains(player);
    }
}
