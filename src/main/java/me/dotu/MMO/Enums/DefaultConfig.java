package me.dotu.MMO.Enums;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.dotu.MMO.Skills.Skill;

public enum DefaultConfig {
    SETTINGS {
        @Override
        public void populate(JsonObject defaultConfig) {
            JsonObject weaponSettings = new JsonObject();
            weaponSettings.addProperty("enabled", true);

            JsonObject toolSettings = new JsonObject();
            toolSettings.addProperty("enabled", true);

            JsonObject armorSettings = new JsonObject();
            armorSettings.addProperty("enabled", true);

            JsonObject pvpSettings = new JsonObject();
            pvpSettings.addProperty("season_timer", System.currentTimeMillis());

            JsonObject skillSettings = new JsonObject();
            for (SkillType skill : SkillType.values()) {
                skillSettings.addProperty(skill.toString().toLowerCase(), true);
            }

            defaultConfig.add("enabled_skills", skillSettings);
            defaultConfig.add("weapon", weaponSettings);
            defaultConfig.add("tool", toolSettings);
            defaultConfig.add("armor", armorSettings);
            defaultConfig.add("pvp", pvpSettings);
        }
    },

    PLAYERDATA {
        @Override
        public void populate(JsonObject defaultConfig) {
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
            for (SkillType skillName : SkillType.values()) {
                Skill skill = Skill.skillsMap.get(skillName);
                if (skill != null) {
                    skills.addProperty(skill.getName().toUpperCase(), skill.getStartingLevel());
                }
            }

            playerData.add("skills", skills);
            playerData.add("guild", guild);
            playerData.add("titles", titles);
            playerData.add("party", party);
            playerData.add("pvp", pvp);

            defaultConfig.add("Data", playerData);
        }
    },

    SPAWNER_DATA {
        @Override
        public void populate(JsonObject defaultConfig) {
            JsonObject spawner = new JsonObject();

            spawner.addProperty("min_level", 10);
            spawner.addProperty("max_level", 50);
            spawner.addProperty("spawn_delay", 60);
            spawner.addProperty("spawn_range", 25);
            spawner.addProperty("spawn_count", 10);
            spawner.addProperty("difficulty", 0.5D);
            spawner.addProperty("armored", true);
            spawner.addProperty("weaponed", true);
            spawner.addProperty("name_visible", true);
            spawner.addProperty("spawn_randomly", false);
            spawner.addProperty("table", "default");

            JsonArray spawnLocations = new JsonArray();
            JsonArray spawnerLocations = new JsonArray();

            spawner.add("spawn_locations", spawnLocations);
            spawner.add("spawner_locations", spawnerLocations);

            defaultConfig.add("test_spawner", spawner);
        }
    },

    SPAWNER_LOCATION_DATA {
        @Override
        public void populate(JsonObject defaultConfig) {
            JsonObject spawner = new JsonObject();

            defaultConfig.add("", spawner);
        }
    },

    GLOBAL_LOOT_TABLE {
        @Override
        public void populate(JsonObject defaultConfig) {
            defaultConfig.addProperty("name", "global");
            defaultConfig.add("items", new JsonArray());
        }
    },

    FISHING{
        @Override
        public void populate(JsonObject defaultConfig){
            final int min = 20;
            final int max = 30;
            final int requiredLevel = 0;
            JsonArray sources = new JsonArray();
            String[] mats = {"COD","SALMON","TROPICAL_FISH","PUFFERFISH"};

            for (String material : mats) {
                JsonObject obj = new JsonObject();
                obj.addProperty("material", material);
                obj.addProperty("min_exp", min);
                obj.addProperty("max_exp", max);
                obj.addProperty("required_level", requiredLevel);
                sources.add(obj);
            }
            defaultConfig.add("sources", sources);
        }
    },

    MINING{
        @Override
        public void populate(JsonObject defaultConfig){
            final int min = 20;
            final int max = 30;
            final int requiredLevel = 0;
            JsonArray sources = new JsonArray();
            String[] mats = {
                "COAL_ORE","DEEPSLATE_COAL_ORE","COPPER_ORE","DEEPSLATE_COPPER_ORE",
                "IRON_ORE","DEEPSLATE_IRON_ORE","REDSTONE_ORE","DEEPSLATE_REDSTONE_ORE",
                "LAPIS_ORE","DEEPSLATE_LAPIS_ORE","GOLD_ORE","DEEPSLATE_GOLD_ORE",
                "DIAMOND_ORE","DEEPSLATE_DIAMOND_ORE","EMERALD_ORE","DEEPSLATE_EMERALD_ORE",
                "NETHER_QUARTZ_ORE","NETHER_GOLD_ORE","ANCIENT_DEBRIS"
            };

            for (String material : mats){
                JsonObject obj = new JsonObject();
                obj.addProperty("material", material);
                obj.addProperty("min_exp", min);
                obj.addProperty("max_exp", max);
                obj.addProperty("required_level", requiredLevel);
                obj.addProperty("required_level", 0);
                sources.add(obj);
            }
            defaultConfig.add("sources", sources);
        }
    },

