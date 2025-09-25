package me.dotu.MMO.Commands.SubCommands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

public class ItemSubCommand implements SubCommand{

    /*
     *  /dotummo item create Fire Helmet

        /dotummo item add/remove augments fire ice 

        /dotummo item add <tablename>

        /dotummo item add/remove gems fire ice

        /dotummo item tier legendary

        /dotummo items <-- list all items in chest UI
     */

    private String[] validItemsName = {
        "_HELMET",
        "_CHESTPLATE",
        "_LEGGINGS",
        "_BOOTS",
        "_SWORD",
        "_AXE",
        "_HOE",
        "_SHOVEL",
    };

    private Material[] validItems = {
        Material.BOW,
        Material.MACE
    };

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public String getPermission() {
        return PermissionType.ITEM.getPermission();
    }

    @Override
    public boolean isConsoleSafe() {
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1){
            
        }

        switch(args[1]){
            case "add":
            this.handleAddItemCommand(player, args);
                break;
            case "remove":
                break;
            case "create":
            this.handleCreateItemCommand(player, args);
                break;
            case "tier":
                break;
        }
        return true;
    }
    
    private void handleCreateItemCommand(Player player, String[] args){

        if (args.length < 3){
            MessageManager.send(player, Messages.ERR_HAND_ITEM_NAME, true);
            return;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR){
            MessageManager.send(player, Messages.ERR_HAND_ITEM_NONE, true);
            return;
        }

        ItemStack handItem = player.getInventory().getItemInMainHand();
        Material handMaterial = handItem.getType();
        boolean isValidItem = false;
        
        for (String validItem : this.validItemsName){
            if (handMaterial.name().endsWith(validItem)){
                isValidItem = true;
            }
        }

        for (Material validMaterial : this.validItems){
            if (handMaterial == validMaterial){
                isValidItem = true;
            }
        }

        if (isValidItem == false){
            MessageManager.send(player, Messages.ERR_HAND_ITEM_TABLE, true);
            return;
        }

        String itemName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        LootTableItem item = new LootTableItem(handMaterial);
        item.setDisplayName(itemName);
        ItemMeta meta = handItem.getItemMeta();
        meta.getPersistentDataContainer().set(ItemKey.CUSTOM_ITEM_ID.getKey(), PersistentDataType.LONG, item.getId());
        handItem.setItemMeta(meta);
        LootTableConfig.lootTableItems.put(System.currentTimeMillis(), item);

        LootTable globalTable = LootTableConfig.lootTables.get("global");

        if (globalTable == null){
            MessageManager.send(player, Messages.ERR_GLOBAL_TABLE_EXISTS, true);
            return;
        }

        globalTable.addItem(item);

        MessageManager.send(player, Messages.HAND_ITEM_CREATED, true, itemName);
    }

    private void handleAddItemCommand(Player player, String[] args){
        
    }
}
