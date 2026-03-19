package me.derstine.levelmanager.services.requirements;

import org.bukkit.entity.Player;

public class Requirement {
    protected boolean db = false;

    protected String title;

    public Requirement(String title) {
        this.title = title;
    }

    public boolean isEligible(Player player) {
        return false;
    }

    // if the requirement involves doing something to user, this function does it
    public void commit(Player player) {

    }

    public String getTitle() {
        return title;
    }
}
