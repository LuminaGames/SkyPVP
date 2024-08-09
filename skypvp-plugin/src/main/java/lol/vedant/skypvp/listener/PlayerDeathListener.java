package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.stats.PlayerStats;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private SkyPVP plugin = SkyPVP.getPlugin();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();
        e.setDeathMessage(null);

        // Instant Respawn
        player.spigot().respawn();

        // Check if killer is null
        if (killer == null) {
            // Handle death without a killer
            Bukkit.broadcastMessage(Utils.cc("&c" + player.getName() + " &edied."));
            PlayerStats playerStats = plugin.getDb().getStats(player.getUniqueId());
            playerStats.setDeaths(playerStats.getDeaths() + 1);
            plugin.getDb().saveStats(player.getUniqueId(), playerStats);
            return;
        }

        // Handle death with a killer
        PlayerStats killerStats = plugin.getDb().getStats(killer.getUniqueId());
        PlayerStats playerStats = plugin.getDb().getStats(player.getUniqueId());

        // Increment killer's kills based on damage cause
        if (player.getLastDamageCause() != null) {
            EntityDamageEvent.DamageCause cause = player.getLastDamageCause().getCause();
            switch (cause) {
                case VOID:
                    killerStats.setKills(killerStats.getKills() + 1);
                    killerStats.setVoidKills(killerStats.getVoidKills() + 1);
                    Bukkit.broadcastMessage(Utils.cc("&c" + player.getName() + " &ehas been thrown into the void by &a" + killer.getName()));
                    break;
                case PROJECTILE:
                    killerStats.setKills(killerStats.getKills() + 1);
                    killerStats.setBowKills(killerStats.getBowKills() + 1);
                    Bukkit.broadcastMessage(Utils.cc("&c" + player.getName() + " &ehas been shot by &a" + killer.getName()));
                    break;
                default:
                    killerStats.setKills(killerStats.getKills() + 1);
                    Bukkit.broadcastMessage(Utils.cc("&c" + player.getName() + " &ehas been killed by &a" + killer.getName()));
                    break;
            }
        }

        // Save stats for the killer
        plugin.getDb().saveStats(killer.getUniqueId(), killerStats);

        // Increment and save death stats for the player
        playerStats.setDeaths(playerStats.getDeaths() + 1);
        plugin.getDb().saveStats(player.getUniqueId(), playerStats);
    }
}
