package yumcraft.getofflineloc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import yumcraft.getofflineloc.Command.command;
import yumcraft.getofflineloc.Event.onPlayerQuit;
import yumcraft.getofflineloc.Redis.redisUnity;
import yumcraft.getofflineloc.Sql.sql;
import yumcraft.getofflineloc.Task.taskSaveDate;

import java.sql.SQLException;

public final class GetOfflineLoc extends JavaPlugin {

    @Override
    public void onEnable() {


        // Plugin startup logic
        FileConfiguration config = this.getConfig();
        saveDefaultConfig();
        //
        this.getCommand("GetOfflineLoc").setExecutor(new command(this));
        //
        this.getServer().getPluginManager().registerEvents(new onPlayerQuit(this), this);
        new taskSaveDate(this).taskSaveDate1();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
