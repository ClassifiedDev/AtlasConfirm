package me.classified.atlasconfirm;

import me.classified.atlasconfirm.commands.AtlasConfirmCommand;
import me.classified.atlasconfirm.commands.ConfirmCommand;
import me.classified.atlasconfirm.commands.GivePackageCommand;
import me.classified.atlasconfirm.handlers.DuePackages;
import me.classified.atlasconfirm.listeners.ConfirmClickListener;
import me.classified.atlasconfirm.utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class AtlasConfirm extends JavaPlugin {

    private static File fileF;
    private static FileConfiguration file;

    private static File configF;
    private static FileConfiguration config;

    private static File pendingPackagesF;
    private static FileConfiguration pendingPackages;

    private static Plugin instance;
    private static LogUtils logUtils;

    public void onEnable(){
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        instance = this;
        generateConfigs();
        DuePackages.loadDuePackages();
        logUtils = new LogUtils(this);
        LogUtils.setup();
        registerEvents();
        registerCommands();
        DuePackages.runPackageCheck();
        logger.info(pdfFile.getName() + " has been ENABLED " + pdfFile.getVersion() + "(Developed by: " + pdfFile.getAuthors() + ")");
    }

    public void onDisable(){
        PluginDescriptionFile pdfFile = getDescription();
        Logger logger = getLogger();
        DuePackages.saveDuePackages();
        logger.info(pdfFile.getName() + " has been DISABLED " + pdfFile.getVersion() + "(Developed by: " + pdfFile.getAuthors() + ")");
    }

    public static Plugin getInstance(){
        return instance;
    }

    public static LogUtils getLogUtils(){return logUtils;}


    private void registerCommands(){
        getCommand("confirm").setExecutor(new ConfirmCommand());
        getCommand("givepackage").setExecutor(new GivePackageCommand());
        getCommand("atlasconfirm").setExecutor(new AtlasConfirmCommand());
    }

    private void registerEvents(){
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new DuePackages(), this);
        pm.registerEvents(new ConfirmClickListener(), this);
    }

    private void generateConfigs(){
        saveDefaultConfig();

        configF = new File(getDataFolder(), "config.yml");
        config = new YamlConfiguration();

        pendingPackagesF = new File(getDataFolder(), "pending_packages.yml");
        pendingPackages = new YamlConfiguration();

        for (int i = 1; i <= 3; i++){
            if (i == 1){
                fileF = configF;
                file = config;
            }
            if (i == 2){
                fileF = pendingPackagesF;
                file = pendingPackages;
            }

            if (!fileF.exists()){
                fileF.getParentFile().mkdirs();
                saveResource(fileF.getName(), false);
            }
            try{
                file.load(fileF);
            }
            catch (IOException | InvalidConfigurationException e){
                e.printStackTrace();
            }
        }
    }

    public static FileConfiguration getConfigFile(){
        return config;
    }

    public static void saveConfigFile(){
        try{
            config.save(configF);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getPendingPackagesFile(){
        return pendingPackages;
    }

    public static void savePendingPackagesFile(){
        try{
            pendingPackages.save(pendingPackagesF);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfigFile(){
        config = YamlConfiguration.loadConfiguration(configF);
        saveConfigFile();
    }

}
