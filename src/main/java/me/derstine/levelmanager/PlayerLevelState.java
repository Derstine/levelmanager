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

    // returns null if eligible, if not eligible then it returns the requirement's title
    private String canLevelUp() {
        for(Requirement requirement : requirements) {
            if(!requirement.isEligible(player)) return requirement.getTitle();
        }
        return null;
    }

    //
    private void commitRequirements() {
        for(Requirement requirement : requirements) {
            requirement.commit(player);
        }
    }

    public String levelUp() throws SQLException {
        // make sure player can level up
        String error = canLevelUp();
        if(error != null) return "Need to complete requirement: " + error;

        // player can level up, so commit all requirements
        commitRequirements();

        // set level
        setLevel(level + 1);

        // give rewards


        return "Leveled up to level" + level;
    }

    public int getLevel() {
        return level;
    }

    public String printRequirements() {
        StringBuilder strSum = new StringBuilder();
        for(Requirement req : requirements) {
            strSum.append(req.getTitle()).append("\n");
        }
        return strSum.toString();
    }

    private void resetRequirements() {
        requirements.clear();
        requirements = Config.getRequirements(level);
    }

    public void setLevel(int level) throws SQLException {
        // update db
        TableLevels table = LevelManager.getTableLevels();
        table.setLevel(player.getUniqueId(), level);

        // update this object
        this.level = level;

        // reset requirements for the given level
        resetRequirements();
    }
}