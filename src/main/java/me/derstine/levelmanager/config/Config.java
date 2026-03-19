package me.derstine.levelmanager.config;

import me.derstine.levelmanager.services.requirements.AdvancementRequirement;
import me.derstine.levelmanager.services.requirements.EconomyRequirement;
import me.derstine.levelmanager.services.requirements.LifetimeStatistic;
import me.derstine.levelmanager.services.requirements.Requirement;
import org.bukkit.Statistic;

import java.util.ArrayList;

public class Config {
    public static ArrayList<Requirement> getRequirements(int level) {
        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(new EconomyRequirement("$100", 100));
        requirements.add(new AdvancementRequirement("Get the Diamonds advancement", "minecraft:story/mine_diamond"));
        requirements.add(new LifetimeStatistic("Statistic title", Statistic.BELL_RING, null, level * 2));

        return requirements;
    }
}
