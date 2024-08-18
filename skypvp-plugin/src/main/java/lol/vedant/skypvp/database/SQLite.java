package lol.vedant.skypvp.database;

import lol.vedant.skypvp.SkyPVP;
import lol.vedant.skypvp.api.perks.PerkType;
import lol.vedant.skypvp.api.stats.KitStats;
import lol.vedant.skypvp.api.stats.PerkStats;
import lol.vedant.skypvp.api.stats.PlayerStats;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

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
                "uuid VARCHAR(255) PRIMARY KEY," +
                "bulldozer_perk BOOLEAN DEFAULT FALSE," +
                "exp_perk BOOLEAN DEFAULT FALSE," +
                "speed_perk BOOLEAN DEFAULT FALSE," +
                "active_perk VARCHAR(255))";
        String sql3 = "CREATE TABLE IF NOT EXISTS skypvp_kits (" +
                "uuid VARCHAR(255) PRIMARY KEY," +
                "warrior_kit BOOLEAN DEFAULT FALSE," +
                "archer_kit BOOLEAN DEFAULT FALSE," +
                "tank_kit BOOLEAN DEFAULT FALSE," +
                "assassin_kit BOOLEAN DEFAULT FALSE," +
                "pyro_kit BOOLEAN DEFAULT FALSE," +
                "sniper_kit BOOLEAN DEFAULT FALSE," +
                "vampire_kit BOOLEAN DEFAULT FALSE," +
                "knight_kit BOOLEAN DEFAULT FALSE," +
                "ninja_kit BOOLEAN DEFAULT FALSE," +
                "thor_kit BOOLEAN DEFAULT FALSE," +
                "healer_kit BOOLEAN DEFAULT FALSE);";
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
    public KitStats getKitStats(UUID player) {
        String sql = "SELECT * FROM skypvp_kits WHERE uuid = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            KitStats stats = new KitStats();
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                stats.setWarriorKit(rs.getBoolean("warrior_kit"));
                stats.setArcherKit(rs.getBoolean("archer_kit"));
                stats.setAssassinKit(rs.getBoolean("assassin_kit"));
                stats.setHealerKit(rs.getBoolean("healer_kit"));
                stats.setKnightKit(rs.getBoolean("knight_kit"));
                stats.setNinjaKit(rs.getBoolean("ninja_kit"));
                stats.setPyroKit(rs.getBoolean("pyro_kit"));
                stats.setSniperKit(rs.getBoolean("sniper_kit"));
                stats.setTankKit(rs.getBoolean("tank_kit"));
                stats.setThorKit(rs.getBoolean("thor_kit"));
                stats.setVampireKit(rs.getBoolean("vampire_kit"));
                return stats;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveKitStats(UUID player, KitStats stats) {
        String sql = "UPDATE skypvp_kits SET " +
                "warrior_kit = ?, " +
                "archer_kit = ?, " +
                "tank_kit = ?, " +
                "assassin_kit = ?, " +
                "pyro_kit = ?, " +
                "sniper_kit = ?, " +
                "vampire_kit = ?, " +
                "knight_kit = ?, " +
                "ninja_kit = ?, " +
                "thor_kit = ?, " +
                "healer_kit = ? " +
                "WHERE uuid = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setBoolean(1, stats.isWarriorKit());
            statement.setBoolean(2, stats.isArcherKit());
            statement.setBoolean(3, stats.isTankKit());
            statement.setBoolean(4, stats.isAssassinKit());
            statement.setBoolean(5, stats.isPyroKit());
            statement.setBoolean(6, stats.isSniperKit());
            statement.setBoolean(7, stats.isVampireKit());
            statement.setBoolean(8, stats.isKnightKit());
            statement.setBoolean(9, stats.isNinjaKit());
            statement.setBoolean(10, stats.isThorKit());
            statement.setBoolean(11, stats.isHealerKit());
            statement.setString(12, player.toString());

            statement.executeUpdate();
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
        String sql;
        if (perk.equals(PerkType.BULLDOZER)) {
            sql = "UPDATE skypvp_perks SET bulldozer_perk = 1 WHERE uuid = ?";
        } else if (perk.equals(PerkType.EXPERIENCE)) {
            sql = "UPDATE skypvp_perks SET exp_perk = 1 WHERE uuid = ?";
        } else if (perk.equals(PerkType.SPEED)) {
            sql = "UPDATE skypvp_perks SET speed_perk = 1 WHERE uuid = ?";
        } else {
            return;
        }

        try {
            checkConnection();

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, player.toString());
            ps.executeUpdate();
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
}
