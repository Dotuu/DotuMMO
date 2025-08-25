package me.dotu.MMO.Enums;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class RewardTable {
    public static enum FishingReward {
        RAW_COD(EntityType.COD, 20),
        RAW_SALMON(EntityType.SALMON, 30),
        TROPICAL_FISH(EntityType.TROPICAL_FISH, 200),
        PUFFERFISH(EntityType.PUFFERFISH, 45);

        private final EntityType entityType;
        private final int xp;

        FishingReward(EntityType entityType, int xp) {
            this.entityType = entityType;
            this.xp = xp;
        }

        public int getXpValue() {
            return this.xp;
        }

        public EntityType getEntityType() {
            return this.entityType;
        }
    }

    public static enum MiningReward {
        COAL_ORE(Material.COAL_ORE, 20),
        DEEPSLATE_COAL_ORE(Material.DEEPSLATE_COAL_ORE, 20),

        COPPER_ORE(Material.COAL_ORE, 20),
        DEEPSLATE_COPPER_ORE(Material.DEEPSLATE_COPPER_ORE, 20),

        IRON_ORE(Material.IRON_ORE, 35),
        DEEPSLATE_IRON_ORE(Material.DEEPSLATE_IRON_ORE, 35),

        REDSTONE_ORE(Material.REDSTONE_ORE, 50),
        DEEPSLATE_REDSTONE_ORE(Material.DEEPSLATE_REDSTONE_ORE, 50),

        LAPIS_ORE(Material.LAPIS_ORE, 75),
        DEEPSLATE_LAPIS_ORE(Material.DEEPSLATE_LAPIS_ORE, 75),

        GOLD_ORE(Material.GOLD_ORE, 90),
        DEEPSLATE_GOLD_ORE(Material.DEEPSLATE_GOLD_ORE, 90),

        DIAMOND_ORE(Material.DIAMOND_ORE, 100),
        DEEPSLATE_DIAMOND_ORE(Material.DEEPSLATE_DIAMOND_ORE, 100),

        EMERALD_ORE(Material.EMERALD_ORE, 200),
        DEEPSLATE_EMERALD_ORE(Material.DEEPSLATE_EMERALD_ORE, 200),

        NETHER_QUARTZ_ORE(Material.NETHER_QUARTZ_ORE, 90),
        NETHER_GOLD_ORE(Material.NETHER_GOLD_ORE, 90),

        ANCIENT_DEBRIS(Material.ANCIENT_DEBRIS, 250);

        private final Material material;
        private final int xp;

        MiningReward(Material material, int xp) {
            this.material = material;
            this.xp = xp;
        }

        public int getXpValue() {
            return this.xp;
        }

        public Material getMaterial() {
            return this.material;
        }
    }

    public static enum WoodcuttingReward {
        OAK_LOG(Material.OAK_LOG, 20),
        SPRUCE_LOG(Material.SPRUCE_LOG, 30),
        BIRCH_LOG(Material.BIRCH_LOG, 20),
        JUNGLE_LOG(Material.JUNGLE_LOG, 45),
        ACACIA_LOG(Material.ACACIA_LOG, 45),
        DARK_OAK_LOG(Material.DARK_OAK_LOG, 45),
        MANGROVE_LOG(Material.MANGROVE_LOG, 45),
        CHERRY_LOG(Material.CHERRY_LOG, 45),
        PALE_OAK_LOG(Material.PALE_OAK_LOG, 45);

        private final Material material;
        private final int xp;

        WoodcuttingReward(Material material, int xp) {
            this.material = material;
            this.xp = xp;
        }

        public int getXpValue() {
            return this.xp;
        }

        public Material getMaterial() {
            return this.material;
        }
    }

    public static enum AxeReward {
        BLAZE(EntityType.BLAZE, 20),
        BOGGED(EntityType.BOGGED, 20),
        BREEZE(EntityType.BREEZE, 20),
        CREAKING(EntityType.CREAKING, 20),
        CREEPER(EntityType.CREEPER, 20),
        ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN, 20),
        ENDERMITE(EntityType.ENDERMITE, 20),
        ENDER_DRAGON(EntityType.ENDER_DRAGON, 20),
        EVOKER(EntityType.EVOKER, 20),
        GHAST(EntityType.GHAST, 20),
        GUARDIAN(EntityType.GUARDIAN, 20),
        HOGLIN(EntityType.HOGLIN, 20),
        HUSK(EntityType.HUSK, 20),
        MAGMA_CUBE(EntityType.MAGMA_CUBE, 20),
        PHANTOM(EntityType.PHANTOM, 20),
        PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, 20),
        PILLAGER(EntityType.PILLAGER, 20),
        RAVAGER(EntityType.RAVAGER, 20),
        SHULKER(EntityType.SHULKER, 20),
        SILVERFISH(EntityType.SILVERFISH, 20),
        SKELETON(EntityType.SKELETON, 20),
        SLIME(EntityType.SLIME, 20),
        STRAY(EntityType.STRAY, 20),
        VEX(EntityType.VEX, 20),
        VINDICATOR(EntityType.VINDICATOR, 20),
        WARDEN(EntityType.WARDEN, 20),
        WITCH(EntityType.WITCH, 20),
        WITHER(EntityType.WITHER, 20),
        WITHER_SKELETON(EntityType.WITHER_SKELETON, 20),
        ZOGLIN(EntityType.ZOGLIN, 20),
        ZOMBIE(EntityType.ZOMBIE, 20),
        ZOMBIE_VILLAGER(EntityType.VILLAGER, 20);

        private final EntityType entityType;
        private final int xp;

        AxeReward(EntityType entityType, int xp) {
            this.xp = xp;
            this.entityType = entityType;
        }

        public int getXpValue() {
            return this.xp;
        }

        public EntityType getEntityType(){
            return this.entityType;
        }
    }

    public static enum SwordReward {
        BLAZE(EntityType.BLAZE, 20),
        BOGGED(EntityType.BOGGED, 20),
        BREEZE(EntityType.BREEZE, 20),
        CREAKING(EntityType.CREAKING, 20),
        CREEPER(EntityType.CREEPER, 20),
        ELDER_GUARDIAN(EntityType.ELDER_GUARDIAN, 20),
        ENDERMITE(EntityType.ENDERMITE, 20),
        ENDER_DRAGON(EntityType.ENDER_DRAGON, 20),
        EVOKER(EntityType.EVOKER, 20),
        GHAST(EntityType.GHAST, 20),
        GUARDIAN(EntityType.GUARDIAN, 20),
        HOGLIN(EntityType.HOGLIN, 20),
        HUSK(EntityType.HUSK, 20),
        MAGMA_CUBE(EntityType.MAGMA_CUBE, 20),
        PHANTOM(EntityType.PHANTOM, 20),
        PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, 20),
        PILLAGER(EntityType.PILLAGER, 20),
        RAVAGER(EntityType.RAVAGER, 20),
        SHULKER(EntityType.SHULKER, 20),
        SILVERFISH(EntityType.SILVERFISH, 20),
        SKELETON(EntityType.SKELETON, 20),
        SLIME(EntityType.SLIME, 20),
        STRAY(EntityType.STRAY, 20),
        VEX(EntityType.VEX, 20),
        VINDICATOR(EntityType.VINDICATOR, 20),
        WARDEN(EntityType.WARDEN, 20),
        WITCH(EntityType.WITCH, 20),
        WITHER(EntityType.WITHER, 20),
        WITHER_SKELETON(EntityType.WITHER_SKELETON, 20),
        ZOGLIN(EntityType.ZOGLIN, 20),
        ZOMBIE(EntityType.ZOMBIE, 20),
        ZOMBIE_VILLAGER(EntityType.VILLAGER, 20);

        private final EntityType entityType;
        private final int xp;

        SwordReward(EntityType entityType, int xp) {
            this.xp = xp;
            this.entityType = entityType;
        }

        public int getXpValue() {
            return this.xp;
        }

        public EntityType getEntityType(){
            return this.entityType;
        }
    }

    public static enum CookingReward {
        PORKCHOP(Material.PORKCHOP, 20),
        MUTTON(Material.MUTTON, 20);

        private Material material;
        private final int xp;

        CookingReward(Material material, int xp) {
            this.xp = xp;
            this.material = material;
        }

        public int getXpValue() {
            return this.xp;
        }

        public Material getFood(){
            return this.material;
        }
    }
}
