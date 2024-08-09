package lol.vedant.skypvp.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static lol.vedant.skypvp.listener.PlayerSelectionListener.playersInSpawn;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();

        // Handle void damage
        if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            player.setHealth(0);
            player.spigot().respawn();
            e.setCancelled(true);
            return;
        }

        // Handle fall damage
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            e.setCancelled(true);
            return;
        }

        // Handle players in spawn
        if (playersInSpawn.contains(player)) {
            e.setCancelled(true);
        }
    }
}
