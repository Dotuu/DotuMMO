package me.dotu.MMO.Enums;

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
                JsonObject settings = new JsonObject();

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

                settings.add("enabled_skills", skillSettings);
                settings.add("weapon", weaponSettings);
                settings.add("tool", toolSettings);
                settings.add("armor", armorSettings);
                settings.add("pvp", pvpSettings);


                defaultConfig.add("Settings", settings);
            }
        },

        PLAYERDATA{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject data = new JsonObject();

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

                data.add("skills", skills);
                data.add("guild", guild);
                data.add("titles", titles);
                data.add("party", party);
                data.add("pvp", pvp);

                defaultConfig.add("Data", data);
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