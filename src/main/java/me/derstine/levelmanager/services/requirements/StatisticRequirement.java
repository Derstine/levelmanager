package me.derstine.levelmanager.services.requirements;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StatisticRequirement extends Requirement {
    protected Statistic stat;
    protected String target;

    public StatisticRequirement(String title, Statistic stat, String target) {
        super(title);
        this.stat = stat;
        this.target = target;
    }

    protected int getStatistic(Player player) {
        // chatgpt code
        switch (stat.getType()) {

            case UNTYPED:
                return player.getStatistic(stat);

            case BLOCK:
            case ITEM:
                Material material = Material.matchMaterial(target);
                if (material == null) return 0;

                return player.getStatistic(stat, material);

            case ENTITY:
                EntityType entity = EntityType.fromName(target);
                if (entity == null) return 0;

                return player.getStatistic(stat, entity);

            default:
                return 0;
        }
    }
}
