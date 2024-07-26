package lol.vedant.skypvp.api.arena;

import org.bukkit.Location;

public class SpawnArea {

    private final Location min;
    private final Location max;

    public SpawnArea(Location min, Location max) {
        this.min = min;
        this.max = max;
    }

    public Location getMin() {
        return min;
    }

    public Location getMax() {
        return max;
    }
}
