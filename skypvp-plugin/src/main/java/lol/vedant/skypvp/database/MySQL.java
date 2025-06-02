package lol.vedant.skypvp.database;

import com.google.gson.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.config.ConfigPath;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.stats.KitStats;
import lol.vedant.skypvp.api.stats.PerkStats;
import lol.vedant.skypvp.api.stats.PlayerStats;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static lol.vedant.skypvp.SkyPVP.config;

public class MySQL implements Database {

    private HikariDataSource dataSource;
    private final String host;
    private final String database;
    private final String user;
    private final String pass;
    private final int port;
    private final boolean ssl;
    private final boolean certificateVerification;
    private final int poolSize;
    private final int maxLifetime;
    private SkyPVP plugin = SkyPVP.getPlugin();

    public MySQL() {
        this.host = config.getString(ConfigPath.DB_HOSTNAME);
        this.database = config.getString(ConfigPath.DB_DATABASE);
        this.user = config.getString(ConfigPath.DB_USERNAME);
        this.pass = config.getString(ConfigPath.DB_PASSWORD);
        this.port = config.getInt(ConfigPath.DB_PORT);
        this.ssl = config.getBoolean(ConfigPath.DB_SSL);
        this.certificateVerification = config.getYml().getBoolean(ConfigPath.DB_VERIFY_CERTIFICATE, true);
        this.poolSize = config.getYml().getInt(ConfigPath.DB_MAX_POOL, 10);
        this.maxLifetime = config.getYml().getInt(ConfigPath.DB_MAX_LIFETIME, 1800);
    }

