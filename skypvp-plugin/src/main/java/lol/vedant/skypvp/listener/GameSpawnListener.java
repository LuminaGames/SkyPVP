package lol.vedant.skypvp.listener;

import com.cryptomorin.xseries.messages.Titles;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.events.SpawnEnterEvent;
import lol.vedant.skypvp.api.events.SpawnExitEvent;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class GameSpawnListener implements Listener {

    SkyPVP plugin = SkyPVP.getPlugin();

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

    //Disable hunger
    @EventHandler
    public void foodChangeEvent(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    //Disable item drop
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    //Turn off natural entity spawning
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Player)) {

            e.setCancelled(true);
        }
    }

    //Disable build
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!plugin.getGameManager().hasBuildMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent e) {
        if(!plugin.getGameManager().hasBuildMode(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
