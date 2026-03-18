package me.derstine.levelmanager.services.requirements;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

public class AdvancementRequirement extends Requirement {
    private Advancement advancement;

    public AdvancementRequirement(String title, String advancementString) {
        Advancement advancement = Bukkit.getAdvancement(org.bukkit.NamespacedKey.fromString(advancementString));

        this.title = title;
        this.advancement = advancement;
    }

    @Override
    public boolean isEligible(Player player) {
        // advancement doesn't exist
        if (advancement == null) {
            return false;
        }

        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        return progress.isDone();
    }
}
