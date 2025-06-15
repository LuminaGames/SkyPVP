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
        config.addDefault(Message.HELP_MESSAGE_1, Arrays.asList(
                "&6&lSkyPVP Plugin Help - Commands",
                "",
                "&6/perks &7- &fTo view/buy perks",
                "&6/kit &7- &fBuy/Preview/Equip Kit from the kits menu",
                "&6/stats &7- &fView your SkyPVP stats",
                "&6/skypvp admin &7- &fView admin commands"
        ));

        config.addDefault(Message.HELP_MESSAGE_ADMIN_1, Arrays.asList(
                "&6&lSkyPVP Admin Help - Commands",
                "",
                "&6/skypvp setup &7- &fSetup the skypvp plugin",
                "&6/skypvp build &7- &fToggle build mode",
                "&6/skypvp setspawn &7- &fSet the spawn position for login and death",
                "&6/skypvp spawnarea &7- &fAdjust the spawn area"
        ));

        config.addDefault(Message.HELP_MESSAGE_ADMIN_2, Arrays.asList(
                "&6&lSkyPVP Admin Help - Page 2",
                "",
                "&6/skypvp setdisplayname <kit> <name> &7- &fSet display name of a kit",
                "&6/skypvp setprice <kit> <price> &7- &fSet the price of a kit"
        ));

        config.addDefault(Message.KILL_MESSAGE_FORMAT, "{prefix} &6{player} &fhas been killed by &c{killer}");
        config.addDefault(Message.KILL_MESSAGE_PROJECTILE, "{prefix} &6{player} &fwas shot by &c{killer}");
        config.addDefault(Message.KILL_MESSAGE_VOID, "{prefix} &6{player} &fwas thrown into the void by &c{killer}");

        config.addDefault(Message.PERK_BULLDOZER_TITLE, "&6&lBulldozer Perk");
        config.addDefault(Message.PERK_EXPERIENCE_TITLE, "&e&lExperience Perk");
        config.addDefault(Message.PERK_SPEED_TITLE, "&b&lSpeed Title");
        config.addDefault(Message.PERK_JUGGERNAUT_TITLE, "&d&lJuggernaut Perk");

        config.addDefault(Message.PERK_BULLDOZER_DESCRIPTION, Arrays.asList("&7On killing a player get", "&7Strength I effect for 3 seconds", "", "{perkStats}"));
        config.addDefault(Message.PERK_EXPERIENCE_DESCRIPTION, Arrays.asList("&7On killing a player get", "&7a level of experience level", "", "{perkStats}"));
        config.addDefault(Message.PERK_SPEED_DESCRIPTION, Arrays.asList("&7On killing a player get", "&7Speed II effect for 5 seconds", "", "{perkStats}"));
        config.addDefault(Message.PERK_JUGGERNAUT_DESCRIPTION, Arrays.asList("&7After entering the PvP zone", "&7and get Resistance I for 10 seconds", "", "{perkStats}"));

        config.addDefault(Message.PERK_ALREADY_ACTIVE, "&cThis perk is already active.");
        config.addDefault(Message.PERK_PURCHASED_SUCCESSFULLY, "&aThe perk was purchased successfully.");
        config.addDefault(Message.PERK_EQUIPPED, "&aPerk was equipped successfully");
        config.addDefault(Message.NOT_ENOUGH_BALANCE, "&cYou do not have enough balance to purchase this.");


        config.options().copyDefaults(true);
        save();
    }



}
