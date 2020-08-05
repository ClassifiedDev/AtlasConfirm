package me.classified.atlasconfirm.utils;

import me.classified.atlasconfirm.AtlasConfirm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    AtlasConfirm plugin;
    static File file;

    public LogUtils(AtlasConfirm plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder() + "/confirm_log.txt");
    }

    public void addMessage(String message){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(message);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setup(){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void log(String ign, String uuid, String packageId, String transactionID, boolean confirmed){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy-HH:mm:ss");
        String date = dateFormat.format(new Date());
        String finalMessage;
        if(confirmed) {
            finalMessage = "CONFIRMED: " + date + " - IGN:" + ign + " - UUID:" + uuid + " - PACKAGE ID:" + packageId +  " - TRANSACTION ID:" + transactionID + "\n";
        }
        else {
            finalMessage = "DENIED: " + date + " - IGN:" + ign + " - UUID:" + uuid + " - PACKAGE ID:" + packageId +  " - TRANSACTION ID:" + transactionID + "\n";
        }
        addMessage(finalMessage);
    }
}
