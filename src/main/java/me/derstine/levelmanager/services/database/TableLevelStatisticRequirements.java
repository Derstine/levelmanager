package me.derstine.levelmanager.services.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

// chatgpt code
public class TableLevelStatisticRequirements {

    private final Connection connection;

    public TableLevelStatisticRequirements(Connection connection) {
        this.connection = connection;
    }

    public void createTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS level_statistic_requirements (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                uuid TEXT NOT NULL,
                stat_key TEXT NOT NULL,
                base_value INTEGER NOT NULL,
                FOREIGN KEY (uuid) REFERENCES player_levels(uuid) ON DELETE CASCADE,
                UNIQUE (uuid, stat_key)
            );
        """;

        connection.createStatement().execute(sql);
    }

    public void insert(UUID uuid, String statKey, int baseValue) throws SQLException {
        String sql = """
            INSERT OR IGNORE INTO level_statistic_requirements (uuid, stat_key, base_value)
            VALUES (?, ?, ?)
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.setString(2, statKey);
            stmt.setInt(3, baseValue);
            stmt.executeUpdate();
        }
    }

    public int getBaseValue(UUID uuid, String statKey) throws SQLException {
        String sql = """
            SELECT base_value
            FROM level_statistic_requirements
            WHERE uuid = ? AND stat_key = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.setString(2, statKey);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("base_value");
            }
        }

        return 0;
    }

    public void setBaseValue(UUID uuid, String statKey, int baseValue) throws SQLException {
        String sql = """
            UPDATE level_statistic_requirements
            SET base_value = ?
            WHERE uuid = ? AND stat_key = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, baseValue);
            stmt.setString(2, uuid.toString());
            stmt.setString(3, statKey);
            stmt.executeUpdate();
        }
    }

    public void addToBaseValue(UUID uuid, String statKey, int amount) throws SQLException {
        String sql = """
            UPDATE level_statistic_requirements
            SET base_value = base_value + ?
            WHERE uuid = ? AND stat_key = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, amount);
            stmt.setString(2, uuid.toString());
            stmt.setString(3, statKey);
            stmt.executeUpdate();
        }
    }

    public void delete(UUID uuid, String statKey) throws SQLException {
        String sql = """
            DELETE FROM level_statistic_requirements
            WHERE uuid = ? AND stat_key = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.setString(2, statKey);
            stmt.executeUpdate();
        }
    }

    public void deleteAll(UUID uuid) throws SQLException {
        String sql = """
            DELETE FROM level_statistic_requirements
            WHERE uuid = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.executeUpdate();
        }
    }

    public boolean exists(UUID uuid, String statKey) throws SQLException {
        String sql = """
            SELECT 1
            FROM level_statistic_requirements
            WHERE uuid = ? AND stat_key = ?
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.setString(2, statKey);

            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}