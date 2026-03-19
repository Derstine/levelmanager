package me.derstine.levelmanager.services.requirements;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class LifetimeStatistic extends StatisticRequirement {
    private int value;

    public LifetimeStatistic(String title, Statistic stat, String target, int value) {
        super(title, stat, target);
        this.value = value;
    }

    @Override
    public boolean isEligible(Player player) {
        int statValue = getStatistic(player);

        if(statValue >= value) return true;

        return false;
    }
}
