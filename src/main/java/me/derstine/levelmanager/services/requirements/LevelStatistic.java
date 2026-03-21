package me.derstine.levelmanager.services.requirements;

import me.derstine.levelmanager.LevelManager;
import me.derstine.levelmanager.services.database.DatabaseManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class LevelStatistic extends StatisticRequirement {
    private int base;

    public LevelStatistic(Player player, String title, String stat, String target, int value) throws SQLException {
        super(title, stat, target, value);

        // get base value
        base = getBaseFromDb(player);
    }

    @Override
    public boolean isEligible(Player player) {
        int statValue = getStatistic(player);

        if(statValue - base >= value) return true;

        return false;
    }

    // search for the base value in db
    // if base value not found, then create it in db
    private int getBaseFromDb(Player player) throws SQLException {
        DatabaseManager dbManager = LevelManager.getDbManager();
        String statKey = getStatKey();
        UUID uuid = player.getUniqueId();

        if(dbManager.levelStatReqExists(uuid, statKey)) {
            base = dbManager.getLevelStatReq(uuid, statKey);
        }
        else {
            int tempBase = getStatistic(player);
            dbManager.insertLevelStatReq(uuid, statKey, base);
            base = tempBase;
        }

        return base;
    }

    private String getStatKey() {
        if(this.target == null) {
            return this.stat.name();
        }
        return this.stat.name() + "-" + this.target;
    }
}
