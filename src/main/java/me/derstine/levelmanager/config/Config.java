package me.derstine.levelmanager.config;

import me.derstine.levelmanager.services.requirements.*;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Config {
    private static Map<String, ArrayList<Map<String, String>>> configMap;

    public static ArrayList<Requirement> getRequirements(Player player, int level) throws SQLException {
        ArrayList<Requirement> requirements = new ArrayList<>();

        ArrayList<Map<String, String>> levelMap = configMap.get(String.valueOf(level));

        for(Map<String, String> reqMap : levelMap) {
            String type = reqMap.get("type");
            String title = reqMap.get("title");

            // check type and title

            // cases for all requirement types
            switch(type) {
                case "economy":
                    int money = Integer.parseInt(reqMap.get("money"));
                    requirements.add(new EconomyRequirement(title, money));
                    break;
                case "advancement":
                    String advancement = reqMap.get("advancement");
                    requirements.add(new AdvancementRequirement(title, advancement));
                    break;
                case "lifetime-statistic": {
                    String metric = reqMap.get("metric");
                    String target = reqMap.get("target");
                    int value = Integer.parseInt(reqMap.get("value"));
                    requirements.add(new LifetimeStatistic(title, metric, target, value));
                    break;
                }
                case "level-statistic":
                    String metric = reqMap.get("metric");
                    String target = reqMap.get("target");
                    int value = Integer.parseInt(reqMap.get("value"));
                    requirements.add(new LevelStatistic(player, title, metric, target, value));
                    break;
                default:
                    // log error
            }
        }


        return requirements;
    }

    public static void readConfig(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();

        configMap = new HashMap<>();

        ConfigurationSection levelsSection = config.getConfigurationSection("levels");
        if (levelsSection == null) return;

        for (String levelKey : levelsSection.getKeys(false)) {
            ConfigurationSection levelSection = levelsSection.getConfigurationSection(levelKey);
            if (levelSection == null) continue;

            ArrayList<Map<String, String>> requirementList = new ArrayList<>();

            if (levelSection.contains("requirements")) {
                for (Map<?, ?> req : levelSection.getMapList("requirements")) {

                    Map<String, String> reqMap = new HashMap<>();

                    for (Map.Entry<?, ?> entry : req.entrySet()) {
                        reqMap.put(
                                String.valueOf(entry.getKey()),
                                String.valueOf(entry.getValue())
                        );
                    }

                    requirementList.add(reqMap);
                }
            }

            configMap.put(levelKey, requirementList);
        }
    }
}
