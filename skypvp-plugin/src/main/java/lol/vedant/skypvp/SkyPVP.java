package lol.vedant.skypvp;

import fr.mrmicky.fastinv.FastInvManager;
import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.kit.KitSerializer;
import lol.vedant.skypvp.api.utils.Utils;
import lol.vedant.skypvp.commands.SkyPVPCommand;
import lol.vedant.skypvp.commands.SkyPVPDeveloperCommands;
import lol.vedant.skypvp.commands.admin.AdminHelpCommand;
import lol.vedant.skypvp.commands.admin.BuildModeCommand;
import lol.vedant.skypvp.commands.admin.setup.*;
import lol.vedant.skypvp.commands.kit.admin.KitCreateCommand;
import lol.vedant.skypvp.commands.kit.KitCommand;
import lol.vedant.skypvp.commands.kit.KitPreviewCommand;
import lol.vedant.skypvp.commands.kit.admin.KitEditCommand;
import lol.vedant.skypvp.commands.kit.admin.KitIconCommand;
import lol.vedant.skypvp.commands.kit.admin.KitPriceCommand;
import lol.vedant.skypvp.commands.perks.PerkCommand;
import lol.vedant.skypvp.commands.stats.StatsCommand;
import lol.vedant.skypvp.config.MessageConfig;
import lol.vedant.skypvp.config.PluginConfig;
import lol.vedant.skypvp.database.Database;
import lol.vedant.skypvp.database.MySQL;
import lol.vedant.skypvp.database.SQLite;
import lol.vedant.skypvp.game.GameManager;
import lol.vedant.skypvp.hooks.SkyPVPExpansion;
import lol.vedant.skypvp.kit.KitManager;
import lol.vedant.skypvp.listener.*;
import lol.vedant.skypvp.metrics.Metrics;
import lol.vedant.skypvp.perks.PerkManager;
import lol.vedant.skypvp.scoreboard.Scoreboard;
import me.clip.placeholderapi.PlaceholderAPI;
import me.despical.commandframework.CommandFramework;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Date;

public final class SkyPVP extends JavaPlugin {


    private static SkyPVP instance;
    public static PluginConfig config;
    public static MessageConfig messages;
    private CommandFramework commandManager;
    private GameManager gameManager;
    private PerkManager perkManager;
    private KitSerializer kitSerializer;
    private KitManager kitManager;
    private Economy economy;
    private Database database;

    public static boolean PLACEHOLDER_API = false;
    public static long START_TIME;

    @Override
    public void onEnable() {
        instance = this;
        START_TIME = System.currentTimeMillis();

        getLogger().info("Loading configurations...");
        config = new PluginConfig(this, "config", getDataFolder().getAbsolutePath());
        messages = new MessageConfig(this, "messages", getDataFolder().getAbsolutePath());

        if(config.getBoolean(ConfigPath.ENABLE_BSTATS)) {
            getLogger().info("Hooked into bStats!");
            Metrics metrics = new Metrics(this, 22796);
        }

        commandManager = new CommandFramework(instance);

        getLogger().info("Trying to load arenas...");
        gameManager = new GameManager();
        gameManager.loadArena();

        if(config.getBoolean(ConfigPath.DB_ENABLED)) {
            getLogger().info("Using MySQL database for storage");
            database = new MySQL();
            database.init();
        } else {
            getLogger().info("Using SQLite database for storage");
            database = new SQLite();
            database.init();
        }

        kitSerializer = new KitSerializer(this);

        getLogger().info("Loading kits...");
        kitManager = new KitManager(this);
        kitManager.load();

        if(!setupEconomy()) {
            getLogger().severe("Plugin disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("Loading perks...");
        perkManager = new PerkManager();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PLACEHOLDER_API = true;
            this.getLogger().info("Hooked into PlaceholderAPI");
            new SkyPVPExpansion().register();
        }

        registerEvents(
                new PlayerLoginListener(),
                new PlayerSelectionListener(),
                new PlayerDamageListener(),
                new PlayerDeathListener(),
                new GameSpawnListener()
        );

        registerCommands(
                new PerkCommand(),
                new StatsCommand(),
                new SetSpawnCommand(),
                new SpawnAreaCommand(),
                new SetupCommand(),
                new BuildModeCommand(),
                new KitCreateCommand(),
                new KitPreviewCommand(this),
                new KitCommand(this),
                new KitCreateCommand(),
                new KitEditCommand(),
                new KitIconCommand(),
                new KitPriceCommand(),
                new SkyPVPCommand(),
                new AdminHelpCommand(),
                new SkyPVPDeveloperCommands()
        );

        Scoreboard.initialize();

        FastInvManager.register(this);

        setWeatherAndTime();

        long startingTime = new Date().getTime() - START_TIME;
        getLogger().info("Plugin was loaded in " + startingTime + "ms");
    }

    @Override
    public void onDisable() {
        getLogger().info("Thank you for using Sky PVP");
        getLogger().info("Closing database connections...");
        database.disable();
    }

    private void setWeatherAndTime() {
        getLogger().info("Loading worlds...");
        getServer().getWorlds().forEach((world) -> {

            if (Utils.getServerVersion() == 18) {
                // 1.8.8 logic
                world.setStorm(false);
                world.setThundering(false);
                world.setGameRuleValue("doDaylightCycle", "false");
                world.setGameRuleValue("doWeatherCycle", "false");
                world.setTime(1000);
            } else {
                world.setStorm(false);
                world.setThundering(false);
                world.setTime(1000); // Morning
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            }


        });

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static SkyPVP getPlugin() {
        return instance;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public static void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(l -> getPlugin().getServer().getPluginManager().registerEvents(l, getPlugin()));
    }

    public static void registerCommands(Object... commands) {
        Arrays.stream(commands).forEach(c -> getPlugin().commandManager.registerCommands(c));
    }

    public Economy getEconomy() {
        return economy;
    }

    public PerkManager getPerkManager() {
        return perkManager;
    }

    public Database getDb() {
        return database;
    }

    public KitSerializer getKitSerializer() {
        return kitSerializer;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public static String parsePlaceholder(Player player, String msg) {
        if(PLACEHOLDER_API) {
            return PlaceholderAPI.setPlaceholders(player, msg);
        }
        return msg;
    }
}


