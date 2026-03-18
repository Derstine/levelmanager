package me.derstine.levelmanager;

import me.derstine.levelmanager.config.Config;
import me.derstine.levelmanager.services.database.TableLevels;
import me.derstine.levelmanager.services.requirements.Requirement;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerLevelState {
    private final Player player;
    private int level;
    private ArrayList<Requirement> requirements;

    public PlayerLevelState(Player player, int level, ArrayList<Requirement> requirements) {
        this.player = player;
        this.level = level;
        this.requirements = requirements;
    }

    // returns false if any of the requirements is not eligible
    private boolean canLevelUp() {
        for(Requirement requirement : requirements) {
            if(!requirement.isEligible(player)) return false;
        }
        return true;
    }

    private void commitRequirements() {
        for(Requirement requirement : requirements) {
            requirement.commit(player);
        }
    }

    public String levelUp() throws SQLException {
        // makes sure player can level if
        if(!canLevelUp()) return "Failed to level up";

        // player can level up, so commit all requirements
        commitRequirements();

        // now update db
        TableLevels table = LevelManager.getTableLevels();
        table.setLevel(player.getUniqueId(), level + 1);
        // update this object
        level++;

        // clear the requirements and replace with new level requirements
        requirements.clear();
        requirements = Config.getRequirements(level);

        return "Leveled up to level" + level;
    }

    public int getLevel() {
        return level;
    }

    public String printRequirements() {
        String strSum = "";
        for(Requirement req : requirements) {
            strSum += req.getTitle() + "\n";
        }
        return strSum;
    }
}
