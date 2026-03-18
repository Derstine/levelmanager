package me.derstine.levelmanager.services.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


//chatgpt generated
public class TableLevels {

    private final Connection connection;

    public TableLevels(Connection connection) {
        this.connection = connection;
    }

    // Create table
    public void createTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS player_levels (
                uuid TEXT PRIMARY KEY,
                level INTEGER NOT NULL
            );
        """;

        connection.createStatement().execute(sql);
    }

    // Insert new player (default level = 1)
    public void insertNewPlayer(UUID uuid) throws SQLException {
        String sql = "INSERT OR IGNORE INTO player_levels (uuid, level) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());
            stmt.setInt(2, 1);
            stmt.executeUpdate();
        }
    }

    // Get player level
    public int getLevel(UUID uuid) throws SQLException {
        String sql = "SELECT level FROM player_levels WHERE uuid = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("level");
            }
        }

        return -1; // default if not found
    }

    // Set player level
    public void setLevel(UUID uuid, int level) throws SQLException {
        String sql = "UPDATE player_levels SET level = ? WHERE uuid = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, level);
            stmt.setString(2, uuid.toString());
            stmt.executeUpdate();
        }
    }
}