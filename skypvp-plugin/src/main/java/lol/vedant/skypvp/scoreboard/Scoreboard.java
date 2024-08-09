package lol.vedant.skypvp.scoreboard;

import fr.mrmicky.fastboard.FastBoard;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

import static lol.vedant.skypvp.SkyPVP.config;

public class Scoreboard extends FastBoard {

    private static SkyPVP plugin = SkyPVP.getPlugin();
    private int taskId;
    private static Map<Player, Scoreboard> scoreboards = new HashMap<>();

    public Scoreboard(Player player) {
        super(player);
        showScoreboard(player);
    }

    private void showScoreboard(Player player) {
        updateTitle(Utils.cc(config.getString(ConfigPath.SCOREBOARD_TITLE)));
        updateLines(config.getList(ConfigPath.SCOREBOARD_VALUES));
        startUpdating(player);
    }

    private void startUpdating(Player player) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    updateTitle(Utils.cc(config.getString(ConfigPath.SCOREBOARD_TITLE)));
                    updateLines(config.getList(ConfigPath.SCOREBOARD_VALUES));
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // Update every second
        this.taskId = task.getTaskId();
    }

    public static void addPlayer(Player player, Scoreboard scoreboard) {
        scoreboards.put(player, scoreboard);
    }

    public static void removePlayer(Player player) {
        if (scoreboards.containsKey(player)) {
            Scoreboard scoreboard = scoreboards.get(player);
            Bukkit.getScheduler().cancelTask(scoreboard.taskId);
            scoreboards.remove(player);
        }
    }

    public static void initialize() {
        // Register the event listener

        // Initialize scoreboards for currently online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = new Scoreboard(player);
            addPlayer(player, scoreboard);
        }
    }
}
