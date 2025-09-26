package me.dotu.MMO.Spawners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Configs.LootTableConfig;
import me.dotu.MMO.Configs.SpawnerConfig;
import me.dotu.MMO.Configs.SpawnerLocationDataConfig;
import me.dotu.MMO.Enums.SpawnerKey;
import me.dotu.MMO.Main;
import me.dotu.MMO.Utils.LocationUtils;
import me.dotu.MMO.Utils.RandomNum;
import net.md_5.bungee.api.ChatColor;

public class CustomSpawnerHandler implements Listener {

    public CustomSpawnerHandler() {
    }

    private final NamespacedKey entityTag = new NamespacedKey(Main.plugin, "DotuMMO_CustomEntity");
    private final NamespacedKey entityTagSpawnerLink = new NamespacedKey(Main.plugin, "DotuMMO_EntitySpawnerLoc");
    private final String[] suffixes = {
        "_HELMET",
        "_CHESTPLATE",
        "_LEGGINGS",
        "_BOOTS"
    };

    public void spawnCustomEntity(Location spawnerLoc, String spawnerLinkLoc) {
        if (spawnerLoc == null || spawnerLoc.getWorld() == null) {
            return;
        }

        if (spawnerLoc.getBlock().getType() != Material.SPAWNER) {
            return;
        }

        CreatureSpawner spawner = (CreatureSpawner) spawnerLoc.getBlock().getState();
        if (!spawner.getPersistentDataContainer().has(SpawnerKey.ROOT.getKey())) {
            return;
        }

        EntityType type = spawner.getSpawnedType();
        if (!type.isAlive() || !type.isSpawnable()) {
            return;
        }

        String name = spawner.getPersistentDataContainer().get(SpawnerKey.NAME.getKey(), PersistentDataType.STRING);
        if (name == null) {
            return;
        }

        CustomSpawner customSpawner = SpawnerConfig.spawners.get(name);
        if (customSpawner == null) {
            return;
        }

        SpawnerLocationData sld = SpawnerLocationDataConfig.spawnerLocationData.get(LocationUtils.serializeLocation(spawnerLoc));
        Location mobSpawnLocation = this.getRandomLocation(sld.getSpawnLocations());
        if (mobSpawnLocation == null || mobSpawnLocation.getWorld() != spawnerLoc.getWorld()) {
            return;
        }

        Location temp = mobSpawnLocation.clone();
        if (temp.getBlock().getType().isSolid()) {
            temp.add(0, 1, 0);
        }
        temp.add(0.5, 0, 0.5);

        LivingEntity living = (LivingEntity) temp.getWorld().spawnEntity(temp, type);

        boolean equip = this.canEquipGear(customSpawner);

        if (this.hasEquipmentSlots(living) && equip) {
            if (customSpawner.isArmored()) {
                for (int x = 0; x < 4; x++) {
                    this.equipItemsToMob(sld, living, x);
                }
            }
            if (customSpawner.isWeaponed()) {
                this.equipItemsToMob(sld, living, 4);
            }
        }

        living.teleport(temp);
        this.setHealth(customSpawner, living);
        this.setDisplayName(customSpawner, living);
        this.tagEntity(living, spawnerLinkLoc);
    }

    private void equipItemsToMob(SpawnerLocationData sld, LivingEntity living, int slot) {
        ArrayList<ItemStack> equipable = new ArrayList<>();
        if (slot <= 3){
            equipable = this.getMatchingItems(sld.getEquipableArmor(), this.suffixes[slot]);
        }
        else{
            equipable = sld.getEquipableWeapon();
        }

        ItemStack stack = rollTable(equipable);

        switch (slot) {
            case 0:
                living.getEquipment().setHelmet(stack);
                break;
            case 1:
                living.getEquipment().setChestplate(stack);
                break;
            case 2:
                living.getEquipment().setLeggings(stack);
                break;
            case 3:
                living.getEquipment().setBoots(stack);
                break;
            case 4:
                living.getEquipment().setItemInMainHand(stack);
                break;
        }
    }

    private ArrayList<ItemStack> getMatchingItems(ArrayList<ItemStack> items, String suffix){
        if (items.isEmpty()){
            return new ArrayList<>();
        }
        
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        for (ItemStack item : items){
            if (item.getType().name().endsWith(suffix)){
                returnArray.add(item);
            }
        }
        return returnArray;
    }

    private ItemStack rollTable(ArrayList<ItemStack> table) {
        if (table.isEmpty()){
            return new ItemStack(Material.AIR);
        }

        int whatToEquip = RandomNum.getRandom(0, table.size());
        return table.get(whatToEquip);
    }

    private Location getRandomLocation(ArrayList<Location> spawnLocations) {
        if (spawnLocations == null || spawnLocations.isEmpty()) {
            return null;
        }
        int index = RandomNum.getRandom(0, spawnLocations.size() - 1);
        return spawnLocations.get(index);
    }

