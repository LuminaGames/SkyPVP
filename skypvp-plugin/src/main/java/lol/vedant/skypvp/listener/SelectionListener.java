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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static lol.vedant.skypvp.SkyPVP.config;

public class SelectionListener implements Listener {

    private Location pos1;
    private Location pos2;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        // Check if the player is holding an item
        if (itemInHand == null || !itemInHand.hasItemMeta()) {
            return;
        }

        ItemMeta meta = itemInHand.getItemMeta();
        if (meta.hasDisplayName() && meta.getDisplayName().equals(Utils.cc("&a&lSelect Wand"))) {
            Action action = e.getAction();
            Block block = e.getClickedBlock();

            if (action == Action.RIGHT_CLICK_BLOCK && block != null) {
                pos1 = block.getLocation();
                player.sendMessage(Utils.cc("&aPosition 1 Set"));
            } else if (action == Action.LEFT_CLICK_BLOCK && block != null) {
                pos2 = block.getLocation();
                player.sendMessage(Utils.cc("&aPosition 2 Set"));
            }

            if (pos1 != null && pos2 != null) {
                config.set(ConfigPath.ARENA_MIN_SPAWN, Utils.parseLocation(pos1));
                config.set(ConfigPath.ARENA_MAX_SPAWN, Utils.parseLocation(pos2));
                player.sendMessage(Utils.cc("&aSpawn area positions set!"));
            }

            e.setCancelled(true);
        }
    }
}
