package me.derstine.levelmanager.services.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

// chatgpt generated cause idk anything about sqlite w/ java
public class DatabaseManager {

    private final JavaPlugin plugin;
    private Connection connection;

    private TableLevels tableLevels;
    private TableLevelStatsReq tableLevelStatsReq;

    public DatabaseManager(JavaPlugin plugin) throws SQLException {
        this.plugin = plugin;

        connect();

        tableLevels = new TableLevels(connection);
        tableLevels.createTable();

        tableLevelStatsReq = new TableLevelStatsReq(connection);
        tableLevelStatsReq.createTable();


    }

    private void connect() throws SQLException {
        File dbFile = new File(plugin.getDataFolder(), "levels.db");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        connection.createStatement().execute("PRAGMA foreign_keys = ON;");
    }

    // use this function to set the player level cause it removes all entries in level stats table
    public void setPlayerLevel(UUID playerId, int newLevel) throws SQLException {

        String updateLevelSql = "UPDATE player_levels SET level = ? WHERE uuid = ?";
        String deleteStatsSql = "DELETE FROM level_statistic_requirements WHERE uuid = ?";

        try {
            connection.setAutoCommit(false);

            try (
                    PreparedStatement updateLevel = connection.prepareStatement(updateLevelSql);
                    PreparedStatement deleteStats = connection.prepareStatement(deleteStatsSql)
            ) {
                // 1. update level
                updateLevel.setInt(1, newLevel);
                updateLevel.setString(2, playerId.toString());
                updateLevel.executeUpdate();

                // 2. delete level statistics
                deleteStats.setString(1, playerId.toString());
                deleteStats.executeUpdate();

                // both worked
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void insertNewPlayer(UUID uuid) throws SQLException {
        tableLevels.insertNewPlayer(uuid);
    }
    public int getPlayerLevel(UUID uuid) throws SQLException {
        return tableLevels.getLevel(uuid);
    }

    public void insertLevelStatReq(UUID uuid, String statKey, int baseValue) throws SQLException {
        tableLevelStatsReq.insert(uuid, statKey, baseValue);
    }

    public boolean levelStatReqExists(UUID uuid, String statKey) throws SQLException {
        return tableLevelStatsReq.exists(uuid, statKey);
    }

    public int getLevelStatReq(UUID uuid, String statKey) throws SQLException {
        return tableLevelStatsReq.getBaseValue(uuid, statKey);
    }
}