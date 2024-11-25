package de.jonas.customitems;

import org.bukkit.plugin.java.JavaPlugin;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CustomItems extends JavaPlugin {

    public static CustomItems INSTANCE;
    public static String PREFIX;
    public Logger logger;

    public void onLoad() {
        INSTANCE = this;

        this.logger = this.getLogger();

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        CommandAPI.onEnable();

        logger.log(Level.INFO, "Activatet Plugin");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        CommandAPI.onDisable();

        logger.log(Level.INFO,"Plugin deaktiviert.");
    }

}
