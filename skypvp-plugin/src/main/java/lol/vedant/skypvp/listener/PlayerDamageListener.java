package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.game.SpawnManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) e.getEntity();

        //Instant void kill
        if(e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
            player.setHealth(0);
        }

        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            e.setCancelled(true);
        }

        if(SpawnManager.isInSpawn(player)) {
            e.setCancelled(true);
        }



    }

}
