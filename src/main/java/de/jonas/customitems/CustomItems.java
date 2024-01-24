package de.jonas.customitems;

import de.jonas.customitems.commands.GiveItemCommand;
import de.jonas.customitems.listener.TreeCutListener;
import de.jonas.customitems.listener.ClickedCustomItemListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CustomItems extends JavaPlugin {

    public static CustomItems INSTANCE;
    public static String PREFIX;
    public Logger logger;

    public void onLoad() {
        this.logger = this.getLogger();

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
        new GiveItemCommand();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        PREFIX = "[CI] ";

        this.listener();

        CommandAPI.onEnable();

        //this.saveDefaultConfig();

        logger.log(Level.INFO, "Activatet Plugin");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CommandAPI.onDisable();

        logger.log(Level.INFO,"Plugin deaktiviert.");
    }

    public void listener() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new ClickedCustomItemListener(), this);
        pm.registerEvents(new TreeCutListener(), this);
    }
}
