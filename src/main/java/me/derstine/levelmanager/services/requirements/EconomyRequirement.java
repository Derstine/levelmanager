package me.derstine.levelmanager.services.requirements;

import me.derstine.levelmanager.LevelManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class EconomyRequirement extends Requirement {
    private int money;

    public EconomyRequirement(String title, int money) {
        this.title = title;
        this.money = money;
    }

    // returns true if player has enough money
    @Override
    public boolean isEligible(Player player) {
        double balance = LevelManager.getEconomy().getBalance(player);
        return balance >= money;
    }

    // takes away the balance
    @Override
    public void commit(Player player) {
        Economy econ = LevelManager.getEconomy();
        econ.withdrawPlayer(player, money);
    }
}
