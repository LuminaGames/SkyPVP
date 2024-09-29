package lol.vedant.skypvp.hooks;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.stats.PlayerStats;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class SkyPVPExpansion extends PlaceholderExpansion {

    SkyPVP plugin = SkyPVP.getPlugin();

    @Override
    public @NotNull String getIdentifier() {
        return "skypvp";
    }

    @Override
    public @NotNull String getAuthor() {
        return "COMPHACK";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String args) {

        PlayerStats stats = plugin.getDb().getStats(player.getUniqueId());

        if (args.endsWith("kills")) {
            return String.format("%d", stats.getKills());
        }

        if (args.endsWith("void_kills")) {
            return String.format("%d", stats.getVoidKills());
        }

        if (args.endsWith("bow_kills")) {
            return String.format("%d", stats.getBowKills());
        }

        if(args.endsWith("deaths")) {
            return String.format("%d", stats.getDeaths());
        }

        if(args.endsWith("ratio")) {
            if(stats.getDeaths() == 0) {
                return "0";
            }
            return String.format("%h", stats.getKills() / stats.getDeaths());
        }

        return null;
    }
}
