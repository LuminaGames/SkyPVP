package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.SkyPVP;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private static SkyPVP plugin = SkyPVP.getPlugin();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if(player.hasPermission("skypvp.commands.admin")) {
            if (!plugin.getGameManager().isSetup()) {
                player.sendMessage(ChatColor.RED + "Use /skypvp setup to get started...");
            }
        }
    }

}
