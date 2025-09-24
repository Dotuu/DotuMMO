package me.dotu.MMO.Commands.SubCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dotu.MMO.Commands.SubCommand;
import me.dotu.MMO.Configs.SettingsConfig;
import me.dotu.MMO.Enums.Messages;
import me.dotu.MMO.Enums.PermissionType;
import me.dotu.MMO.Enums.Settings;
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
        if (!(sender instanceof Player)){
            switch (args[1].toLowerCase()) {
                case "startseason":
                    if (this.handleStartSeasonCommand()) {
                        MessageManager.send(sender, Messages.SEASON_RESET, true);
                        break;
                    }
                    MessageManager.send(sender, Messages.ERR_GENERIC, true);
                    break;
                default:
                    // send help list
                    break;
            }
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission(this.getPermission()) || player.hasPermission(PermissionType.ADMIN.getPermission())) {
            switch (args[1].toLowerCase()) {
                case "startseason":
                    if (this.handleStartSeasonCommand()) {
                        MessageManager.send(sender, Messages.SEASON_RESET, true);
                        break;
                    }
                    MessageManager.send(sender, Messages.ERR_GENERIC, true);
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

            SettingsManager settingsManager = SettingsConfig.settingsMap.get(Settings.PVP);
            settingsManager.setSettingsLong(Settings.PVP, "season_timer", System.currentTimeMillis());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
