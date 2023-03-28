package yumcraft.getofflineloc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import yumcraft.getofflineloc.Command.command;
import yumcraft.getofflineloc.Event.onPlayerQuit;
import yumcraft.getofflineloc.Redis.redis;
import yumcraft.getofflineloc.Sql.sql;

import java.sql.SQLException;

public final class GetOfflineLoc extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        FileConfiguration config = this.getConfig();
        saveDefaultConfig();

        //
        redis redis = new redis(this);
        redis.ConnectRedis();
        this.getCommand("GetOfflineLoc").setExecutor(new command(this));
        //
        this.getServer().getPluginManager().registerEvents(new onPlayerQuit(this), this);
        try {
            new sql().ConnectSql();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
