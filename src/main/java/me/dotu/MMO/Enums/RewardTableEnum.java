package me.dotu.MMO.Enums;

public class RewardTableEnum {
    public static enum FishingReward{
        RAW_COD (20),
        RAW_SALMON (30),
        TROPICAL_FISH (200),
        PUFFERFISH (45);

        private final int xp;

        FishingReward(int xp){
            this.xp = xp;
        }

        public int getXpValue(){
            return this.xp;
        }
    }

    public static enum MiningReward{
        COAL_ORE (10),
        DEEPSLATE_COAL_ORE (15),
        
        COPPER_ORE (10),
        DEEPSLATE_COPPER_ORE (15),

        IRON_ORE (15),
        DEEPSLATE_IRON_ORE (20),

        REDSTONE_ORE (20),
        DEEPSLATE_REDSTONE_ORE (25),

        LAPIS_ORE (20),
        DEEPSLATE_LAPIS_ORE (25),
        
        GOLD_ORE (25),
        DEEPSLATE_GOLD_ORE (30),
        
        DIAMOND_ORE (30),
        DEEPSLATE_DIAMOND_ORE (35),

        EMERALD_ORE (50),
        DEEPSLATE_EMERALD_ORE (55),

        NETHER_QUARTZ_ORE (100),
        NETHER_GOLD_ORE (105),

        ANCIENT_DEBRIS (175);
        private final int xp;

        MiningReward(int xp){
            this.xp = xp;
        }

        public int getXpValue(){
            return this.xp;
        }
    }

    public static enum WoodcuttingReward{
        OAK_LOG (20),
        SPRUCE_LOG (30),
        BIRCH_LOG (200),
        JUNGLE_LOG (45),
        ACACIA_LOG(45),
        DARK_OAK_LOG (45),
        MANGROVE_LOG (45),
        CHERRY_LOG (45),
        PALE_OAK_LOG (45);

        private final int xp;

        WoodcuttingReward(int xp){
            this.xp = xp;
        }

        public int getXpValue(){
            return this.xp;
        }
    }

    public static enum AxeReward{
        BLAZE(20),
        BOGGED(20),
        BREEZE(20),
        CREAKING(20),
        CREEPER(20),
        ELDER_GUARDIAN(20),
        ENDERMITE(20),
        ENDER_DRAGON(20),
        EVOKER(20),
        GHAST(20),
        GUARDIAN(20),
        HOGLIN(20),
        HUSK(20),
        MAGMA_CUBE(20),
        PHANTOM(20),
        PIGLIN_BRUTE(20),
        PILLAGER(20),
        RAVAGER(20),
        SHULKER(20),
        SILVERFISH(20),
        SKELETON(20),
        SLIME(20),
        STRAY(20),
        VEX(20),
        VINDICATOR(20),
        WARDEN(20),
        WITCH(20),
        WITHER(20),
        WITHER_SKELETON(20),
        ZOGLIN(20),
        ZOMBIE(20),
        ZOMBIE_VILLAGER(20);

        private final int xp;

        AxeReward(int xp){
            this.xp = xp;
        }

        public int getXpValue(){
            return this.xp;
        }
    }
}
