package me.derstine.levelmanager.services;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class PlayerLevelState {
    private Player player;

    private ArrayList<Requirement> requirements;

    public PlayerLevelState(Player player) {

    }

    public boolean canLevelUp() {
        // if a single requirement is not complete return false
        for(int i = 0; i < requirements.size(); i++) {
            Requirement tempRequirement = requirements.get(i);
            if(!tempRequirement.isComplete()) {return false;}
        }
        // otherwise return true
        return true;
    }

    public void levelUp() {
        if(canLevelUp()) {
            //
        }
    }

    private void init() {

    }

    private void destroy() {

    }
}
