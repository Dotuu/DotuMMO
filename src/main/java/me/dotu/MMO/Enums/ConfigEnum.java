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

                exampleTool.addProperty("DisplayName", "example tool");
                exampleTool.addProperty("tier", "COMMON");
                tools.add("example tool", exampleTool);

                defaultConfig.add("Tools", tools);
            }
        },
        
        ARMORS{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject armors = new JsonObject();
                JsonObject exampleArmor = new JsonObject();

                exampleArmor.addProperty("DisplayName", "example armor");
                exampleArmor.addProperty("tier", "COMMON");
                armors.add("example armor", exampleArmor);

                defaultConfig.add("Armors", armors);
            }
        },

        WEAPONS{
            @Override
            public void populate(JsonObject defaultConfig){
                JsonObject weapons = new JsonObject();
                JsonObject exampleWeapon = new JsonObject();

                exampleWeapon.addProperty("DisplayName", "example weapon");
                exampleWeapon.addProperty("tier", "COMMON");
                weapons.add("example weapon", exampleWeapon);

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
                
                JsonObject skillSettings = new JsonObject();
                for (SkillEnum.Skill skill : SkillEnum.Skill.values()){
                    skillSettings.addProperty(skill.toString().toLowerCase(), true);
                }

                settings.add("enabled skills", skillSettings);
                settings.add("weapon settings", weaponSettings);
                settings.add("tool settings", toolSettings);
                settings.add("armor settings", armorSettings);


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

                JsonObject skills = new JsonObject();
                JsonObject skills_xp = new JsonObject();
                for (SkillEnum.Skill skillName : SkillEnum.Skill.values()){
                    MasterSkill skill = MasterSkill.skillsMap.get(skillName);
                    skills_xp.addProperty(skill.getName(), skill.getStartingLevel());
                }
                skills.add("xp", skills_xp);

                data.add("skills", skills);
                data.add("guild", guild);
                data.add("titles", titles);
                data.add("party", party);

                defaultConfig.add("Data", data);
            }
        };
        public abstract void populate(JsonObject config);
    }
    
    public static enum Settings{
        ENABLED_SKILLS,
        WEAPON_SETTINGS,
        TOOL_SETTINGS,
        ARMOR_SETTINGS,
    }
}