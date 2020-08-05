package me.classified.atlasconfirm.commands;

import me.classified.atlasconfirm.AtlasConfirm;
import me.classified.atlasconfirm.handlers.DuePackages;
import me.classified.atlasconfirm.utils.ItemBuilder;
import me.classified.atlasconfirm.utils.ItemMetadata;
import me.classified.atlasconfirm.utils.Utils;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ConfirmCommand implements CommandExecutor {

    public static ItemStack confirmPackage(Material material, Short data, String packageName, String packageId, String transactionID){
        ArrayList<String> lore = new ArrayList<>();
        for(String string : AtlasConfirm.getConfigFile().getStringList("inventory.package-lore")){
            lore.add(string.replaceAll("%package_id%", packageId.toUpperCase()).replaceAll("%transaction_id%", transactionID));
        }

        ItemStack itemStack = new ItemBuilder(material, data, 1).setName(packageName).setLore(lore).build();

        net.minecraft.server.v1_8_R3.ItemStack nonStackable = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tagCompound = nonStackable.getTag() != null ? nonStackable.getTag() : new NBTTagCompound();
        tagCompound.setString("transactionID", transactionID);
        tagCompound.setString("packageId", packageId.toUpperCase());

        itemStack = CraftItemStack.asCraftMirror(nonStackable);

        return itemStack;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!(sender instanceof Player)){
            return false;
        }
        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("confirm")){
            for(String s : DuePackages.getPackagesToGive()){
                String[] splitter = s.split(":");
                String uuidString = splitter[0];
                String packageId = splitter[1];
                String transactionId = splitter[2];
                Inventory buycraftClaim = Bukkit.createInventory(p, 9, Utils.color(AtlasConfirm.getConfigFile().getString("inventory.title")));
                String itemName = AtlasConfirm.getConfigFile().getString("packages." + packageId + ".name");
                buycraftClaim.addItem(confirmPackage(Material.ENDER_CHEST, (short) 0, itemName, packageId, transactionId));
                p.openInventory(buycraftClaim);
                p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 10 , 2);
                return false;
            }
            for(String message : AtlasConfirm.getConfigFile().getStringList("messages.no-packages")){
                p.sendMessage(Utils.color(message));
            }
            return false;
        }
        return false;
    }

}
