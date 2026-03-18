package me.derstine.levelmanager.controllers;

import me.derstine.levelmanager.LevelManager;
import me.derstine.levelmanager.PlayerLevelState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class LevelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        PlayerLevelState playerLevelState = LevelManager.getPlayerLevelState(player.getUniqueId());

        if (playerLevelState == null) {
            player.sendMessage("Your level data is not loaded yet.");
            return true;
        }

        // /level
        if (args.length == 0) {
            player.sendMessage("Your level is " + playerLevelState.getLevel());
            player.sendMessage("Requirements: \n" + playerLevelState.printRequirements());
            return true;
        }

        // /level up
        if (args[0].equalsIgnoreCase("up")) {
            try {
                String levelUpMessage = playerLevelState.levelUp();
                player.sendMessage(levelUpMessage);
            } catch (SQLException e) {
                player.sendMessage("There was an error while leveling up.");
                e.printStackTrace();
            }
            return true;
        }

        player.sendMessage("Unknown subcommand. Use /level help");
        return true;
    }
}
