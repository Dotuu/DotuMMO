package me.dotu.MMO.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.DefaultConfig;
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Managers.MessageManager;
import me.dotu.MMO.Managers.PvpManager;
import me.dotu.MMO.Managers.SettingsManager;

public class PvpSubCommand implements SubCommand {

    @Override
    public String getName() {
        return "pvp";
    }

    @Override
    public String getPermission() {
        return PermissionType.PVP.getPermission();
    }

    @Override
    public boolean isConsoleSafe() {
        return true;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (player.hasPermission(this.getPermission())
                || player.hasPermission(PermissionType.ADMIN.getPermission())) {
            switch (args[1].toLowerCase()) {
                case "startseason":
                    if (this.handleStartSeasonCommand()) {
                        player.sendMessage(MessageManager.send(MessageManager.Type.SUCCESS,
                                "New season started, all stats have been reset"));
                        break;
                    }
                    player.sendMessage(MessageManager.send(MessageManager.Type.ERROR,
                            "Something went wrong, please check console for logs"));
                    break;
                default:
                    // send help list
                    break;
            }
        }

        return true;
    }

    private boolean handleStartSeasonCommand() {
        try {
            PvpManager pvpManager = new PvpManager();
            pvpManager.resetPvpSeasonForOnlinePlayers();

            SettingsManager settingsManager = SettingsConfig.settingsMap.get(DefaultConfig.Settings.PVP);
            settingsManager.setSettingsLong(DefaultConfig.Settings.PVP, "season_timer", System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