    WOODCUTTING{
        @Override
        public void populate(JsonObject defaultConfig){
            final int min = 20;
            final int max = 30;
            final int requiredLevel = 0;
            JsonArray sources = new JsonArray();
            String[] mats = {"OAK_LOG","SPRUCE_LOG","BIRCH_LOG","JUNGLE_LOG","ACACIA_LOG", 
            "DARK_OAK_LOG","MANGROVE_LOG","CHERRY_LOG","PALE_OAK_LOG"};
                
            for (String material : mats){
                JsonObject obj = new JsonObject();
                obj.addProperty("material", material);
                obj.addProperty("min_exp", min);
                obj.addProperty("max_exp", max);
                obj.addProperty("required_level", requiredLevel);
                sources.add(obj);
            }
            defaultConfig.add("sources", sources);
        }
    },

    AXE{
        @Override
        public void populate(JsonObject defaultConfig){
            final int min = 20;
            final int max = 30;
            final int requiredLevel = 0;
            JsonArray sources = new JsonArray();
            String[] entityTypes = {
                "BLAZE","BOGGED","BREEZE","CREAKING","CREEPER","ELDER_GUARDIAN","ENDERMITE",
                "ENDER_DRAGON","EVOKER","GHAST","GUARDIAN","HOGLIN","HUSK","MAGMA_CUBE",
                "PHANTOM","PIGLIN_BRUTE","PILLAGER","RAVAGER","SHULKER","SILVERFISH","SKELETON",
                "SLIME","STRAY","VEX","VINDICATOR","WARDEN","WITCH","WITHER","WITHER_SKELETON",
                "ZOGLIN","ZOMBIE","ZOMBIE_VILLAGER"
            };
            for (String entityType : entityTypes){
                JsonObject obj = new JsonObject();
                obj.addProperty("entitytype", entityType);
                obj.addProperty("min_exp", min);
                obj.addProperty("max_exp", max);
                obj.addProperty("required_level", requiredLevel);
                sources.add(obj);
            }
            defaultConfig.add("sources", sources);
        }
    },

    SWORD{
        @Override
        public void populate(JsonObject defaultConfig){
            final int min = 20;
            final int max = 30;
            final int requiredLevel = 0;
            JsonArray sources = new JsonArray();
            String[] entityTypes = {
                "BLAZE","BOGGED","BREEZE","CREAKING","CREEPER","ELDER_GUARDIAN","ENDERMITE",
                "ENDER_DRAGON","EVOKER","GHAST","GUARDIAN","HOGLIN","HUSK","MAGMA_CUBE",
                "PHANTOM","PIGLIN_BRUTE","PILLAGER","RAVAGER","SHULKER","SILVERFISH","SKELETON",
                "SLIME","STRAY","VEX","VINDICATOR","WARDEN","WITCH","WITHER","WITHER_SKELETON",
                "ZOGLIN","ZOMBIE","ZOMBIE_VILLAGER"
            };

            for (String entityType : entityTypes){
                JsonObject obj = new JsonObject();
                obj.addProperty("entitytype", entityType);
                obj.addProperty("min_exp", min);
                obj.addProperty("max_exp", max);
                obj.addProperty("required_level", requiredLevel);
                sources.add(obj);
            }
            defaultConfig.add("sources", sources);
        }
    },

    FARMING{
        @Override
        public void populate(JsonObject defaultConfig){
            final int min = 20;
            final int max = 30;
            final int requiredLevel = 0;
            JsonArray sources = new JsonArray();
            String[] materials = {
                "WHEAT", "CARROT", "POTATO", "NETHER_WART", "COCOA", "SWEETBERRY_BUSH", "BEETROOT"
            };

            for (String material : materials){
                JsonObject obj = new JsonObject();
                obj.addProperty("material", material);
                obj.addProperty("min_exp", min);
                obj.addProperty("max_exp", max);
                obj.addProperty("required_level", requiredLevel);
                sources.add(obj);
            }
            defaultConfig.add("sources", sources);
        }
    },

    COOKING{
        @Override
        public void populate(JsonObject defaultConfig){
            final int min = 20;
            final int max = 30;
            final int requiredLevel = 0;
            JsonArray sources = new JsonArray();
            String[] foods = {
                "CAKE", "PUMPKIN_PIE", "BREAD", "COOKIE", "SUSPICIOUS_STEW", "GOLDEN_CARROT",
                "GOLDEN_APPLE", "HONEY_BOTTLE", "RABBIT_STEW", "BEETROOT_SOUP"
            };

            for (String entityType : foods){
                JsonObject obj = new JsonObject();
                obj.addProperty("material", entityType);
                obj.addProperty("min_exp", min);
                obj.addProperty("max_exp", max);
                obj.addProperty("required_level", requiredLevel);
                sources.add(obj);
            }
            defaultConfig.add("sources", sources);
        }
    },

    ARCHERY{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },

    MACE{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },

    POTION_CRAFTING{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },

    GEM_CRAFTING{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },

    ARMOR_CRAFTING{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },

    WEAPON_CRAFTING{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },
    
    BLOCK{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },
    
    UNARMED{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    },
    
    TAMING{
        @Override
        public void populate(JsonObject defaultConfig){
            JsonObject root = new JsonObject();

            root.addProperty("COD", 15);
            defaultConfig.add("exp", root);
        }
    };

    public abstract void populate(JsonObject config);
}