package lol.vedant.skypvp.game;

import lol.vedant.skypvp.api.arena.SpawnArea;
import lol.vedant.skypvp.api.arena.IArena;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Arena implements IArena {

    public static List<Player> players = new ArrayList<>();
    private SpawnArea spawnArea;
    private Location spawnLocation;

    public Arena(Location spawnLocation, SpawnArea spawnArea) {
        this.spawnArea = spawnArea;
        this.spawnLocation = spawnLocation;
    }

    @Override
    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public SpawnArea getSpawnArea() {
        return spawnArea;
    }

    @Override
    public List<Player> getPlayers() {
        return Collections.emptyList();
    }
}