package lol.vedant.skypvp.config;

import lol.vedant.skypvp.api.config.Config;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class PluginConfig extends Config {

    public PluginConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);
        YamlConfiguration config = this.getYml();

        //Database
        config.addDefault("database.enabled", false);
        config.addDefault("database.host", "localhost");
        config.addDefault("database.port", 3306);
        config.addDefault("database.database", "database");

        //bStats
        config.addDefault("bstats.enabled", true);

        config.options().copyDefaults(true);
        save();
    }
}
