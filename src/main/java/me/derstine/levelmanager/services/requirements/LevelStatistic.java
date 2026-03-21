package me.derstine.levelmanager.services.requirements;

import me.derstine.levelmanager.LevelManager;
import me.derstine.levelmanager.services.database.TableLevelStatisticRequirements;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LevelStatistic extends StatisticRequirement {
    private int base;

    public LevelStatistic(Player player, String title, String stat, String target, int value) {
        super(title, stat, target, value);

        // get base value
        this.base = getBaseFromDb(player);
    }

    @Override
    public boolean isEligible(Player player) {
        int statValue = getStatistic(player);

        if(statValue - base >= value) return true;

        return false;
    }

    // search for the base value in db
    // if base value not found, then create it in db
    private int getBaseFromDb(Player player) {
        try {
            var table = LevelManager.getTableLevelStatisticRequirements();
            String statKey = getStatKey();
            UUID uuid = player.getUniqueId();

            if (!table.exists(uuid, statKey)) {
                int currentValue = getStatistic(player);
                table.insert(uuid, statKey, currentValue);
                return currentValue;
            }

            return table.getBaseValue(uuid, statKey);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private String getStatKey() {
        if(this.target == null) {
            return this.stat.name();
        }
        return this.stat.name() + "-" + this.target;
    }
}
