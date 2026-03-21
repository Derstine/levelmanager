package me.derstine.levelmanager;

import me.derstine.levelmanager.config.Config;
import me.derstine.levelmanager.controllers.LevelCommand;
import me.derstine.levelmanager.services.database.DatabaseManager;
import me.derstine.levelmanager.services.database.TableLevelStatsReq;
import me.derstine.levelmanager.services.database.TableLevels;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelManager extends JavaPlugin {
    private static Economy econ;

    private static DatabaseManager dbManager;

    private static Map<UUID, PlayerLevelState> playerLevelStates;

    @Override
    public void onEnable() {
        getLogger().info("LevelManager enabled.");

        Config.readConfig(this);

        try {
            dbManager = new DatabaseManager(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // init playerlevelstates map
        playerLevelStates = new HashMap<>();

        // init player connection listener
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);

        // setup economy
        if (!setupEconomy()) {
            getLogger().severe("No economy plugin found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("level").setExecutor(new LevelCommand());
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer()
                .getServicesManager()
                .getRegistration(Economy.class);
        if (rsp == null) return false;

        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        getLogger().info("LevelManager disabled.");
    }

    public static void addPlayerLevelState(UUID uuid, PlayerLevelState playerLevelState) {
        playerLevelStates.put(uuid, playerLevelState);
    }

    public static void removePlayerLevelState(UUID uuid) {
        playerLevelStates.remove(uuid);
    }

    public static PlayerLevelState getPlayerLevelState(UUID uuid) {
        return playerLevelStates.get(uuid);
    }

    public static DatabaseManager getDbManager() {return dbManager;}
}