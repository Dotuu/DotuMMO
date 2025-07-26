package me.dotu.MMO.Enums;

public class DropTableEnum {
    public static enum FishingDrop{
        RAW_COD (20),
        RAW_SALMON (30),
        TROPICAL_FISH (200),
        PUFFERFISH (45);

        private final int xp;

        FishingDrop(int xp){
            this.xp = xp;
        }

        public int getXpValue(){
            return this.xp;
        }
    }

    public static enum MiningDrop{
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

        MiningDrop(int xp){
            this.xp = xp;
        }

        public int getXpValue(){
            return this.xp;
        }
    }

    public static enum WoodcuttingDrop{
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

        WoodcuttingDrop(int xp){
            this.xp = xp;
        }

        public int getXpValue(){
            return this.xp;
        }
    }
}
