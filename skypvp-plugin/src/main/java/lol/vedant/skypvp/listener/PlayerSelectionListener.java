package lol.vedant.skypvp.listener;

import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.events.SpawnEnterEvent;
import lol.vedant.skypvp.api.events.SpawnExitEvent;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.Set;

import static lol.vedant.skypvp.SkyPVP.config;

public class PlayerSelectionListener implements Listener {

    public static final Set<Player> playersInSpawn = new HashSet<>();

    private Location pos1;
    private Location pos2;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();

        // Check if the player actually moved to a different block
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        if (!config.getYml().contains(ConfigPath.ARENA_MAX_SPAWN) || !config.getYml().contains(ConfigPath.ARENA_MIN_SPAWN)) {
            return;
        }

        Location spawnMin = Utils.getLocation(config.getString(ConfigPath.ARENA_MIN_SPAWN));
        Location spawnMax = Utils.getLocation(config.getString(ConfigPath.ARENA_MAX_SPAWN));

        boolean isInSpawnNow = isInSpawn(to, spawnMin, spawnMax);
        boolean wasInSpawnBefore = playersInSpawn.contains(player);

        if (isInSpawnNow && !wasInSpawnBefore) {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnEnterEvent(player));
            playersInSpawn.add(player);
        } else if (!isInSpawnNow && wasInSpawnBefore) {
            Bukkit.getServer().getPluginManager().callEvent(new SpawnExitEvent(player));
            playersInSpawn.remove(player);
        }
    }

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

    public boolean isInSpawn(Location location, Location minLocation, Location maxLocation) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        double minX = Math.min(minLocation.getX(), maxLocation.getX());
        double minY = Math.min(minLocation.getY(), maxLocation.getY());
        double minZ = Math.min(minLocation.getZ(), maxLocation.getZ());
        double maxX = Math.max(minLocation.getX(), maxLocation.getX());
        double maxY = Math.max(minLocation.getY(), maxLocation.getY());
        double maxZ = Math.max(minLocation.getZ(), maxLocation.getZ());

        boolean isInX = x >= minX && x <= maxX;
        boolean isInY = y >= minY && y <= maxY;
        boolean isInZ = z >= minZ && z <= maxZ;
        return isInX && isInY && isInZ;
    }
}
