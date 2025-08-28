package me.dotu.MMO.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.ChunkLoader.ChunkDataManager;

public class TestCommand implements CommandExecutor{

    public TestCommand(){
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {
            if (sender instanceof Player){

                Player player = (Player) sender;

                // CustomItem customItem = new CustomItem("Dotu's Helmet of FIRE", 100, (short) 2, Material.DIAMOND_HELMET, ItemEnum.Tier.COMMON);
                // ItemStack item = new ItemStack(customItem);

                // ItemMeta meta = item.getItemMeta();
                // NamespacedKey slowEat = new NamespacedKey(Main.plugin, AugmentEnum.Augment.SLOW_EAT.getName());
                // meta.getPersistentDataContainer().set(slowEat, PersistentDataType.INTEGER, 1);

                // item.setItemMeta(meta);
                // ItemMeta decorate = Decorator.decorate(item, props);
                // item.setItemMeta(decorate);
                
                // Damageable damageable = (Damageable) decorate;
                // damageable.setDamage(100);

                // decorate.setDisplayName("Dotu's helm of fireeee");
                

                // player.getInventory().addItem(item);
            }else {
                sender.sendMessage("This command can only be used by players.");
            }
            return true;
        }
        else if (command.getName().equalsIgnoreCase("chunktest")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                player.sendMessage(Integer.toString(ChunkDataManager.loadedChunks.size()));
            }
            else{
                System.out.println(Integer.toString(ChunkDataManager.loadedChunks.size()));
            }
        }
        return true;
    }
}
