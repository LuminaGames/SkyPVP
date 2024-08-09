package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.scoreboard.Scoreboard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static lol.vedant.skypvp.SkyPVP.config;

public class PlayerLoginListener implements Listener {

    private static SkyPVP plugin = SkyPVP.getPlugin();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        Scoreboard scoreboard = new Scoreboard(player);
        Scoreboard.addPlayer(player, scoreboard);

        if(!plugin.getDb().hasStats(player.getUniqueId())) {
            plugin.getDb().createUser(player);
        }

        if(player.hasPermission("skypvp.commands.admin")) {
            if (!plugin.getGameManager().isSetup()) {
                player.sendMessage(ChatColor.RED + "Use /skypvp setup to get started...");
            }
        }

        if(plugin.getGameManager().isSetup()) {
            player.teleport(Utils.getLocation(config.getString(ConfigPath.ARENA_SPAWN)));
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Scoreboard.removePlayer(player);
    }

}