    @Override
    public void init() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("Delivery-pool");
        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setMaxLifetime(maxLifetime);
        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pass);
        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(ssl));

        if (!certificateVerification) {
            hikariConfig.addDataSourceProperty("verifyServerCertificate", String.valueOf(false));
        }

        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("encoding", "UTF-8");
        hikariConfig.addDataSourceProperty("useUnicode", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("jdbcCompliantTruncation", "false");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "275");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));

        dataSource = new HikariDataSource(hikariConfig);

        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTables();


    }

    @Override
    public void createUser(Player player) {
        String sql = "INSERT INTO skypvp_stats (uuid, name) VALUES (?, ?)";
        String sql2 = "INSERT INTO skypvp_perks (uuid) VALUES (?)";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, player.getName());
            ps.executeUpdate();

            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setString(1, player.getUniqueId().toString());
            ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS skypvp_stats (" +
                "uuid VARCHAR(255) PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "kills INT DEFAULT 0," +
                "deaths INT DEFAULT 0," +
                "void_kills INT DEFAULT 0," +
                "bow_kills INT DEFAULT 0," +
                "experience INT DEFAULT 0" +
                ")";
        String sql2 = "CREATE TABLE IF NOT EXISTS skypvp_perks (" +
                "uuid VARCHAR(255) PRIMARY KEY," +
                "bulldozer_perk BOOLEAN DEFAULT FALSE," +
                "exp_perk BOOLEAN DEFAULT FALSE," +
                "speed_perk BOOLEAN DEFAULT FALSE," +
                "active_perk VARCHAR(255))";
        String sql3 = "CREATE TABLE IF NOT EXISTS skypvp_kits (" +
                "uuid VARCHAR(255) PRIMARY KEY," +
                "unlocked_kits LONGTEXT);";
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.execute(sql3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean hasStats(UUID player) {
        String sql = "SELECT COUNT(*) FROM skypvp_stats WHERE uuid = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public PlayerStats getStats(UUID player) {
        String sql = "SELECT kills, deaths, void_kills, bow_kills FROM skypvp_stats WHERE uuid = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                PlayerStats stats = new PlayerStats();
                stats.setKills(rs.getInt("kills"));
                stats.setDeaths(rs.getInt("deaths"));
                stats.setVoidKills(rs.getInt("void_kills"));
                stats.setBowKills(rs.getInt("bow_kills"));
                stats.setExp(rs.getLong("experience"));
                return stats;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getKitStats(UUID player) {
        String sql = "SELECT * FROM skypvp_kits WHERE uuid = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            KitStats stats = new KitStats();
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Gson gson = new Gson();
                List<String> unlockedKits = new ArrayList<>();
                JsonElement jelem = gson.fromJson(rs.getString("unlocked_kits"), JsonElement.class);
                jelem.getAsJsonObject().getAsJsonArray().forEach(k -> unlockedKits.add(k.getAsString()));

                return unlockedKits;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveKitStats(UUID player, String kitId) {
        String sql1 = "SELECT * FROM skypvp_kits WHERE uuid = ?";
        String sql2 = "INSERT INTO skypvp_kits (uuid, unlocked_kits) VALUES (?, ?)";
        String sql3 = "UPDATE skypvp_kits SET unlocked_kits = ? WHERE uuid = ?";

        Gson gson = new Gson();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement selectPs = connection.prepareStatement(sql1);
            selectPs.setString(1, player.toString());
            ResultSet rs = selectPs.executeQuery();

            JsonArray unlockedKitsJson;
            if (rs.next()) {
                // Retrieve existing unlocked kits
                String unlockedKitsString = rs.getString("unlocked_kits");
                unlockedKitsJson = gson.fromJson(unlockedKitsString, JsonArray.class);
                if (!unlockedKitsJson.contains(gson.toJsonTree(kitId))) {
                    unlockedKitsJson.add(kitId);
                }
                // Update existing entry
                PreparedStatement updatePs = connection.prepareStatement(sql3);
                updatePs.setString(1, gson.toJson(unlockedKitsJson));
                updatePs.setString(2, player.toString());
                updatePs.executeUpdate();
            } else {
                // Create new entry
                unlockedKitsJson = new JsonArray();
                unlockedKitsJson.add(kitId);
                PreparedStatement insertPs = connection.prepareStatement(sql2);
                insertPs.setString(1, player.toString());
                insertPs.setString(2, gson.toJson(unlockedKitsJson));
                insertPs.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveStats(UUID player, PlayerStats stats) {
        String sql = "UPDATE skypvp_stats SET kills = ?, deaths = ?, void_kills = ?, bow_kills = ?, experience = ? WHERE uuid = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, stats.getKills());
            ps.setInt(2, stats.getDeaths());
            ps.setInt(3, stats.getDeaths());
            ps.setInt(4, stats.getVoidKills());
            ps.setInt(5, stats.getBowKills());
            ps.setLong(6, stats.getExp());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public PerkType getActivePerk(UUID player) {
        String sql = "SELECT active_perk FROM skypvp_perks WHERE uuid = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                String perk = rs.getString("active_perk");
                if (perk == null || perk.isEmpty()) {
                    return PerkType.NONE;
                }
                return PerkType.valueOf(perk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setActivePerk(UUID player, PerkType perk) {
        String sql = "UPDATE skypvp_perks SET active_perk = ? WHERE uuid = ?";

        try(Connection connection = dataSource.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, perk.toString());
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPerk(UUID player, PerkType perk) {
        String sql;
        if (perk.equals(PerkType.BULLDOZER)) {
            sql = "UPDATE skypvp_perks SET bulldozer_perk = TRUE WHERE uuid = ?";
        } else if (perk.equals(PerkType.EXPERIENCE)) {
            sql = "UPDATE skypvp_perks SET exp_perk = TRUE WHERE uuid = ?";
        } else if (perk.equals(PerkType.SPEED)) {
            sql = "UPDATE skypvp_perks SET speed_perk = TRUE WHERE uuid = ?";
        } else {
            return;
        }

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            ps.executeUpdate();
        } catch (SQLException e ) {
            e.printStackTrace();
        }

    }

    @Override
    public PerkStats getPerks(UUID player) {
        String sql = "SELECT * FROM skypvp_perks WHERE uuid = ?";

        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return new PerkStats(
                        rs.getBoolean("bulldozer_perk"),
                        rs.getBoolean("speed_perk"),
                        rs.getBoolean("exp_perk")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void disable() {
        try(Connection connection = dataSource.getConnection()) {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
