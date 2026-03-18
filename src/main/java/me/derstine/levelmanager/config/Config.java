package me.derstine.levelmanager.config;

import me.derstine.levelmanager.services.requirements.EconomyRequirement;
import me.derstine.levelmanager.services.requirements.Requirement;

import java.util.ArrayList;

public class Config {
    public static ArrayList<Requirement> getRequirements(int level) {
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(new EconomyRequirement("$100", 100));

        return requirements;
    }
}
