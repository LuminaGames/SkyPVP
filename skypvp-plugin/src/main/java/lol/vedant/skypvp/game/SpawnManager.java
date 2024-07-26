package lol.vedant.skypvp.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnManager {

    public static List<Player> spawnPlayers = new ArrayList<>();

    public static void addPlayer(Player player) {
        if(!spawnPlayers.contains(player)) {
            spawnPlayers.add(player);
        }
    }

    public static void removePlayer(Player player) {
        spawnPlayers.remove(player);
    }

    public static boolean isInSpawn(Player player) {
        return spawnPlayers.contains(player);
    }
}
