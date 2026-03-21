package me.derstine.levelmanager.services.requirements;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class LifetimeStatistic extends StatisticRequirement {

    public LifetimeStatistic(String title, String stat, String target, int value) {
        super(title, stat, target, value);
    }

    @Override
    public boolean isEligible(Player player) {
        int statValue = getStatistic(player);

        if(statValue >= value) return true;

        return false;
    }
}
