package me.classified.atlasconfirm.handlers;

import me.classified.atlasconfirm.AtlasConfirm;
import me.classified.atlasconfirm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class DuePackages implements Listener {

    public static ArrayList<String> packagesToGive = new ArrayList<>();
    public static ArrayList<String> getPackagesToGive() {
        return packagesToGive;
    }

    public static void saveDuePackages(){
        AtlasConfirm.getPendingPackagesFile().set("pendingPackages", packagesToGive);
        AtlasConfirm.savePendingPackagesFile();
    }

    public static void loadDuePackages(){
        for(String s : AtlasConfirm.getPendingPackagesFile().getStringList("pendingPackages")){
            packagesToGive.add(s);
        }
        AtlasConfirm.savePendingPackagesFile();
    }

    @EventHandler
    public void onPackageLogin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        for(String s : getPackagesToGive()){
            String[] splitter = s.split(":");
            String uuidString = splitter[0];
            String packageId = splitter[1];
            String transactionId = splitter[2];
            if(uuidString.equals(uuid.toString())){
                for(String message : AtlasConfirm.getConfigFile().getStringList("messages.confirm-notify")){
                    p.sendMessage(Utils.color(message));
                }
                p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 10, 2);
            }
        }
    }

    public static void runPackageCheck(){
        new BukkitRunnable(){
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()){
                    UUID uuid = p.getUniqueId();
                    for(String s : getPackagesToGive()){
                        String[] splitter = s.split(":");
                        String uuidString = splitter[0];
                        String packageId = splitter[1];
                        String transactionId = splitter[2];
                        if(uuidString.equals(uuid.toString())){
                            for(String message : AtlasConfirm.getConfigFile().getStringList("messages.confirm-notify")){
                                p.sendMessage(Utils.color(message));
                            }
                            p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 10, 2);
                        }
                    }
                }
            }
        }.runTaskTimer(AtlasConfirm.getInstance(), 20, (20 * 60) * 5);
    }

}
