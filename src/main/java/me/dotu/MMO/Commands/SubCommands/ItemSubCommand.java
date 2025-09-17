package me.dotu.MMO.Commands.SubCommands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Commands.SubCommand;
import me.dotu.MMO.Configs.LootTableConfig;
import me.dotu.MMO.Enums.NamedKey;
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Tables.LootTableItem;

public class ItemSubCommand implements SubCommand{

    /*
     *  /dotummo item create Fire Helmet

        /dotummo item add/remove augments fire ice 

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
            player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Hand item requires name"));
            return;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR){
            player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Command requires an item in hand"));
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
            player.sendMessage(MessageManager.send(MessageManager.Type.ERROR, "Hand item is not a valid drop table item"));
            return;
        }

        String itemName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        LootTableItem item = new LootTableItem(handMaterial, itemName);
        handItem.getItemMeta().getPersistentDataContainer().set(NamedKey.CUSTOM_ITEM.getKey(), PersistentDataType.BOOLEAN, true);
        LootTableConfig.lootTableItems.put(System.currentTimeMillis(), item);
    }
}
