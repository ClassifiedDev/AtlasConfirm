package me.classified.atlasconfirm.commands;

import me.classified.atlasconfirm.AtlasConfirm;
import me.classified.atlasconfirm.handlers.DuePackages;
import me.classified.atlasconfirm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePackageCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(cmd.getName().equalsIgnoreCase("givepackage")){
            if(!sender.hasPermission("atlasconfirm.givepackage")){
                sender.sendMessage(Utils.color(AtlasConfirm.getConfigFile().getString("messages.no-permission")));
                return false;
            }
            if(args.length != 3){
                for(String message : AtlasConfirm.getConfigFile().getStringList("messages.givepackage-usage")){
                    sender.sendMessage(Utils.color(message));
                }
                return false;
            }
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null){
                sender.sendMessage(Utils.color(AtlasConfirm.getConfigFile().getString("messages.player-not-found")));
                return false;
            }
            String packageName = args[1].toLowerCase();
            String transactionId = args[2];
            if(AtlasConfirm.getConfigFile().getConfigurationSection("packages." + packageName) == null){
                sender.sendMessage(Utils.color(AtlasConfirm.getConfigFile().getString("messages.package-not-found")));
                return false;
            }

            DuePackages.getPackagesToGive().add(target.getUniqueId().toString() + ":" + packageName + ":" + transactionId);
            for(String message : AtlasConfirm.getConfigFile().getStringList("messages.confirm-notify")){
                target.sendMessage(Utils.color(message));
            }
            target.playSound(target.getLocation(), Sound.SUCCESSFUL_HIT, 10, 2);
            return false;
        }
        return false;
    }
}
