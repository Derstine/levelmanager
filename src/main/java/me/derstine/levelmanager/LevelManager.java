package me.derstine.levelmanager;

import me.derstine.levelmanager.controllers.LevelCommand;
import me.derstine.levelmanager.services.database.DatabaseManager;
import me.derstine.levelmanager.services.database.TableLevels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelManager extends JavaPlugin {
    private static Economy econ;

    private static DatabaseManager databaseManager;
    private static TableLevels tableLevels;

    private static Map<UUID, PlayerLevelState> playerLevelStates;

    @Override
    public void onEnable() {
        getLogger().info("LevelManager enabled.");

        // db
        try {
            databaseManager = new DatabaseManager(this);
            databaseManager.connect();

            tableLevels = new TableLevels(databaseManager.getConnection());
            tableLevels.createTable();

        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

//        if (command.getName().equalsIgnoreCase("level")) {
//
//            if (!(sender instanceof Player player)) {
//                sender.sendMessage("Only players can use this command.");
//                return true;
//            }
//
//            PlayerLevelState playerLevelState = playerLevelStates.get(player.getUniqueId());
//
//            player.sendMessage("Your level is " + playerLevelState.getLevel());
//
//            return true;
//        }

        if (command.getName().equalsIgnoreCase("level")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            PlayerLevelState playerLevelState = playerLevelStates.get(player.getUniqueId());

            String levelUpMessage;
            try {
                levelUpMessage = playerLevelState.levelUp();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            player.sendMessage(levelUpMessage);

            return true;
        }

        return false;
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

    public static TableLevels getTableLevels() {
        return tableLevels;
    }
}