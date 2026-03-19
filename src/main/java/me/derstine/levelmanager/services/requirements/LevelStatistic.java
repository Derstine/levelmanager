package me.derstine.levelmanager.services.requirements;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class LevelStatistic extends StatisticRequirement {
    private int value;
    private int base;

    public LevelStatistic(String title, Statistic stat, String target, int value, int base) {
        super(title, stat, target);
        this.value = value;
        this.base = base;
    }

    @Override
    public boolean isEligible(Player player) {
        int statValue = getStatistic(player);

        if(statValue - base >= value) return true;

        return false;
    }
}
