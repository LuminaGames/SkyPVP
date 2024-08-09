package lol.vedant.skypvp.config;

import lol.vedant.skypvp.api.config.Config;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class PluginConfig extends Config {

    public PluginConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration config = this.getYml();

        //Database
        config.addDefault("database.enabled", false);
        config.addDefault("database.host", "localhost");
        config.addDefault("database.port", 3306);
        config.addDefault("database.database", "database");
        config.addDefault("database.password", "password");
        config.addDefault("database.ssl", false);
        config.addDefault("database.verify-certificate", true);
        config.addDefault("database.max-pool", 10);
        config.addDefault("database.max-lifetime", 1000);

        //bStats
        config.addDefault("bstats.enabled", true);

        //Scoreboard
        config.addDefault("scoreboard.title", "&6&lSkyPVP");
        config.addDefault("scoreboard.values", Arrays.asList(
                "",
                " &6&lStats",
                "&fKills: &6%skypvp_kills%",
                "&fDeaths: &6%skypvp_deaths%",
                "&fRatio: &6%skypvp_ratio%",
                "",
                "&fOnline: &6%server_online%",
                "",
                "&6server.com"
        ));

        config.addDefault("perks.bulldozer.price", 10000);
        config.addDefault("perks.speed.price", 15000);
        config.addDefault("perks.experience.price", 7500);

        config.options().copyDefaults(true);
        save();
    }
}
