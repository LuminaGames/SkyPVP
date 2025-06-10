package lol.vedant.skypvp.api.stats;

import org.bukkit.entity.Player;

import java.util.List;

public class PerkStats {

    private final List<String> unlockedKits;

    public PerkStats(List<String> unlockedKits) {
        this.unlockedKits = unlockedKits;
    }

    public boolean hasBulldozer() {
        return unlockedKits.contains("bulldozer");
    }

    public boolean hasSpeed() {
        return unlockedKits.contains("speed");
    }

    public boolean hasExperience() {
        return unlockedKits.contains("experience");
    }

    public boolean hasJuggernaut() {
        return unlockedKits.contains("juggernaut");
    }
}
