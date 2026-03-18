package me.derstine.levelmanager;

import me.derstine.levelmanager.config.Config;
import me.derstine.levelmanager.services.database.TableLevels;
import me.derstine.levelmanager.services.requirements.Requirement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws SQLException {
        // get player uuid
        UUID uuid = event.getPlayer().getUniqueId();

        // if player uuid is not found in table levels, add uuid
        TableLevels table = LevelManager.getTableLevels();
        int level = table.getLevel(uuid);

        // player isn't in database
        if(level == -1) {
            table.insertNewPlayer(uuid);
            level = 1;
        }

        ArrayList<Requirement> requirements = Config.getRequirements(level);

        PlayerLevelState playerLevelState = new PlayerLevelState(event.getPlayer(), level, requirements);

        LevelManager.addPlayerLevelState(uuid, playerLevelState);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        // remove player level state from level manager
        LevelManager.removePlayerLevelState(event.getPlayer().getUniqueId());
    }
}