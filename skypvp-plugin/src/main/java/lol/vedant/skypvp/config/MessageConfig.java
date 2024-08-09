package lol.vedant.skypvp.config;

import lol.vedant.skypvp.api.config.Config;
import lol.vedant.skypvp.api.config.Message;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MessageConfig extends Config {

    public MessageConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);

        YamlConfiguration config = this.getYml();
        config.addDefault(Message.KILL_MESSAGE_FORMAT, "{prefix} &6{player} &fhas been killed by &c{killer}");
        config.addDefault(Message.KILL_MESSAGE_PROJECTILE, "{prefix} &6{player} &fwas shot by &c{killer}");
        config.addDefault(Message.KILL_MESSAGE_VOID, "{prefix} &6{player} &fwas thrown into the void by &c{killer}");

        config.options().copyDefaults(true);
        save();
    }



}
