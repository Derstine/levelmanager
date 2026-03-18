package me.derstine.levelmanager.services.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.plugin.java.JavaPlugin;

// chatgpt generated cause idk anything about sqlite w/ java
public class DatabaseManager {

    private final JavaPlugin plugin;
    private Connection connection;

    public DatabaseManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void connect() throws SQLException {
        File dbFile = new File(plugin.getDataFolder(), "levels.db");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
    }

    public Connection getConnection() {
        return connection;
    }
}