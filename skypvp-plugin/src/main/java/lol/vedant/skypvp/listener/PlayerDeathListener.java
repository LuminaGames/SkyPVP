package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.api.events.PlayerKillEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();
        e.setDeathMessage(null);

        //Instant Respawn
        player.spigot().respawn();

        if(killer instanceof Player) {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerKillEvent(player, killer));
        }

    }

}
