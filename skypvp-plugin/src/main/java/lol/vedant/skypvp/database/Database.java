package lol.vedant.skypvp.database;

import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.stats.KitStats;
import lol.vedant.skypvp.api.stats.PerkStats;
import lol.vedant.skypvp.api.stats.PlayerStats;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface Database {

    void init();

    void createUser(Player player);

    boolean hasStats(UUID player);

    PlayerStats getStats(UUID player);

    List<String> getKitStats(UUID player);

    void saveKitStats(UUID player, String id);

    void saveStats(UUID player, PlayerStats stats);

    PerkType getActivePerk(UUID player);

    void setActivePerk(UUID player, PerkType perk);

    void addPerk(UUID player, PerkType perk);

    PerkStats getPerks(UUID player);

    void disable();
}
