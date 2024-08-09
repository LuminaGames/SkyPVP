package lol.vedant.skypvp.listener;

import com.cryptomorin.xseries.messages.Titles;
import lol.vedant.skypvp.api.events.SpawnEnterEvent;
import lol.vedant.skypvp.api.events.SpawnExitEvent;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class GameSpawnListener implements Listener {

    @EventHandler
    public void onPlayerSpawnLeave(SpawnExitEvent e) {
        Player player = e.getPlayer();
        Titles.sendTitle(player, Utils.cc("&cEntered PVP Zone"), "");
    }

    @EventHandler
    public void onPlayerSpawnEnter(SpawnEnterEvent e) {
        Player player = e.getPlayer();
        Titles.sendTitle(player, Utils.cc("&aEntered Spawn"), "");
    }

    //Turn off natural entity spawning
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        if(!(e.getEntity() instanceof Player)) {

            e.setCancelled(true);
        }
    }
}
