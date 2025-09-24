package me.dotu.MMO.Commands.SubCommands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.dotu.MMO.Commands.SubCommand;
import me.dotu.MMO.Configs.LootTableConfig;
import me.dotu.MMO.Enums.GemType;
import me.dotu.MMO.Enums.ItemTier;
import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Enums.NamedKey;
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Gems.Gem;
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
        switch(args[1].toLowerCase()){
            case "create":
                this.handleTableCreateCommand(player, args);
                break;
            case "add":
            this.handleTableAddCommand(player, args);
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
            MessageManager.send(player, Messages.ERR_TABLE_EXISTS, true);
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

    private void handleTableAddCommand(Player player, String[] args){// dotummo table add name 10 inv
        if (args.length < 3){
            // send syntax message
        }

        LootTable lootTable = LootTableConfig.lootTables.get(args[2]);
        if (lootTable == null){
            //send invalid loot table message here
            return;
        }

        if (args[4].equalsIgnoreCase("inv") || args[4].equalsIgnoreCase("inventory")){
            for (int x = 0; x < player.getInventory().getSize(); x++){
                ItemStack stack = player.getInventory().getItem(x);
                
                if (stack.getType() == Material.AIR){
                    continue;
                }

                if (!stack.hasItemMeta()){
                    continue;
                }

                ItemMeta meta = stack.getItemMeta();
                
                if (this.hasKey(meta, NamedKey.CUSTOM_ITEM.getKey())){
                    LootTableItem lootTableItem = new LootTableItem(stack.getType(), meta.getDisplayName()); 
                    lootTable.addItem(lootTableItem);

                    if (this.hasKey(meta, NamedKey.GEMS.getKey())){
                        String[] gemStrings = this.getArrayFromKeyValue(this.getKey(meta, NamedKey.GEMS));
                        ArrayList<Gem> gems = new ArrayList<>();
                        for (String gemKey : gemStrings){
                            String[] gemDetails = gemKey.split(":");
                            if (GemType.valueOf(gemDetails[0]) != null){
                                String gemName = gemDetails[0];
                                String gemTier = gemDetails[1];

                                Gem gem = new Gem(ItemTier.valueOf(gemTier), x, GemType.valueOf(gemName), getName());
                            }
                        }
                    }
    
                    if (this.hasKey(meta, NamedKey.AUGMENTS.getKey())){
                        
                    }
    
                    if (meta.hasLore()){
                        
                    }
                }
            }
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR){
            // send need item in hand or use /dotummo table add <table> inv
            return;
        }
    }

    private String getKey(ItemMeta meta, NamedKey key){
        return meta.getPersistentDataContainer().get(key.getKey(), PersistentDataType.STRING);
    }

    private boolean hasKey(ItemMeta meta, NamespacedKey nsk){
        return meta.getPersistentDataContainer().has(nsk);
    }

    private String[] getArrayFromKeyValue(String key){
        return key.contains("-") ? key.split("-") : new String[] {key};
    }
}
