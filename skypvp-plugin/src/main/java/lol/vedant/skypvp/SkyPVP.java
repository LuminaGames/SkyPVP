package lol.vedant.skypvp;

import fr.mrmicky.fastinv.FastInvManager;
import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.commands.perks.PerkCommand;
import lol.vedant.skypvp.commands.setup.SetSpawnCommand;
import lol.vedant.skypvp.commands.setup.SetupCommand;
import lol.vedant.skypvp.commands.setup.SpawnAreaCommand;
import lol.vedant.skypvp.config.MessageConfig;
import lol.vedant.skypvp.config.PluginConfig;
import lol.vedant.skypvp.database.Database;
import lol.vedant.skypvp.database.MySQL;
import lol.vedant.skypvp.database.SQLite;
import lol.vedant.skypvp.game.GameManager;
import lol.vedant.skypvp.listener.*;
import lol.vedant.skypvp.metrics.Metrics;
import lol.vedant.skypvp.perks.PerkManager;
import lol.vedant.skypvp.scoreboard.Scoreboard;
import me.despical.commandframework.CommandFramework;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class SkyPVP extends JavaPlugin {

    private static SkyPVP instance;
    public static PluginConfig config;
    public static MessageConfig messages;
    private CommandFramework commandManager;
    private GameManager gameManager;
    private PerkManager perkManager;
    private Economy economy;
    private Database database;

    @Override
    public void onEnable() {
        instance = this;

        config = new PluginConfig(this, "config", getDataFolder().getAbsolutePath());
        messages = new MessageConfig(this, "messages", getDataFolder().getAbsolutePath());

        if(config.getBoolean(ConfigPath.ENABLE_BSTATS)) {
            Metrics metrics = new Metrics(this, 22796);
        }

        commandManager = new CommandFramework(instance);

        gameManager = new GameManager();
        gameManager.loadArena();

        if(config.getBoolean(ConfigPath.DB_ENABLED)) {
            database = new MySQL();
            database.init();
        } else {
            database = new SQLite();
            database.init();
        }


        if(!setupEconomy()) {
            getLogger().severe("Plugin disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        perkManager = new PerkManager();

        registerEvents(
                new PlayerLoginListener(),
                new PlayerSelectionListener(),
                new PlayerDamageListener(),
                new PlayerDeathListener(),
                new GameSpawnListener()
        );

        registerCommands(
                new SetSpawnCommand(),
                new SpawnAreaCommand(),
                new SetupCommand(),
                new PerkCommand()
        );

        Scoreboard.initialize();

        FastInvManager.register(this);

    }

    @Override
    public void onDisable() {

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
}


