package me.classified.atlasconfirm.commands;

import me.classified.atlasconfirm.AtlasConfirm;
import me.classified.atlasconfirm.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AtlasConfirmCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("atlasconfirm")){
            if(!sender.hasPermission("atlasconfirm.reload")){
                sender.sendMessage(Utils.color(AtlasConfirm.getConfigFile().getString("messages.no-permission")));
                return false;
            }
            if(args[0].equalsIgnoreCase("reload")){
                AtlasConfirm.reloadConfigFile();
                sender.sendMessage(Utils.color(AtlasConfirm.getConfigFile().getString("messages.reload")));
            }
            else {
                for(String message : AtlasConfirm.getConfigFile().getStringList("messages.atlasconfirm-usage")){
                    sender.sendMessage(Utils.color(message));
                }
            }
            return false;
        }
        return false;
    }

}
