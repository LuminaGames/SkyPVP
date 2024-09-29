package lol.vedant.skypvp.config;

import lol.vedant.skypvp.api.config.Config;
import lol.vedant.skypvp.api.config.Message;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class MessageConfig extends Config {

    public MessageConfig(Plugin plugin, String name, String dir) {
        super(plugin, name, dir);

        YamlConfiguration config = this.getYml();
        config.addDefault(Message.KILL_MESSAGE_FORMAT, "{prefix} &6{player} &fhas been killed by &c{killer}");
        config.addDefault(Message.KILL_MESSAGE_PROJECTILE, "{prefix} &6{player} &fwas shot by &c{killer}");
        config.addDefault(Message.KILL_MESSAGE_VOID, "{prefix} &6{player} &fwas thrown into the void by &c{killer}");

        config.addDefault(Message.PERK_BULLDOZER_TITLE, "&6&lBulldozer Perk");
        config.addDefault(Message.PERK_EXPERIENCE_TITLE, "&e&lExperience Perk");
        config.addDefault(Message.PERK_SPEED_TITLE, "&b&lSpeed Title");

        config.addDefault(Message.PERK_BULLDOZER_DESCRIPTION, Arrays.asList("&7On killing a player get", "&7Strength I effect for 3 seconds", "", "{perkStats}"));
        config.addDefault(Message.PERK_EXPERIENCE_DESCRIPTION, Arrays.asList("&7On killing a player get", "&7a level of experience level", "", "{perkStats}"));
        config.addDefault(Message.PERK_SPEED_DESCRIPTION, Arrays.asList("&7On killing a player get", "&7Speed II effect for 5 seconds", "", "{perkStats}"));

        config.addDefault(Message.PERK_ALREADY_ACTIVE, "&cThis perk is already active.");
        config.addDefault(Message.PERK_PURCHASED_SUCCESSFULLY, "&aThe perk was purchased successfully.");
        config.addDefault(Message.PERK_EQUIPPED, "&aPerk was equipped successfully");
        config.addDefault(Message.NOT_ENOUGH_BALANCE, "&cYou do not have enough balance to purchase this.");

        config.options().copyDefaults(true);
        save();
    }



}
