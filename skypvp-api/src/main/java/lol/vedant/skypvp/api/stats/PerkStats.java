package lol.vedant.skypvp.api.stats;

import org.bukkit.entity.Player;

import java.util.List;

public class PerkStats {

    private final List<String> unlockedKits;

    public PerkStats(List<String> unlockedKits) {
        this.unlockedKits = unlockedKits;
        System.out.println(unlockedKits);
    }

    public boolean hasBulldozer() {
        return unlockedKits.contains("BULLDOZER");
    }

    public boolean hasSpeed() {
        return unlockedKits.contains("SPEED");
    }

    public boolean hasExperience() {
        return unlockedKits.contains("EXPERIENCE");
    }

    public boolean hasJuggernaut() {
        return unlockedKits.contains("JUGGERNAUT");
    }
}
