package me.derstine.levelmanager;

import me.derstine.levelmanager.services.requirements.Requirement;
import org.bukkit.entity.Player;

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

    public void levelUp() {
        // makes sure player can level if
        if(!canLevelUp()) return;

        // player can level up, so commit all requirements
        commitRequirements();

        // now update db

    }

    public int getLevel() {
        return level;
    }
}
