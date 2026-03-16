package me.derstine.levelmanager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelManager extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("LevelManager enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("LevelManager disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("level")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            player.sendMessage("Your level is not implemented yet.");

            return true;
        }

        return false;
    }
}