    private boolean canEquipGear(CustomSpawner customSpawner) {
        if (this.rollChanceToGear(customSpawner.getDifficulty())) {
            if (this.lootTableExists(customSpawner.getDropTable())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEquipmentSlots(LivingEntity entity) {
        return (entity instanceof Mob) && entity.getEquipment() != null;
    }

    private boolean rollChanceToGear(double chance) {
        return Math.random() < (chance / 100.0);
    }

    private int calculateHealth(int level) {
        int baseHealth = 20;
        double growthRate = 1.15;
        return (int) (baseHealth * Math.pow(growthRate, level - 1));
    }

    private void setDisplayName(CustomSpawner customSpawner, LivingEntity living) {
        if (customSpawner.isNameVisible()) {
            living.setCustomNameVisible(true);
            String entityName = living.getName();
            int health = (int) living.getHealth();
            String displayHealth = Integer.toString((int) Math.ceil(health));
            living.setCustomName(ChatColor.YELLOW + entityName + " " + ChatColor.RED + displayHealth);
        } else {
            living.setCustomNameVisible(false);
        }
    }

    private void tagEntity(LivingEntity living, String spawnerLinkLoc) {
        living.getPersistentDataContainer().set(this.entityTag, PersistentDataType.BOOLEAN, true);
        living.getPersistentDataContainer().set(this.entityTagSpawnerLink, PersistentDataType.STRING, spawnerLinkLoc);
    }

    @EventHandler
    public void entityDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        LivingEntity living = (LivingEntity) event.getEntity();
        if (living.getPersistentDataContainer().has(this.entityTag)) {
            String[] livingNameParts = living.getName().split("\\ ");
            String entityName = livingNameParts[0];
            double preHealth = living.getHealth();
            double postHealth = Math.max(0.0, preHealth - event.getFinalDamage());
            String displayHealth = Integer.toString((int) Math.ceil(postHealth));
            living.setCustomName(ChatColor.YELLOW + entityName + " " + ChatColor.RED + displayHealth);
        }
    }

    @EventHandler
    public void spawnerSpawn(SpawnerSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void entityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }
        LivingEntity living = (LivingEntity) event.getEntity();
        if (living.getPersistentDataContainer().has(this.entityTag) && living.getPersistentDataContainer().has(this.entityTagSpawnerLink)) {
            String[] nskArray = living.getPersistentDataContainer().get(this.entityTagSpawnerLink, PersistentDataType.STRING).split("\\|");
            CustomSpawner customSpawner = this.getSpawnerFromNsk(nskArray[0]);
            String spawnerLoc = nskArray[1];

            if (customSpawner == null || spawnerLoc == null) {
                return;
            }

            SpawnerEntityData spawnerData = SpawnerConfig.spawnerDataList.get(spawnerLoc);
            spawnerData.setActiveEntitiesAmount(spawnerData.getActiveEntitiesAmount() - 1);

            if (spawnerData.getActiveEntitiesAmount() < 0) {
                spawnerData.setActiveEntitiesAmount(0);
            }
        }
    }

    private CustomSpawner getSpawnerFromNsk(String spawnerName) {
        return SpawnerConfig.spawners.get(spawnerName);
    }

    private void setHealth(CustomSpawner customSpawner, LivingEntity living) {
        int level = RandomNum.getRandom(customSpawner.getMinLevel(), customSpawner.getMaxLevel());
        int targetHealth = calculateHealth(level);
        if (targetHealth < 1) {
            targetHealth = 1;
        }

        AttributeInstance maxAttr = living.getAttribute(Attribute.MAX_HEALTH);
        if (maxAttr != null) {
            maxAttr.setBaseValue(targetHealth);
            living.setHealth(maxAttr.getValue());
        } else {
            living.setHealth(Math.min(targetHealth, living.getHealth()));
        }
    }

    @EventHandler
    public void spawnerPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.SPAWNER) {
            ItemStack handItem = event.getItemInHand();
            ItemMeta handMeta = handItem.getItemMeta();
            if (handMeta.getPersistentDataContainer().has(SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN)) {
                String name = handMeta.getPersistentDataContainer().get(SpawnerKey.NAME.getKey(), PersistentDataType.STRING);
                CreatureSpawner spawner = (CreatureSpawner) block.getState();
                CustomSpawner customSpawner = SpawnerConfig.spawners.get(name);
                this.setSpawnerProps(customSpawner, spawner);
                spawner.setSpawnRange(customSpawner.getSpawnRange());
                SpawnerLocationData sld = new SpawnerLocationData(customSpawner.getName(), block.getLocation(), null, new ArrayList<ItemStack>(), new ArrayList<>());
                SpawnerLocationDataConfig.spawnerLocationData.put(LocationUtils.serializeLocation(block.getLocation()),sld);
            }
        }
    }

    @EventHandler
    public void spawnerBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() == Material.SPAWNER) {
            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            try {
                if (spawner.getPersistentDataContainer().has(SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN)) {
                    SpawnerLocationDataConfig.spawnerLocationData
                            .remove(LocationUtils.serializeLocation(block.getLocation()));
                }
            } catch (Exception e) {
            }
        }
    }

    @EventHandler
    public void entityCombust(EntityCombustEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        if (event instanceof EntityCombustByBlockEvent) {
            return;
        }

        if (event instanceof EntityCombustByEntityEvent) {
            return;
        }

        LivingEntity living = (LivingEntity) event.getEntity();
        if (!living.getPersistentDataContainer().has(this.entityTag)
                && !living.getPersistentDataContainer().has(this.entityTagSpawnerLink)) {
            return;
        }

        event.setCancelled(true);
    }

    public void killTaggedEntities() {
        for (World world : Bukkit.getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (!(entity instanceof LivingEntity)) {
                    continue;
                }

                LivingEntity living = (LivingEntity) entity;
                if (!living.getPersistentDataContainer().has(this.entityTag)
                        && !living.getPersistentDataContainer().has(this.entityTagSpawnerLink)) {
                    continue;
                }

                if (living.isDead()) {
                    continue;
                }

                living.remove();
            }
        }
    }

    public static ItemStack decorateSpawnerStack(CustomSpawner customSpawner) {
        String spawnerName = customSpawner.getName();

        List<String> lores = Arrays.asList(
                ChatColor.AQUA + "Loot Table: " + ChatColor.YELLOW + customSpawner.getDropTable(),
                ChatColor.AQUA + "Min Level: " + ChatColor.YELLOW + String.valueOf(customSpawner.getMinLevel()),
                ChatColor.AQUA + "Max Level: " + ChatColor.YELLOW + String.valueOf(customSpawner.getMaxLevel()),
                ChatColor.AQUA + "Spawn Delay (seconds): " + ChatColor.YELLOW + String.valueOf(customSpawner.getSpawnDelay()),
                ChatColor.AQUA + "Spawn Range: " + ChatColor.YELLOW + String.valueOf(customSpawner.getSpawnRange()),
                ChatColor.AQUA + "Difficulty: " + ChatColor.YELLOW + Double.toString(customSpawner.getDifficulty()),
                ChatColor.AQUA + "Armored: " + ChatColor.YELLOW + Boolean.toString(customSpawner.isArmored()),
                ChatColor.AQUA + "Weaponed: " + ChatColor.YELLOW + Boolean.toString(customSpawner.isWeaponed()),
                ChatColor.AQUA + "Name Visible: " + ChatColor.YELLOW + Boolean.toString(customSpawner.isNameVisible()),
                ChatColor.AQUA + "Spawn Randomly: " + ChatColor.YELLOW + Boolean.toString(customSpawner.isSpawnRandomly()));

        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN, true);
        meta.getPersistentDataContainer().set(SpawnerKey.NAME.getKey(), PersistentDataType.STRING, spawnerName);

        meta.setDisplayName(ChatColor.BLUE + spawnerName);
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public void setSpawnerProps(CustomSpawner customSpawner, CreatureSpawner spawner) {
        spawner.getPersistentDataContainer().set(SpawnerKey.ROOT.getKey(), PersistentDataType.BOOLEAN, true);
        spawner.getPersistentDataContainer().set(SpawnerKey.MIN_LEVEL.getKey(), PersistentDataType.INTEGER, customSpawner.getMinLevel());
        spawner.getPersistentDataContainer().set(SpawnerKey.MAX_LEVEL.getKey(), PersistentDataType.INTEGER, customSpawner.getMaxLevel());
        spawner.getPersistentDataContainer().set(SpawnerKey.DIFFICULTY.getKey(), PersistentDataType.DOUBLE, customSpawner.getDifficulty());
        spawner.getPersistentDataContainer().set(SpawnerKey.ARMORED.getKey(), PersistentDataType.BOOLEAN, customSpawner.isArmored());
        spawner.getPersistentDataContainer().set(SpawnerKey.WEAPONED.getKey(), PersistentDataType.BOOLEAN, customSpawner.isWeaponed());
        spawner.getPersistentDataContainer().set(SpawnerKey.NAME_VISIBLE.getKey(), PersistentDataType.BOOLEAN, customSpawner.isNameVisible());
        spawner.getPersistentDataContainer().set(SpawnerKey.SPAWN_RANDOMLY.getKey(), PersistentDataType.BOOLEAN, customSpawner.isSpawnRandomly());
        spawner.getPersistentDataContainer().set(SpawnerKey.NAME.getKey(), PersistentDataType.STRING, customSpawner.getName());
        spawner.getPersistentDataContainer().set(SpawnerKey.TABLE.getKey(), PersistentDataType.STRING, customSpawner.getDropTable());
        spawner.getPersistentDataContainer().set(SpawnerKey.SPAWN_DELAY.getKey(), PersistentDataType.INTEGER, customSpawner.getSpawnDelay());
        spawner.getPersistentDataContainer().set(SpawnerKey.SPAWN_RANGE.getKey(), PersistentDataType.INTEGER, customSpawner.getSpawnRange());
        spawner.update();
    }

    private boolean lootTableExists(String lootTable) {
        return LootTableConfig.lootTables.containsKey(lootTable);
    }
}