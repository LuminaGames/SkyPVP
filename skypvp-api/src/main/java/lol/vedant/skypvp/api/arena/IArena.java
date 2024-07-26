package lol.vedant.skypvp.api.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface IArena {

    void addPlayer(Player player);

    void removePlayer(Player player);

    Location getSpawnLocation();

    SpawnArea getSpawnArea();

    List<Player> getPlayers();


}
