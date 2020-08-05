package me.classified.atlasconfirm.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String color(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String removeColor(String msg){
        return ChatColor.stripColor(msg);
    }

    public static int randomInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt(max - min + 1) + min;
        return randomNum;
    }

    public static double randDouble(double min, double max){
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    public static String formatBalance(Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(price);
    }


    public static String getDateAndTime(){
        DateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
        Date d = new Date();
        String fd = df.format(d);
        String[] fds = fd.split(" ");
        if(fds[0].startsWith("0")){
            fds[0] = fds[0].replaceFirst("0", "");
        }
        if(fds[1].startsWith("0")){
            fds[1] = fds[1].replaceFirst("0", "");
        }
        String ffd = fds[0] + " at " + fds[1] + " " + fds[2];
        return ffd;
    }

    public static String getDate(){
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date d = new Date();
        String fd = df.format(d);
        String[] fds = fd.split(" ");
        if(fds[0].startsWith("0")){
            fds[0] = fds[0].replaceFirst("0", "");
        }
        String ffd = fds[0];
        return ffd;
    }

}
