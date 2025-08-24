package me.dotu.MMO.Enums;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.dotu.MMO.Skills.MasterSkill;

public class ConfigEnum {
    public static enum Type {
        TOOLS{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject tools = new JsonObject();
                JsonObject exampleTool = new JsonObject();

                exampleTool.addProperty("DisplayName", "example_tool");
                exampleTool.addProperty("tier", "COMMON");
                tools.add("example_tool", exampleTool);

                defaultConfig.add("Tools", tools);
            }
        },
        
        ARMORS{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject armors = new JsonObject();
                JsonObject exampleArmor = new JsonObject();

                exampleArmor.addProperty("DisplayName", "example_armor");
                exampleArmor.addProperty("tier", "COMMON");
                armors.add("example_armor", exampleArmor);

                defaultConfig.add("Armors", armors);
            }
        },

        WEAPONS{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject weapons = new JsonObject();
                JsonObject exampleWeapon = new JsonObject();

                exampleWeapon.addProperty("DisplayName", "example_weapon");
                exampleWeapon.addProperty("tier", "COMMON");
                weapons.add("example_weapon", exampleWeapon);

                defaultConfig.add("Weapons", weapons);
            }
        },

        SETTINGS{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject weaponSettings = new JsonObject();
                weaponSettings.addProperty("enabled", true);

                JsonObject toolSettings = new JsonObject();
                toolSettings.addProperty("enabled", true);

                JsonObject armorSettings = new JsonObject();
                armorSettings.addProperty("enabled", true);

                JsonObject pvpSettings = new JsonObject();
                pvpSettings.addProperty("season_timer", System.currentTimeMillis());
                
                JsonObject skillSettings = new JsonObject();
                for (SkillEnum.Skill skill : SkillEnum.Skill.values()){
                    skillSettings.addProperty(skill.toString().toLowerCase(), true);
                }

                defaultConfig.add("enabled_skills", skillSettings);
                defaultConfig.add("weapon", weaponSettings);
                defaultConfig.add("tool", toolSettings);
                defaultConfig.add("armor", armorSettings);
                defaultConfig.add("pvp", pvpSettings);
            }
    },

        PLAYERDATA{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject playerData = new JsonObject();

                JsonObject guild = new JsonObject();
                guild.addProperty("active", false);

                JsonObject titles = new JsonObject();
                titles.addProperty("guild_title", "");
                titles.addProperty("player_title", "");

                JsonObject party = new JsonObject();
                party.addProperty("active", false);

                JsonObject pvp = new JsonObject();
                pvp.addProperty("kills", 0);
                pvp.addProperty("deaths", 0);
                pvp.addProperty("season_timer", System.currentTimeMillis());

                JsonObject skills = new JsonObject();
                JsonObject skillsExp = new JsonObject();
                for (SkillEnum.Skill skillName : SkillEnum.Skill.values()){
                    MasterSkill skill = MasterSkill.skillsMap.get(skillName);
                    if (skill != null){
                        skillsExp.addProperty(skill.getName(), skill.getStartingLevel());
                    }
                }
                skills.add("xp", skillsExp);

                playerData.add("skills", skills);
                playerData.add("guild", guild);
                playerData.add("titles", titles);
                playerData.add("party", party);
                playerData.add("pvp", pvp);

                defaultConfig.add("Data", playerData);
            }
        },

        MOB_TABLE{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject mobTable = new JsonObject();

                // Easy Table
                JsonObject easy = new JsonObject();
                JsonObject easyItem = new JsonObject();
                easyItem.addProperty("WEIGHT", 10);

                easy.add("LEATHER_HELMET", easyItem);

                // Medium Table
                JsonObject medium = new JsonObject();
                JsonObject mediumItem = new JsonObject();
                mediumItem.addProperty("weight", 10);

                medium.add("GOLDEN_HELMET", mediumItem);

                // Hard Table
                JsonObject hard = new JsonObject();
                JsonObject hardItem = new JsonObject();
                hardItem.addProperty("weight", 10);

                hard.add("CHAINMAIL_HELMET", hardItem);

                // Very Hard Table
                JsonObject veryHard = new JsonObject();
                JsonObject veryHardItem = new JsonObject();
                veryHardItem.addProperty("weight", 10);

                veryHard.add("IRON_HELMET", veryHardItem);

                // Brutal Table
                JsonObject brutal = new JsonObject();
                JsonObject brutalItem = new JsonObject();
                brutalItem.addProperty("weight", 10);

                brutal.add("DIAMOND_HELMET", brutalItem);

                // Impossible Table
                JsonObject impossible = new JsonObject();
                JsonObject impossibleItem = new JsonObject();
                impossibleItem.addProperty("weight", 10);

                impossible.add("NETHERITE_HELMET", impossibleItem);

                mobTable.add("EASY", easy);
                mobTable.add("MEDIUM", medium);
                mobTable.add("HARD", hard);
                mobTable.add("VERY_HARD", veryHard);
                mobTable.add("BRUTAL", brutal);
                mobTable.add("IMPOSSIBLE", impossible);  
                
                defaultConfig.add("Tables", mobTable);
            }
        },

        SPAWNER_DATA{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject spawner = new JsonObject();

                spawner.addProperty("min_level", 10);
                spawner.addProperty("max_level", 50);
                spawner.addProperty("min_spawn_delay", 10);
                spawner.addProperty("max_spawn_delay", 50);
                spawner.addProperty("spawn_range", 25);
                spawner.addProperty("difficulty", 0.5D);
                spawner.addProperty("armored", true);
                spawner.addProperty("weaponed", true);
                spawner.addProperty("name_visible", true);
                spawner.addProperty("spawn_randomly", false);
                spawner.addProperty("table", "default");
                
                JsonArray spawnLocations = new JsonArray();
                spawnLocations.add("world.100.100.100");
                spawnLocations.add("world.200.200.200");

                spawner.add("spawn_locations", spawnLocations);

                defaultConfig.add("test_spawner", spawner);
            }
        };

        public abstract void populate(JsonObject config);
    }
    
    public static enum Settings{
        ENABLED_SKILLS,
        WEAPON,
        TOOL,
        ARMOR,
        PVP
    }

    public static enum PlayerSettings{
        SKILLS,
        GUILD,
        TITLES,
        PARTY,
        PVP
    }
}