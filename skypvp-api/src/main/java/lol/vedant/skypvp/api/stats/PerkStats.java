package lol.vedant.skypvp.api.stats;

import org.bukkit.entity.Player;

public class PerkStats {

    private final boolean bulldozer;
    private final boolean speed;
    private final boolean experience;

    public PerkStats(boolean bulldozer, boolean speed, boolean experience) {
        this.bulldozer = bulldozer;
        this.speed = speed;
        this.experience = experience;
    }

    public boolean hasBulldozer() {
        return bulldozer;
    }

    public boolean hasSpeed() {
        return speed;
    }

    public boolean hasExperience() {
        return experience;
    }
}
