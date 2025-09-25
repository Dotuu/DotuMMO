package me.dotu.MMO.Commands.SubCommands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Commands.SubCommand;
import me.dotu.MMO.Configs.LootTableConfig;
import me.dotu.MMO.Enums.ItemKey;
import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Tables.LootTable;
import me.dotu.MMO.Tables.LootTableItem;

public class TableSubCommand implements SubCommand{

    /*
     * /dotummo table create Dallas

       /dotummo table add Dallas 10 inv <-- optional param to select every item in inventory (defaults to hand)
     */

    @Override
    public String getName() {
        return "table";
    }

    @Override
    public String getPermission() {
        return PermissionType.TABLE.getPermission();
    }

    @Override
    public boolean isConsoleSafe() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length <= 2){
            // TODO send syntax message
            return true;
        }

        switch(args[1].toLowerCase()){
            case "create":
                this.handleTableCreateCommand(player, args);
                break;
            case "add":
                this.handleTableAddCommand(player, args);
                break;
            default:
                // TODO: send syntax message
                break;
        }
        return true;
    }
    
    private void handleTableCreateCommand(Player player, String[] args){// dotummo table create name
        if (args.length < 3){
            MessageManager.send(player, Messages.ERR_GENERIC_ARGS, true);
            return;
        }

        String lootTableName = args[2].toLowerCase();

        if (LootTableConfig.lootTables.containsKey(lootTableName)){
            MessageManager.send(player, Messages.ERR_TABLE_ALREADY_EXISTS, true);
            return;
        }

        if (!lootTableName.matches("^[\\w]+$")){
            MessageManager.send(player, Messages.ERR_TABLE_NAME, true);
            return;
        }

        LootTable lootTable = new LootTable(lootTableName);
        LootTableConfig.lootTables.put(lootTableName, lootTable);
        MessageManager.send(player, Messages.TABLE_CREATED, true, lootTableName);
    }

    private void handleTableAddCommand(Player player, String[] args){
        // dotummo table add name 10 inv
        if (args.length < 4){
            // TODO: send syntax message
            return;
        }

        LootTable lootTable = LootTableConfig.lootTables.get(args[2]);
        if (lootTable == null){
            MessageManager.send(player, Messages.ERR_TABLE_EXISTS, true, args[2]);
            return;
        }

        if (this.isNumber(args[3]) == false){
            MessageManager.send(player, Messages.ERR_TABLE_WEIGHT, true, args[3]);
            return;
        }

        int weight = Integer.parseInt(args[3]);

        if (args.length == 4){
            ItemStack handItem = player.getInventory().getItemInMainHand();

            if (handItem.getType() == Material.AIR){
                MessageManager.send(player, Messages.ERR_HAND_ITEM_TABLE, true);
                return;
            }

            Material handMaterial = handItem.getType();

            if (handItem.hasItemMeta() == false){
                MessageManager.send(player, Messages.ERR_ITEM_DOTUMMO, true);
                return;
            }

            ItemMeta handMeta = handItem.getItemMeta();

            if (this.hasKey(handMeta, ItemKey.CUSTOM_ITEM_ID.getKey())){
                Long itemId = handMeta.getPersistentDataContainer().get(ItemKey.CUSTOM_ITEM_ID.getKey(), PersistentDataType.LONG);
                if (LootTableConfig.lootTableItems.containsKey(itemId)){
                    LootTableItem lootTableItem = LootTableConfig.lootTableItems.get(itemId);
                    lootTable.addItem(lootTableItem);
                    lootTableItem.setWeight(weight);
                    MessageManager.send(player, Messages.ITEM_CREATED, true, lootTable.getTableName());
                }
                else{
                    MessageManager.send(player, Messages.ERR_ITEM_IN_TABLE, true);
                    return;
                }
            }
            else{
                MessageManager.send(player, Messages.ERR_ITEM_DOTUMMO, true);
            }
        }

        else if (args.length >= 5){
            if (!args[4].equalsIgnoreCase("inv") || !args[4].equalsIgnoreCase("inventory")){
                // TODO: send syntax message
                return;
            }

            for (ItemStack item : this.getValidInventoryItems(player.getInventory())){
                ItemMeta meta = item.getItemMeta();
                
                if (this.hasKey(meta, ItemKey.CUSTOM_ITEM_ID.getKey())){
                    Long itemId = meta.getPersistentDataContainer().get(ItemKey.CUSTOM_ITEM_ID.getKey(), PersistentDataType.LONG);
                    if (LootTableConfig.lootTableItems.containsKey(itemId)){
                        LootTableItem lootTableItem = LootTableConfig.lootTableItems.get(itemId);
                        lootTable.addItem(lootTableItem);
                        lootTableItem.setWeight(weight);
                        MessageManager.send(player, Messages.ITEM_CREATED, true, lootTable.getTableName());
                    }
                }
                else{
                    MessageManager.send(player, Messages.ERR_ITEM_DOTUMMO, true);
                }
            }
        }
    }

    private ArrayList<ItemStack> getValidInventoryItems(Inventory inv){
        ArrayList<ItemStack> returnArray = new ArrayList<>();
        for (ItemStack item : inv){
            if (item == null){
                continue;
            }

            if (item.getType() == Material.AIR){
                continue;
            }

            if (!item.hasItemMeta()){
                continue;
            }

            returnArray.add(item);
        }
        return returnArray;
    }

    private boolean isNumber(String key){
        try {
            Integer.valueOf(key);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean hasKey(ItemMeta meta, NamespacedKey nsk){
        return meta.getPersistentDataContainer().has(nsk);
    }
}
