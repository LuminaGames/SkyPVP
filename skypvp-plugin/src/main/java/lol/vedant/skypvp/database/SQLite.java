package lol.vedant.skypvp.database;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.stats.PerkStats;
import lol.vedant.skypvp.api.stats.PlayerStats;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ALL")
public class SQLite implements Database {

    SkyPVP plugin = SkyPVP.getPlugin();
    private Connection connection;
    private final String url;

    public SQLite() {
        File file = new File(plugin.getDataFolder(), "data.db");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        url = "jdbc:sqlite:" + file.getAbsolutePath();

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        createTables();
    }

    @Override
    public void createUser(Player player) {
        String sql = "INSERT INTO skypvp_stats (uuid, name) VALUES (?, ?)";
        String sql2 = "INSERT INTO skypvp_perks (uuid) VALUES (?)";

        try {
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
                "uuid VARCHAR(36) PRIMARY KEY," +
                "unlocked_perks TEXT DEFAULT '[]'," +
                "active_perk VARCHAR(100))";
        String sql3 = "CREATE TABLE IF NOT EXISTS skypvp_kits (" +
                "uuid VARCHAR(255) PRIMARY KEY," +
                "unlocked_kits LONGTEXT);";
        try (Connection connection = DriverManager.getConnection(url);
             Statement stmt = connection.createStatement()) {
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
        try {
            checkConnection();

            PreparedStatement pstmt = connection.prepareStatement(sql);
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
        String sql = "SELECT kills, deaths, void_kills, bow_kills, experience FROM skypvp_stats WHERE uuid = ?";
        try {
            checkConnection();

            PreparedStatement pstmt = connection.prepareStatement(sql);
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
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, player.toString());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String json = rs.getString("unlocked_kits");
                    if (json == null || json.isEmpty()) {
                        return new ArrayList<>();  // return empty list if no kits stored
                    }
                    return new Gson().fromJson(json, new TypeToken<List<String>>() {}.getType());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @Override
    public void saveKitStats(UUID player, String kitId) {
        String sql1 = "SELECT * FROM skypvp_kits WHERE uuid = ?";
        String sql2 = "INSERT INTO skypvp_kits (uuid, unlocked_kits) VALUES (?, ?)";
        String sql3 = "UPDATE skypvp_kits SET unlocked_kits = ? WHERE uuid = ?";

        Gson gson = new Gson();
        try {
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
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, stats.getKills());
            ps.setInt(2, stats.getDeaths());
            ps.setInt(3, stats.getVoidKills());
            ps.setInt(4, stats.getBowKills());
            ps.setLong(5, stats.getExp());
            ps.setString(6, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PerkType getActivePerk(UUID player) {
        String sql = "SELECT active_perk FROM skypvp_perks WHERE uuid = ?";

        try{
            checkConnection();

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String perk = rs.getString("active_perk");
                System.out.println("Retrieved perk: " + perk);
                if (perk == null || perk.isEmpty()) {
                    return PerkType.NONE;
                }
                return PerkType.valueOf(perk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return PerkType.NONE;
    }

    @Override
    public void setActivePerk(UUID player, PerkType perk) {
        String sql = "UPDATE skypvp_perks SET active_perk = ? WHERE uuid = ?";

        try {
            checkConnection();

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
        String sql1 = "SELECT * FROM skypvp_perks WHERE uuid = ?";
        String sql2 = "INSERT INTO skypvp_perks (uuid, unlocked_perks) VALUES (?, ?)";
        String sql3 = "UPDATE skypvp_perks SET unlocked_perks = ? WHERE uuid = ?";

        Gson gson = new Gson();
        try {
            PreparedStatement selectPs = connection.prepareStatement(sql1);
            selectPs.setString(1, player.toString());
            ResultSet rs = selectPs.executeQuery();

            JsonArray unlockedKitsJson;
            if (rs.next()) {
                // Retrieve existing unlocked kits
                String unlockedKitsString = rs.getString("unlocked_perks");
                unlockedKitsJson = gson.fromJson(unlockedKitsString, JsonArray.class);
                if (!unlockedKitsJson.contains(gson.toJsonTree(perk.toString()))) {
                    unlockedKitsJson.add(perk.toString());
                }
                // Update existing entry
                PreparedStatement updatePs = connection.prepareStatement(sql3);
                updatePs.setString(1, gson.toJson(unlockedKitsJson));
                updatePs.setString(2, player.toString());
                updatePs.executeUpdate();
            } else {
                // Create new entry
                unlockedKitsJson = new JsonArray();
                unlockedKitsJson.add(perk.toString());
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
    public PerkStats getPerks(UUID player) {
        String sql = "SELECT * FROM skypvp_perks WHERE uuid = ?";

        try {
            checkConnection();

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Gson gson = new Gson();
                List<String> unlockedPerks = new ArrayList<>();

                String rawJson = rs.getString("unlocked_perks");
                JsonElement jelem = gson.fromJson(rawJson, JsonElement.class);

                if (jelem != null && jelem.isJsonArray()) {
                    jelem.getAsJsonArray().forEach(k -> unlockedPerks.add(k.getAsString()));
                }

                return new PerkStats(unlockedPerks);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void checkConnection() throws SQLException {
        boolean renew = false;

        if (this.connection == null)
            renew = true;
        else
        if (this.connection.isClosed())
            renew = true;

        if (renew)
            this.connection = DriverManager.getConnection(url);
    }

    @Override
    public void disable() {
        try {
            checkConnection();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
