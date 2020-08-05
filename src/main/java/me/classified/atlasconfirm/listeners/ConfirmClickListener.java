package me.classified.atlasconfirm.listeners;

import me.classified.atlasconfirm.AtlasConfirm;
import me.classified.atlasconfirm.handlers.DuePackages;
import me.classified.atlasconfirm.utils.LogUtils;
import me.classified.atlasconfirm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ConfirmClickListener implements Listener {

    LogUtils logUtils = AtlasConfirm.getLogUtils();

    @EventHandler
    public void onConfirmClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(Utils.color(AtlasConfirm.getConfigFile().getString("inventory.title")))) {
            if (e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) return;
            if (!e.getCurrentItem().hasItemMeta()) {
                e.setCancelled(true);
                return;
            }
            Inventory inv = e.getView().getTopInventory();
            if(e.getClick().isLeftClick()) {
                if (e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().hasLore() && e.getCurrentItem().getItemMeta().getLore().get(0).equals(Utils.color(AtlasConfirm.getConfigFile().getStringList("inventory.package-lore").get(0)))) {
                    String packageId = CraftItemStack.asNMSCopy(e.getCurrentItem()).getTag().getString("packageId").toLowerCase();
                    String transactionId = CraftItemStack.asNMSCopy(e.getCurrentItem()).getTag().getString("transactionID");

                    DuePackages.getPackagesToGive().remove(p.getUniqueId().toString() + ":" + packageId + ":" + transactionId);
                    for (String commands : AtlasConfirm.getConfigFile().getStringList("packages." + packageId + ".commands")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commands.replaceAll("%name%", p.getName()));
                    }
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 10, 2);
                    logUtils.log(p.getName(), p.getUniqueId().toString(), packageId, transactionId, true);
                    Bukkit.getLogger().info("[AtlasConfirm] " + p.getName() + " has ACCEPTED the a Buycraft package with Package ID:" + packageId + " and Transaction ID:" + transactionId);
                    for(String message : AtlasConfirm.getConfigFile().getStringList("messages.package-accepted")){
                        p.sendMessage(Utils.color(message).replaceAll("%package_name%", Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
                    }
                    for(String message : AtlasConfirm.getConfigFile().getStringList("messages.donation")){
                        Bukkit.broadcastMessage(Utils.color(message).replaceAll("%player%", p.getName()));
                    }
                    e.setCancelled(true);
                    p.closeInventory();
                    for(String s : DuePackages.getPackagesToGive()){
                        String[] splitter = s.split(":");
                        String uuidString = splitter[0];
                        if(uuidString.equals(p.getUniqueId().toString())){
                            for(String message : AtlasConfirm.getConfigFile().getStringList("messages.confirm-remaining")){
                                p.sendMessage(Utils.color(message));
                            }
                            p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 10, 2);
                        }
                    }
                    return;
                }
            }
            if(e.getClick().isRightClick()){
                String packageId = CraftItemStack.asNMSCopy(e.getCurrentItem()).getTag().getString("packageId").toLowerCase();
                String transactionId = CraftItemStack.asNMSCopy(e.getCurrentItem()).getTag().getString("transactionID");
                DuePackages.getPackagesToGive().remove(p.getUniqueId().toString() + ":" + packageId + ":" + transactionId);
                p.playSound(p.getLocation(), Sound.BLAZE_DEATH, 10, 2);
                logUtils.log(p.getName(), p.getUniqueId().toString(), packageId, transactionId, false);
                Bukkit.getLogger().warning("[AtlasConfirm] " + p.getName() + " has DENIED the a Buycraft package with Package ID:" + packageId + " and Transaction ID:" + transactionId);
                for(String message : AtlasConfirm.getConfigFile().getStringList("messages.package-denied")){
                    p.sendMessage(Utils.color(message).replaceAll("%package_name%", Utils.removeColor(e.getCurrentItem().getItemMeta().getDisplayName())));
                }
                e.setCancelled(true);
                p.closeInventory();
                for(String s : DuePackages.getPackagesToGive()){
                    String[] splitter = s.split(":");
                    String uuidString = splitter[0];
                    if(uuidString.equals(p.getUniqueId().toString())){
                        for(String message : AtlasConfirm.getConfigFile().getStringList("messages.confirm-remaining")){
                            p.sendMessage(Utils.color(message));
                        }
                        p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 10, 2);
                    }
                }
                return;
            }
            e.setCancelled(true);
        }
    }

}
