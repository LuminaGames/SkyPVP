package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static lol.vedant.skypvp.SkyPVP.config;

public class SelectionListener implements Listener {

    Location pos1;
    Location pos2;

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if(player.getItemInHand() != null && player.getItemInHand().getItemMeta().hasDisplayName() && player.getItemInHand().getItemMeta().getDisplayName().equals(Utils.cc("&a&lSelect Wand"))) {
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                Block block = e.getClickedBlock();
                pos1 = block.getLocation();
                player.sendMessage("Position 1 Set");
            }

            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                Block block = e.getClickedBlock();
                pos2 = block.getLocation();
                player.sendMessage("Position 2 Set");
            }

            if(pos1 != null && pos2 != null) {
                config.set(ConfigPath.ARENA_MIN_SPAWN, Utils.parseLocation(pos1));
                config.set(ConfigPath.ARENA_MAX_SPAWN, Utils.parseLocation(pos2));
                player.sendMessage(Utils.cc("&aSpawn area positions set!"));
            }
        }
    }




}
