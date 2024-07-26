package lol.vedant.skypvp.game;

import lol.vedant.skypvp.api.arena.SpawnArea;
import lol.vedant.skypvp.api.config.Config;
import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Location;

import static lol.vedant.skypvp.SkyPVP.config;

public class GameManager {

    private Location spawnLocation;
    private Location arenaMinSpawn;
    private Location arenaMaxSpawn;
    private SpawnArea spawnArea;
    private boolean isSetup;

    public void loadArena() {
        if (config.getYml().contains("arena")) {
            if(config.getYml().contains(ConfigPath.ARENA_SPAWN)) {
                spawnLocation = Utils.getLocation(config.getString(ConfigPath.ARENA_SPAWN));
            }

            if(config.getYml().contains(ConfigPath.ARENA_MIN_SPAWN)) {
                arenaMinSpawn = Utils.getLocation(config.getString(ConfigPath.ARENA_MIN_SPAWN));
            }

            if(config.getYml().contains(ConfigPath.ARENA_MAX_SPAWN)) {
                arenaMaxSpawn = Utils.getLocation(config.getString(ConfigPath.ARENA_MAX_SPAWN));
            }
        }

        //Check for any misconfiguration related to arena
        check();
    }

    private boolean check() {
        if (spawnLocation == null || arenaMinSpawn == null || arenaMaxSpawn == null) {
            this.isSetup = false;
            return false;
        }

        spawnArea = new SpawnArea(arenaMinSpawn, arenaMaxSpawn);

        return true;
    }

    public boolean isSetup() {
        return isSetup;
    }


}
