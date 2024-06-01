package de.jonas.customitems;

import de.jonas.customitems.commands.CreateItemCommand;
import de.jonas.customitems.commands.GiveItemCommand;
import de.jonas.customitems.commands.ItemCommand;
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
    public DataBasePool dbPool;

    public void onLoad() {
        INSTANCE = this;
        PREFIX = "[CI] ";

        dbPool = new DataBasePool();

        this.logger = this.getLogger();

        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
        new GiveItemCommand();
        new CreateItemCommand();
        new ItemCommand();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        dbPool.init();

        try {
            dbPool.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
